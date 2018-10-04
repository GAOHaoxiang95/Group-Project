package hereforthebeer.sokoban.Entity.Components.Input.SokobanSolver;

import java.lang.reflect.Array;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;

import hereforthebeer.sokoban.Level.Level;
import hereforthebeer.sokoban.Util.Position;

/**
 * Created by James on 18/04/2017.
 */
class SokobanNode {
    //Store the number of boxes in the current level
    public static int BOX_COUNT;

    //Store the width of the current level
    public static int WIDTH;

    //Store a ease of access adjacency array
    public static Position[] ADJACENT = {
            new Position(0, -1),
            new Position(1, 0),
            new Position(0, 1),
            new Position(-1, 0) };

    //Store a ease of access opposite adjacency array
    public static Position[] OPP_ADJACENT = {
            new Position(0, 1),
            new Position(-1, 0),
            new Position(0, -1),
            new Position(1, 0) };

    //Masks for bit packing
    private final static int MASK_COORD = 0b111111;
    private final static int MASK_POS = 0b111;
    private final static int MASK_LAST_BOX = 0b111;
    private final static int MASK_BOOL = 0b1;
    private final static int MASK_COORD_IN_PLACE = MASK_COORD << 20;




    //Final predicted cost of a path through this node to the goal
    public int f;

    //How many steps it has taken to get this far
    public int g;

    //Reference to parent node
    public SokobanNode parent;

    //Packed int for storing the positions of all the boxes
    //  0-2 = Box x pos
    //  3-5 = Box y pos
    //Over 5 boxes taking up bits from 0-29
    public int packedBoxPositions;

    //Packed int for storing if adjacent tiles are accessible, the player position and the last
    //box moved.
    //  0 = Top
    //  1 = Right
    //  2 = Bottom
    //  3 = Left
    //This takes up bits 0-19
    //  20-22 = Player x pos
    //  23-25 = Player y pos
    //  26-28 = Last box moved
    public int packedAdjacencyAndPlayerPos;





    /** Returns all children nodes accessible from this node */
    public ArrayList<SokobanNode> getChildren(Level level, boolean[] deadlocked) {
        //Store the width of the level
        int width = level.getWidth();

        //Get the positions of the boxes for this node
        ArrayList<Position> boxPositions = getBoxPositions();

        //Get adjacent positions and if they are reachable of all the boxes
        ArrayList<Position> adjacent = getAdjacentBoxPositions(ADJACENT);
        ArrayList<Position> oppAdjacent = getAdjacentBoxPositions(OPP_ADJACENT);

        //Get which adjacent positions are reachable
        boolean[] adjacentReachable = getAdjacentReachable();

        //Create array of children nodes to return
        ArrayList<SokobanNode> children = new ArrayList<>();

        //Loop through all adjacent spaces
        for (int i = 0; i < adjacent.size(); ++i) {
            //If this position isn't accessible by the player skip this child
            if (!adjacentReachable[i]) {
                continue;
            }

            //Store the position the box will be moved to
            Position targetPosition = oppAdjacent.get(i);

            //Skip child if the tile to move the box to is deadlocked, a wall or another box
            if (deadlocked[targetPosition.x + targetPosition.y * width] ||
                level.getTile(targetPosition).isCollidable() ||
                boxPositions.contains(targetPosition)) {
                continue;
            }

            //Create new child node
            SokobanNode child = new SokobanNode();

            //Get the index of the box to push to create child node
            int targetBoxIndex = i / 4;
            child.setLastBoxMoved(targetBoxIndex);

            //Set child's box positions to be a copy of this but target box moved
            ArrayList<Position> childBoxPositions = new ArrayList<>(boxPositions);
            childBoxPositions.set(targetBoxIndex, targetPosition);
            child.setBoxPositions(childBoxPositions);

            //Temporarily save the position of the box being moved in the players position
            child.setPlayerPosition(boxPositions.get(targetBoxIndex));

            //Add the child to the array to be returned
            children.add(child);
        }

        //Return the children
        return children;
    }

    /** Calculates the heuristic for this node */
    public int getHeuristic(ArrayList<Position> goals) {
        //Start with a heuristic value of 0
        int h = 0;

        //Get the positions of all the boxes
        ArrayList<Position> boxPositions = getBoxPositions();

        //Iterate through all boxes
        for (Position b : boxPositions) {
            //Start off the min value at the max int value
            int min = Integer.MAX_VALUE;

            //Loop through all goal positions
            for (Position g : goals) {
                //Update min if needed
                min = Math.min(min, Position.manhattan(b, g));
            }

            //Increase h by that value
            h += min;
        }

        //Return the heuristic value
        return h;
    }

    /** Returns an array of all the adjacent spaces next to the boxes */
    public ArrayList<Position> getAdjacentBoxPositions(Position[] adjArray) {
        //Get the positions of the boxes for this node
        ArrayList<Position> boxPositions = getBoxPositions();

        //Store adjacent positions of all the boxes
        ArrayList<Position> adjacent = new ArrayList<>(4 * BOX_COUNT);

        //Set the adjacent positions of all the boxes
        for (Position boxPos : boxPositions) {
            for (Position adj : adjArray) {
                adjacent.add(new Position(boxPos).translate(adj));
            }
        }

        //Return array
        return adjacent;
    }

    /** Returns if this node is a goal state */
    public boolean isGoalState(ArrayList<Position> goalPositions) {
        //Convert both over to a hash set and see if they are equal
        final HashSet<Position> s1 = new HashSet<>(getBoxPositions());
        final HashSet<Position> s2 = new HashSet<>(goalPositions);
        return s1.equals(s2);
    }

    /** Stores if the player can reach the adjacent tiles around a box
     * NOTE: This does not clear the bits first so only assign
     *       once per node! */
    public void setAdjacentReachable(boolean[] reachable) {
        //Loop through all adjacent positions
        for (int i = 0; i < reachable.length; ++i) {
            //Pack bools into int
            packedAdjacencyAndPlayerPos |= (reachable[i] ? 1 : 0) << i;
        }
    }

    /** Returns an array of booleans regarding which adjacent spaces are reachable */
    public boolean[] getAdjacentReachable() {
        //Copy the packed int to a temp variable
        int packedCopy = packedAdjacencyAndPlayerPos;

        //Create array for booleans
        int size = 4 * BOX_COUNT;
        boolean[] adjacent = new boolean[size];

        //Loop through and assign each boolean
        for (int i = 0; i < size; ++i) {
            adjacent[i] = (packedCopy & MASK_BOOL) != 0;
            packedCopy >>>= 1;
        }

        //Return the array
        return adjacent;
    }

    /** Sets the positions of all the boxes for the node.
     * NOTE: This does not clear the bits first so only assign
     *       once per node! */
    public void setBoxPositions(ArrayList<Position> boxPositions) {
        //Loop through and set all the box positions
        for (int i = 0, n = boxPositions.size(); i < n; ++i) {
            packedBoxPositions |= (boxPositions.get(i).x - 1) << (i * 6);
            packedBoxPositions |= (boxPositions.get(i).y - 1) << (i * 6 + 3);
        }
    }

    /** Returns the positions of the boxes for this node */
    public ArrayList<Position> getBoxPositions() {
        //Create array for storing positions
        ArrayList<Position> boxPositions = new ArrayList<Position>(BOX_COUNT);

        //Copy bits to temporary holder
        int packedCopy = packedBoxPositions;

        //Loop through all boxes and unpack their positions
        for (int i = 0; i < BOX_COUNT; packedCopy >>>= 3, ++i) {
            boxPositions.add(new Position(
                    packedCopy & MASK_POS,
                    (packedCopy >>>= 3) & MASK_POS).translate(1, 1));
        }

        //Return array
        return boxPositions;
    }

    /** Sets the reference position of the player for the node. */
    public void setPlayerPosition(Position p) {
        //Clear bits where the position will occupy
        packedAdjacencyAndPlayerPos &= ~(MASK_COORD << 20);
        packedAdjacencyAndPlayerPos |= (p.x - 1) << 20;
        packedAdjacencyAndPlayerPos |= (p.y - 1) << 23;
    }

    /** Gets the position of the player for the node */
    public Position getPlayerPosition() {
        return new Position(
                (packedAdjacencyAndPlayerPos >>> 20) & MASK_POS,
                (packedAdjacencyAndPlayerPos >>> 23) & MASK_POS).translate(1, 1);
    }

    /** Returns if the player position of a given node is the same as this node */
    public boolean playerPosEqual(SokobanNode n) {
        return (packedAdjacencyAndPlayerPos & MASK_COORD_IN_PLACE) == (n.packedAdjacencyAndPlayerPos & MASK_COORD_IN_PLACE);
    }

    /** Gets the index of the last box moved */
    public int getLastBoxMoved() {
        return (packedAdjacencyAndPlayerPos >>> 26) & MASK_LAST_BOX;
    }

    /** Sets the index of the last moved box.
     * NOTE: This does not clear the bits first so only assign
     *       once per node! */
    public void setLastBoxMoved(int index) {
        packedAdjacencyAndPlayerPos |= index << 26;
    }


    //Override equals to allow comparisons between nodes to be accurate
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || !(o instanceof SokobanNode))
            return false;
        SokobanNode node = (SokobanNode) o;
        return packedBoxPositions == node.packedBoxPositions && playerPosEqual(node);
    }

    //Create a custom hash based on the value of the packed box positions int
    @Override
    public int hashCode() {
        return packedBoxPositions;
    }

    //Create a custom string conversion for ease of display
    @Override
    public String toString() {
        //Unpack box positions
        ArrayList<Position> boxPositions = getBoxPositions();

        //Start the string with an open brace
        String str = "{ ";

        //Loop through all box positions, adding their string value to this one (comma delimed)
        for (Position p : boxPositions) {
            str += p + ", ";
        }

        //Finally add the player position and the cost of the node before closing the brace
        str += "Player: " + getPlayerPosition() + ", Cost: " + f + " }";
        return str;
    }
}
