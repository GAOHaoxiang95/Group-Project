package hereforthebeer.sokoban.Entity.Components.Input.SokobanSolver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;

import hereforthebeer.sokoban.Level.Level;
import hereforthebeer.sokoban.Util.Position;
import hereforthebeer.sokoban.Util.ServiceLocator;

/**
 * Created by James on 18/04/2017.
 */
class SokobanSolver {

    //Track some data
    private static int NODES_GENERATED = 1;
    private static int NODES_EXPANDED = 0;
    private static int MAX_OPEN_SIZE = 0;
    private static long START_TIME = System.currentTimeMillis();

    /** Data class for tracking each step in the solution **/
    public static class SolutionStep {
        //Stores the id of the moved box
        public int box;

        //Store the location the box is to be moved to
        public Position move;

        //Constructor for creation
        public SolutionStep(int box, Position move) {
            this.box = box;
            this.move = move;
        }
    }

    /**
     * Attempts to generate a solution to a given sokoban level.
     * @param level The level to solve
     * @param boxPositions The position of the boxes in the level
     * @param playerPosition The position of the player in the level
     * @param deadlocked An array of pre-processed deadlocked positions
     * @return Either a list of solutionSteps or NULL if no path is found
     */
    public static ArrayList<SolutionStep> solve(Level level, ArrayList<Position> boxPositions, Position playerPosition, boolean[] deadlocked) {
        //Create a way to track previously expanded nodes
        HashSet<SokobanNode> closedSet = new HashSet<>();

        //Create open list as a priority queue to store states waiting to be expanded in order
        PriorityQueue<SokobanNode> openList = new PriorityQueue<>(1, new Comparator<SokobanNode>() {
            @Override
            public int compare(SokobanNode a, SokobanNode b) {
                return a.f - b.f;
            }
        });

        //Store the positions of the goals
        ArrayList<Position> goalPositions = level.getGoalPositions();

        //Update state inside SokobanNode class
        SokobanNode.BOX_COUNT = boxPositions.size();
        SokobanNode.WIDTH = level.getWidth();

        //Create the starting node
        SokobanNode startNode = new SokobanNode();
        startNode.parent = null;
        startNode.g = 0;

        //Set box positions and calculate final cost
        startNode.setBoxPositions(boxPositions);
        startNode.f = startNode.getHeuristic(goalPositions);

        //Do one iteration of flood fill to determine what adjacent tiles the player can reach
        ArrayList<Position> adjacent = startNode.getAdjacentBoxPositions(SokobanNode.ADJACENT);
        boolean[] adjacentReachable = new boolean[adjacent.size()];
        startNode.setPlayerPosition(SokobanFloodFill.search(level, playerPosition, boxPositions, adjacent, adjacentReachable));
        startNode.setAdjacentReachable(adjacentReachable);

        //Insert the starting node into the queue
        openList.add(startNode);

        //Collect data about the algorithm
        //NODES_GENERATED = 1;
        //NODES_EXPANDED = 0;
        //MAX_OPEN_SIZE = 0;
        //START_TIME = System.currentTimeMillis();

        //Loop until no more nodes to expand
        while (!openList.isEmpty()) {
            //Get the lowest f cost node
            //MAX_OPEN_SIZE = Math.max(MAX_OPEN_SIZE, openList.size());
            SokobanNode currentNode = openList.poll();
            //NODES_EXPANDED += 1;

            //System.out.println("Current: " + currentNode);
            //level.printMapToConsole(true, currentNode.getBoxPositions(), currentNode.getPlayerPosition());

            //Check if current node is the goal state
            if (currentNode.isGoalState(goalPositions)) {
                //Print stats
                //ServiceLocator.getLog().println("Nodes Generated: " + NODES_GENERATED);
                //ServiceLocator.getLog().println("Nodes Expanded: " + NODES_EXPANDED);
                //ServiceLocator.getLog().println("Max Open Size: " + MAX_OPEN_SIZE);
                //ServiceLocator.getLog().println("Time Taken: " + (System.currentTimeMillis() - START_TIME) + "ms");

                //Return obtained path
                return getPath(currentNode);
            }

            //Add the node to the closed set
            closedSet.add(currentNode);

            //Expand all children of the current node
            ArrayList<SokobanNode> children = currentNode.getChildren(level, deadlocked);
            for (SokobanNode child : children) {
                //Set child state
                child.parent = currentNode;
                child.g = currentNode.g + 1;

                //Get state from the node that is already set
                ArrayList<Position> childBoxPositions = child.getBoxPositions();

                //Get adjacent box positions for child and create an array for storing which are accessible
                ArrayList<Position> childAdjacent = child.getAdjacentBoxPositions(SokobanNode.ADJACENT);
                boolean[] childAdjacentReachable = new boolean[adjacent.size()];

                //Do flood fill to get player reference and find out what adjacent positions are reachable
                child.setPlayerPosition(SokobanFloodFill.search(level, child.getPlayerPosition(), childBoxPositions, childAdjacent, childAdjacentReachable));

                //Pack adjacent positions into child node state
                child.setAdjacentReachable(childAdjacentReachable);

                //Only expand node if its not already part of the open list or the closed set
                if (!closedSet.contains(child) && !openList.contains(child)) {
                    //Set heuristic value
                    child.f = child.getHeuristic(goalPositions) + child.g;

                    //Add the child to the open list
                    openList.add(child);
                    //NODES_GENERATED += 1;
                }
            }
        }

        //If no path is found return NULL
        return null;
    }

    /** Returns the moves needed to get to the given node from the start */
    private static ArrayList<SolutionStep> getPath(SokobanNode current) {
        //Create path array
        ArrayList<SolutionStep> path = new ArrayList<>();

        //Assign places to store the positions for all boxes of both current and it's parent
        ArrayList<Position> parentBoxPositions;
        ArrayList<Position> currentBoxPositions = current.getBoxPositions();

        //Loop until current doesn't have a parent
        while (current.parent != null) {
            //Get which box moved to get to the current state
            int boxMoved = current.getLastBoxMoved();

            //Get the positions of all the boxes for the parent
            parentBoxPositions = current.parent.getBoxPositions();

            //Get the move that was executed to get to the current state from the parent state
            Position delta = currentBoxPositions.get(boxMoved).difference(parentBoxPositions.get(boxMoved));

            //Add the move to the list of moves
            path.add(new SolutionStep(boxMoved, delta));

            //Set the current box positions to point to the parents to save unpacking twice
            currentBoxPositions = parentBoxPositions;

            //Set current to be referring to the parent
            current = current.parent;
        }

        //The path is now created but is in the wrong direction so we reverse its order
        Collections.reverse(path);

        //Return the final path
        return path;
    }
}
