package hereforthebeer.sokoban.Entity.Components.Input.NewAStarSolver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;

import hereforthebeer.sokoban.Level.Level;
import hereforthebeer.sokoban.Util.Position;
import hereforthebeer.sokoban.Util.ServiceLocator;

/**
 * Created by James on 07/03/2017.
 * This is an optimized version of the AStar used to solve sokoban levels
 */
public class BoxSolution {
    public static ArrayList<SolutionStep> solve(Level level, Position[] boxes, Position player, boolean[] deadlocked) {

        //Create closed list to check if a node has already been visited
        HashSet<BoxSolutionNode> closedList = new HashSet<>();

        //Create the open list to store states waiting to be expanded
        PriorityQueue<BoxSolutionNode> openList = new PriorityQueue<>(1, new Comparator<BoxSolutionNode>() {
            @Override
            public int compare(BoxSolutionNode a, BoxSolutionNode b) {
                return a.f - b.f;
            }
        });

        //Get the list of goal positions from the level and put it into an array
        ArrayList<Position> tempGoals = level.getGoalPositions();
        Position[] goals = tempGoals.toArray(new Position[tempGoals.size()]);

        //Create the starting node with a last box pushed of 0
        BoxSolutionNode start = new BoxSolutionNode();
        start.packBoxPositions(boxes, 0);
        start.parent = null;
        start.topLeft = FloodFill.search(player, level, boxes, null);
        start.g = 0;
        start.f = start.calculateHeuristic(goals);

        //Add the start node to the open list
        openList.add(start);

        //Debug values
        int boxesCreated = 0;
        int boxesExpanded = 0;
        int largestOpenSize = 0;
        long startTime = System.currentTimeMillis();

        //Loop until no more nodes to expand
        while (!openList.isEmpty()) {
            //Get the lowest f cost node
            BoxSolutionNode current = openList.poll();

            ++boxesExpanded;
            largestOpenSize = Math.max(openList.size(), largestOpenSize);

            //try { Thread.sleep(100); } catch (Exception e) { e.printStackTrace(); }
            //System.out.println("Expanding: " + current);
            //level.printMapToConsole(true, current.unpackBoxPositions(), current.topLeft);

            //Check if the node is a goal state
            if (current.isGoalState(goals)) {
                System.out.println("Nodes Created: " + boxesCreated);
                System.out.println("Nodes Expanded: " + boxesExpanded);
                System.out.println("Open List Max Size: " + largestOpenSize);
                System.out.println("Time Taken: " + (System.currentTimeMillis() - startTime));
                return getPath(current);
            }

            //Add the node to the closed list
            closedList.add(current);

            //Expand children
            ArrayList<BoxSolutionNode> children = current.getChildren(level, deadlocked);
            for (BoxSolutionNode child : children) {
                //Do not process the node if it has been processed already
                if (!closedList.contains(child) && !openList.contains(child)) {
                    //Set child state
                    child.parent = current;
                    child.g = current.g + 1;
                    child.f = child.calculateHeuristic(goals) + child.g;

                    //Add the child to the open list
                    ++boxesCreated;
                    openList.add(child);
                    //System.out.println("Adding Child: " + child);
                }
            }
        }

        //All reached node have been expanded and none are the goal state so return null.
        return null;
    }

    /** Returns the moves needed to get to the given node from the start */
    private static ArrayList<SolutionStep> getPath(BoxSolutionNode current) {
        //Create path array
        ArrayList<SolutionStep> path = new ArrayList<>();

        //Assign places to store the positions for all boxes of both current and it's parent
        Position[] parentBoxPositions;
        Position[] currentBoxPositions = current.unpackBoxPositions();

        //Loop until current doesn't have a parent
        while (current.parent != null) {
            //Get which box moved to get to the current state
            int boxMoved = current.unpackLastBoxMoved();

            //Get the positions of all the boxes for the parent
            parentBoxPositions = current.parent.unpackBoxPositions();

            //Get the move that was executed to get to the current state from the parent state
            Position delta = currentBoxPositions[boxMoved].difference(parentBoxPositions[boxMoved]);

            //Add the move to the list of moves
            path.add(new SolutionStep(boxMoved, delta));

            //Set the current box positions to point to the parents to save unpacking twice
            currentBoxPositions = parentBoxPositions;

            //Set current to be refering to the parent
            current = current.parent;
        }

        //The path is now created but is in the wrong direction so we reverse its order
        Collections.reverse(path);

        //Return the final path
        return path;
    }

    /** This is a data class used to keep track of a single step in a solution */
    public static class SolutionStep {
        //Store the index of the box that moved
        public int box;

        //Store the change in position of the box
        public Position delta;

        /** Standard assignment constructor */
        public SolutionStep(int boxIndex, Position boxDelta) {
            box = boxIndex;
            delta = boxDelta;
        }
    }
}
