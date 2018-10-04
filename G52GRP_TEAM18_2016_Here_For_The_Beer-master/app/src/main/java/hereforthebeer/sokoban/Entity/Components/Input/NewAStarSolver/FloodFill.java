package hereforthebeer.sokoban.Entity.Components.Input.NewAStarSolver;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

import hereforthebeer.sokoban.Level.Level;
import hereforthebeer.sokoban.Util.Position;

/**
 * Created by James on 07/03/2017.
 * This is an optimized version of the flood fill algorithm used to
 * find target tiles the player can access.
 */
public class FloodFill {
    /**
     * Applies a flood fill search from the start position, marking any given goal tiles as
     * accessible if they are found.
     * @param start Start position for the flood fill
     * @param level Level with which to fill
     * @param boxes Position of boxes to collide against
     * @param goals List of goals to try to fill to
     * @return Two top left-most valid positions found by the flood fill
     */
    public static Position search(Position start, Level level, Position[] boxes, ArrayList<BoxSolutionNode.PotentialMove> goals) {
        //Store size of level
        int width = level.getWidth();
        int size = width * level.getHeight();

        //Store the state of all nodes in the map
        //   -2  -> Goal
        //   -1  -> Collidable
        //    0  -> Not processed
        // 1-127 -> Processed (Value is manhattan distance to start tile offset by +1)
        int[] node = new int[size];
        boolean[] goal = new boolean[size];

        if (goals != null) {
            for (BoxSolutionNode.PotentialMove move : goals) {
                goal[move.playerPosition.x + move.playerPosition.y * width] = true;
            }
        }

        //Keep track of how many goals have been reached
        int goalsReached = 0;

        //Loop through all tiles on the map and set colliding nodes state to -1
        for (int i = 0; i < size; ++i) {
            //If the tile collides then set the node value to -1
            if (level.getTile(i % width, i / width).isCollidable()) {
                node[i] = -1;
            }
        }

        //Loop through all boxes and mark their positions as collidable
        for (Position boxPosition : boxes) {
            node[boxPosition.x + boxPosition.y * width] = -1;
        }

        //Create queue for storing nodes in the open list
        ArrayDeque<Integer> openList = new ArrayDeque<>();

        //Add the starting position to the open list and set its state to 1
        int startIndex = start.x + start.y * width;
        openList.add(startIndex);
        node[startIndex] = 1;

        //Store the top left-most index [0] and the second top left-most index [1]
        int topLeft = startIndex;

        //Loop until all nodes processed
        while (!openList.isEmpty()) {
            //Get next node in the queue
            int current = openList.pollFirst();

            //Update the top left-most position if possible
            if (current < topLeft) {
                topLeft = current;
            }

            //Check if the current position is a goal position
            if (goal[current]) {
                //Loop through all goals to find the goals that point to that position
                for (BoxSolutionNode.PotentialMove goalPos : goals) {
                    //Check if the current node is the same position as a goal
                    if (current == goalPos.playerPosition.x + goalPos.playerPosition.y * width) {
                        //Update goal state
                        goalPos.accessible = true;
                        goalPos.distance = node[current] - 1;

                        //Increase the number of goals reached
                        goalsReached += 1;

                        //Check if all goals have been reached
                        if (goalsReached == goals.size()) {
                            //Return the top left position reached
                            return new Position(topLeft % width, topLeft / width);
                        }
                    }
                }
            }

            //Make array of children positions
            int children[] = { current - width, current + 1, current + width, current - 1 };

            //Add all children that are valid and not yet processed to the open list
            for (int child : children) {
                checkValidAndAdd(child, node[current] + 1, node, openList);
            }
        }

        //Return the top left-most position found
        return new Position(topLeft % width, topLeft / width);
    }

    /**
     * Adds the given child to the open list if it is valid
     * @param child Map index of child node
     * @param depth Depth of child node
     * @param node Map state array
     * @param openList Open list of non-expanded nodes
     */
    private static void checkValidAndAdd(int child, int depth, int node[], Queue<Integer> openList) {
        //Surround state check in try, catch to stop children generated outside the map being added
        try {
            //Check if the child is not yet processed
            if (node[child] == 0) {
                //Add child to the open list and update its state
                openList.add(child);
                node[child] = depth;
            }
        } catch(ArrayIndexOutOfBoundsException e) {}
    }
}
