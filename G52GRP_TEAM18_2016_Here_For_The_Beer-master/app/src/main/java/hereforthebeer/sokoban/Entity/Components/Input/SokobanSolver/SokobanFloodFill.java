package hereforthebeer.sokoban.Entity.Components.Input.SokobanSolver;

import android.support.v4.util.CircularIntArray;

import java.util.ArrayDeque;
import java.util.ArrayList;

import hereforthebeer.sokoban.Level.Level;
import hereforthebeer.sokoban.Util.Position;

/**
 * Created by James on 18/04/2017.
 */
class SokobanFloodFill {
    public static Position search(Level level, Position start, ArrayList<Position> boxes, ArrayList<Position> adjacentBoxPositions, boolean[] adjacentReachable) {
        //Ease of access variables
        int width = level.getWidth();
        int height = level.getHeight();
        int size = width * level.getHeight();

        //Store the map as bytes of info
        //  0 = Not expanded
        //  1 = Closed / Wall
        //  2 = Goal
        byte[] map = new byte[size];

        //Convert to an index array for faster checking
        int numAdjacentPositions = adjacentBoxPositions.size();
        int[] adjPositionsIndexed = new int[numAdjacentPositions];
        for (int i = 0; i < numAdjacentPositions; ++i) {
            Position p = adjacentBoxPositions.get(i);
            adjPositionsIndexed[i] = p.x + p.y * width;
        }

        //Label which spots are accessible
        boolean[] adjacentAccessable = new boolean[adjacentBoxPositions.size()];

        //Label wall positions on the map as 1 and empty tiles as 0
        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                map[x + y * width] = (byte) (level.getTile(x, y).isCollidable() ? 1 : 0);
            }
        }

        //Block off tiles with boxes on
        for (Position p : boxes) {
            map[p.x + p.y * width] = 1;
        }

        //Setup the goal positions on the map as 2
        for (Position p : adjacentBoxPositions) {
            //Only allow the goal to be placed if the area is empty
            int index = p.x + p.y * width;
            if (map[index] == 0) {
                map[index] = 2;
            }
        }

        //30 is chosen as a size that is *big enough* but may need to be
        //increased if map size is increased past 12x12 in the future
        CircularIntArray openList = new CircularIntArray(30);

        //Add starting index to the open list
        openList.addFirst(start.x + start.y * width);

        //Assign the topLeft to be equal to the starting node
        int topLeft = start.x + start.y * width;

        //Iterate until no more nodes to expand
        while (!openList.isEmpty()) {
            //Get first node in queue
            int current = openList.popFirst();

            //Check if the new node is a better option as
            //the reference for player position
            if (current < topLeft) {
                topLeft = current;
            }

            //If the current node position is a goal
            if (map[current] == 2) {
                //Loop through all adjacent positions
                for (int i = 0; i < numAdjacentPositions; ++i) {
                    //If the position is the current node mark it as accessible
                    if (adjPositionsIndexed[i] == current) {
                        adjacentReachable[i] = true;
                    }
                }
            }

            //Expand children from all four accessible tiles around current node
            checkValidAndAdd(current - width, map, openList);
            checkValidAndAdd(current + 1, map, openList);
            checkValidAndAdd(current + width, map, openList);
            checkValidAndAdd(current - 1, map, openList);

            //Mark the current node on the map as expanded
            map[current] = 1;
        }

        //Return the top left-most position accessed by the flood fill
        return new Position(topLeft % width, topLeft / width);
    }

    /** Small helper function to take a child and add it to the open list if valid */
    private static void checkValidAndAdd(int child, byte[] map, CircularIntArray openList) {
        //Add child to the openList if the node isn't closed / a wall
        if (map[child] != 1) {
            openList.addLast(child);
        }
    }
}
