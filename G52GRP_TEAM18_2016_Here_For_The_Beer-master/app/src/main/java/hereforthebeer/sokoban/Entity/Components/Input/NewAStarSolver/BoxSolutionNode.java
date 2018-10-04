package hereforthebeer.sokoban.Entity.Components.Input.NewAStarSolver;

import java.util.ArrayList;

import hereforthebeer.sokoban.Level.Level;
import hereforthebeer.sokoban.Util.Position;

/**
 * Created by James on 07/03/2017.
 *
 * TODO:
 *      Optimize box collision check from needing to cycle over all box positions for every
 *          adjacent box position.
 *
 *      Try to take out last box move from node memory.
 */
public class BoxSolutionNode {
    /**
     * Stores information about a potential move that can be made. <p\>
     * Used to interact with the FloodFill class.
     */
    public static class PotentialMove {
        //Index of the box to move
        public int box;

        //Position the box will move to
        public Position newBoxPosition;
        public Position playerPosition;

        //If the player can access the position (Set by the flood fill)
        public boolean accessible;

        //Distance of the position from the player start position
        public int distance;

        /** Standard assignment constructor */
        public PotentialMove(int box, Position newPosition, Position playerPosition) {
            this.box = box;
            this.newBoxPosition = newPosition;
            this.playerPosition = playerPosition;
        }
    }

    //Store the bitmask for packing and unpacking nodes
    private static int lastBoxBitmask = 0b11;
    private static int numberBoxesBitmask = 0b111111;
    private static int coordBitmask = 0b111;

    //Store a static reference to some delta positions in a clockwise array (Starts with up delta)
    private static Position[] deltaPositions = {
            new Position( 0, -1),
            new Position( 1,  0),
            new Position( 0,  1),
            new Position(-1,  0)};

    //Bit packed int for storing up to 4 box positions
    // 1-2  -> Last box pushed index
    // 3-8  -> Amount of boxes (up to 4)
    // 9-32 -> Box positions (3 for x, 3 for y, per box)
    public long boxPositionsPacked;

    //Other A* storage
    public int g;
    public int f;

    public Position topLeft;

    //Reference to parent node
    public BoxSolutionNode parent;

    /** Creates a list of all valid children of this node */
    public ArrayList<BoxSolutionNode> getChildren(Level level, boolean[] deadlocked) {
        //Unpack box positions into array for processing
        Position[] boxPositions = unpackBoxPositions();

        //Create a list of potential moves
        ArrayList<PotentialMove> moves = new ArrayList<>();

        //Create an array of positions to store the box's adjacent positions and if they collide
        Position[] adjacent = { new Position(), new Position(), new Position(), new Position() };
        boolean[] collision = new boolean[4];

        //Get width of level
        int width = level.getWidth();

        //Loop through all boxes
        for (int i = 0; i < boxPositions.length; ++i) {
            //Check if this box is deadlocked
            if (deadlocked[boxPositions[i].x + boxPositions[i].y * width]) {
                return new ArrayList<>();
            }

            //Loop through all adjacent positions
            for (int j = 0; j < 4; ++j) {
                adjacent[j].set(boxPositions[i]).translate(deltaPositions[j]);
                collision[j] = level.getTile(adjacent[j]).isCollidable() || contains(boxPositions, adjacent[j]);
            }

            //Divide into vertical and horizontal parts
            boolean vertical = collision[0] || collision[2];
            boolean horizontal = collision[1] || collision[3];

            //If vertical is free to move, add above and below to list of potential moves
            if (!vertical) {
                if (!deadlocked[adjacent[0].x + adjacent[0].y * width])
                    moves.add(new PotentialMove(i, new Position(adjacent[0]), new Position(adjacent[2])));
                if (!deadlocked[adjacent[2].x + adjacent[2].y * width])
                    moves.add(new PotentialMove(i, new Position(adjacent[2]), new Position(adjacent[0])));
            }

            //If horizontal is free to move, add left and right to list of potential moves
            if (!horizontal) {
                if (!deadlocked[adjacent[1].x + adjacent[1].y * width])
                    moves.add(new PotentialMove(i, new Position(adjacent[1]), new Position(adjacent[3])));
                if (!deadlocked[adjacent[3].x + adjacent[3].y * width])
                    moves.add(new PotentialMove(i, new Position(adjacent[3]), new Position(adjacent[1])));
            }
        }

        //Create list of children to add nodes to
        ArrayList<BoxSolutionNode> children = new ArrayList<>();

        //Do a flood fill to mark which moves are accessible to the player
        FloodFill.search(topLeft, level, boxPositions, moves);

        //for (PotentialMove p : moves) {
        //    System.out.println("(" + p.box + ") Player pos: " + p.playerPosition + ", Box pos: " + p.newBoxPosition + ", " + p.accessible);
        //}

        //Loop through all potential moves
        for (PotentialMove move : moves) {
            //Skip to the next move if this one is not accessible
            if (!move.accessible) {
                continue;
            }

            //Create a new child node
            BoxSolutionNode child = new BoxSolutionNode();

            //Copy parents box positions for the child
            Position[] childBoxPositions = new Position[boxPositions.length];
            for (int i = 0; i < boxPositions.length; ++i) {
                childBoxPositions[i] = new Position(boxPositions[i]);
            }

            //Move the box to the new position in the child
            childBoxPositions[move.box].set(move.newBoxPosition);

            //Pack all the new box positions into the child's memory
            child.packBoxPositions(childBoxPositions, move.box);

            //Do flood fill to determine the top left-most position accessible
            child.topLeft = FloodFill.search(boxPositions[move.box], level, childBoxPositions, null);

            //Add the child to the array
            children.add(child);
        }

        //Return any valid children generated
        return children;
    }

    /** Returns the heuristic value for this node given a set of goal positions */
    public short calculateHeuristic(Position[] goals) {
        //Set h to 0 to start with
        short h = 0;

        //Unpack the position of all the boxes
        Position[] boxPositions = unpackBoxPositions();

        //Loop through all boxes
        for (Position b : boxPositions) {
            //Calculate the minimum distance from the box to any goal
            int min = Integer.MAX_VALUE;
            for (Position g : goals) {
                min = Math.min(min, Position.manhattan(b, g));
            }

            //Add that distance onto h
            h += min;
        }

        //Return the final value of h
        return h;
    }

    /** Returns if this node matches a given goal state */
    public boolean isGoalState(Position[] goals) {
        //Unpack box positions and check if it equals the array of goal positions
        int goalsMatched = 0;
        for (Position boxPos : unpackBoxPositions()) {
            for (Position goalPos : goals) {
                if (goalPos.equals(boxPos)) {
                    goalsMatched += 1;
                    break;
                }
            }
        }
        return goalsMatched == goals.length;
    }

    /** Packs both the positions of up to 4 boxes and the last box moved into an int */
    public void packBoxPositions(Position[] boxes, int lastBoxMoved) {
        //Start the int as 32 '0' bits
        boxPositionsPacked = 0;

        //Place box positions sequentially into the packed int (bits 9-32)
        for (int i = 0; i < boxes.length; ++i) {
            boxPositionsPacked |= (boxes[i].y - 1) << 3 | (boxes[i].x - 1);
            boxPositionsPacked <<= 6;
        }

        //Next place the number of boxes (bits 3-8)
        boxPositionsPacked |= boxes.length;
        boxPositionsPacked <<= 2;

        //Lastly place the index of the last moved box (bits 1-2)
        boxPositionsPacked |= lastBoxMoved;
    }

    /** Gets the positions of the boxes for a node given a valid packed position int */
    public Position[] unpackBoxPositions() {
        //Shift packed to get rid of first 2 bits
        long packed = boxPositionsPacked >> 2;

        //Get the number of boxes
        int numberBoxes = (int)packed & numberBoxesBitmask;

        //Create array for their positions
        Position[] unpacked = new Position[numberBoxes];

        //Loop through and retrieve the positions of the boxes
        for (int i = numberBoxes - 1; i >= 0; --i) {
            packed >>= 6;
            unpacked[i] = new Position((int)(packed & coordBitmask) + 1, (int)((packed >> 3) & coordBitmask) + 1);
        }
        return unpacked;
    }

    /** Gets the index of the last moved box for a node given a valid packed position int */
    public int unpackLastBoxMoved() {
        //AND the packed int with a particular mask
        return (int)boxPositionsPacked & lastBoxBitmask;
    }

    /** Check if a given Position is contained in a Position array */
    private static boolean contains(Position[] pArray, Position pCheck) {
        for (Position p : pArray) {
            if (pCheck.equals(p)) {
                return true;
            }
        }
        return false;
    }

    //Override equals to allow comparisons between nodes to be accurate
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || !(o instanceof BoxSolutionNode))
            return false;
        BoxSolutionNode node = (BoxSolutionNode) o;
        return (boxPositionsPacked >>> 6) == (node.boxPositionsPacked >>> 6)
                && topLeft.equals(node.topLeft);
    }

    //Create a custom hash based on the value of the packed box positions int
    @Override
    public int hashCode() {
        return (int)boxPositionsPacked >>> 8;
    }

    //Create a custom string conversion for ease of display
    @Override
    public String toString() {
        //Unpack box positions
        Position[] boxPositions = unpackBoxPositions();

        //Start the string with an open brace
        String str = "{ ";

        //Loop through all box positions, adding their string value to this one (comma delimed)
        for (Position p : boxPositions) {
            str += p + ", ";
        }

        //Finally add the player position and the cost of the node before closing the brace
        str += "Player: " + topLeft + ", f: " + f + " }";
        return str;
    }
}
