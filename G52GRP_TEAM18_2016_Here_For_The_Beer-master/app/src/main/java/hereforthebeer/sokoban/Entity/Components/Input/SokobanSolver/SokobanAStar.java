package hereforthebeer.sokoban.Entity.Components.Input.SokobanSolver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;

import hereforthebeer.sokoban.Level.Level;
import hereforthebeer.sokoban.Util.Position;

/**
 * Created by James on 20/04/2017.
 */
class SokobanAStar {
    public static ArrayList<Position> getPath(Position startPosition, Position endPosition, Level level, ArrayList<Position> boxPositions) {

        //Ease of access variables
        int width = level.getWidth();
        int height = level.getHeight();

        //Convert start and end positions to index form
        int startIndex = startPosition.x + startPosition.y * width;
        int goalIndex = endPosition.x + endPosition.y * width;

        //Create a map of which to check collision off of
        boolean[] blocked = new boolean[width * height];

        //Loop through map and see mark all collided
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                blocked[x + y * width] = level.getTile(x, y).isCollidable();
            }
        }

        //Add box collision
        for (Position p : boxPositions) {
            blocked[p.x + p.y * width] = true;
        }

        //Create open list that sorts by node cost
        PriorityQueue<Node> openList = new PriorityQueue<>(1, new Comparator<Node>() {
            @Override
            public int compare(Node n1, Node n2) {
                return n1.f - n2.f;
            }
        });

        //Add the starting node to the open list
        Node start = new Node();
        start.position = startIndex;
        start.parent = null;
        start.g = 0;
        start.f = start.getHeuristic(level, goalIndex);

        openList.add(start);

        //Iterate until no more nodes are in the open list
        while (!openList.isEmpty()) {
            //Grab lowest predicted cost node
            Node current = openList.poll();

            //System.out.println("Current: " + current);

            //If this node is at the goal
            if (current.position == goalIndex) {
                //Trace the path back to the start and return it
                return tracePath(level, current);
            }

            //Mark this node as visited
            blocked[current.position] = true;

            //Get the child nodes of the current node
            ArrayList<Node> children = current.getChildren(level, goalIndex, blocked);
            for (Node child : children) {
                //System.out.println("Added Child: " + child);
                openList.add(child);
            }
        }

        //No path found
        return null;
    }

    /** Returns the path found from the start to the end position */
    private static ArrayList<Position> tracePath(Level level, Node end) {
        //Store level width
        int width = level.getWidth();

        //Create path array (Set size to be depth of the tree + 1)
        ArrayList<Position> path = new ArrayList<>(end.g + 1);

        //Iterate through all nodes up the tree
        Node current = end;
        Position currentPosition = new Position(current.position % width, current.position / width);
        Position parentPosition;
        while (current.parent != null) {
            //Assign parent stuff
            Node parent = current.parent;
            parentPosition = new Position(parent.position % width, parent.position / width);

            //Add the delta position to the path
            path.add(currentPosition.difference(parentPosition));

            //Assign next current to be this parent position
            currentPosition = parentPosition;

            //Climb tree one level
            current = current.parent;
        }

        //Reverse path to be ordered start node -> end node
        Collections.reverse(path);

        //Return path
        return path;
    }

    private static class Node {
        int position;
        int f;
        int g;
        Node parent;

        /** Returns a list off all children of this node */
        public ArrayList<Node> getChildren(Level level, int goal, boolean[] blocked) {
            //Store width of the level
            int width = level.getWidth();

            //Add the four adjacent nodes to the children array
            ArrayList<Node> children = new ArrayList<>();
            checkValidAndAdd(position - width, children, level, goal, blocked);
            checkValidAndAdd(position + 1, children, level, goal, blocked);
            checkValidAndAdd(position + width, children, level, goal, blocked);
            checkValidAndAdd(position - 1, children, level, goal, blocked);

            //Return child array
            return children;
        }

        /** Adds a child position to the child array if its valid */
        private void checkValidAndAdd(int newPosition, ArrayList<Node> children, Level level, int goal, boolean[] blocked) {
            //Check if not closed and not collidable
            if (!blocked[newPosition]) {
                //Create child and set state
                Node child = new Node();
                child.position = newPosition;
                child.parent = this;
                child.g = g + 1;
                child.f = child.getHeuristic(level, goal) + child.g;

                //Add child to child list
                children.add(child);
            }
        }

        /** Returns the heuristic for this node */
        public int getHeuristic(Level level, int goal) {
            int width = level.getWidth();
            return Position.manhattan(new Position(position % width, position / width), new Position(goal % width, goal / width));
        }

        @Override
        public String toString() {
            return "{ Pos: {" + position % 8 + ", " + position / 8 + "}, Cost: " + f + " }";
        }
    }
}
