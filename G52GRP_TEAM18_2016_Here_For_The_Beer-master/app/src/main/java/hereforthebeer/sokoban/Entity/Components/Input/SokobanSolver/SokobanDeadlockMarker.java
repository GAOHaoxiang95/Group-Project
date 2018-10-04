package hereforthebeer.sokoban.Entity.Components.Input.SokobanSolver;

import hereforthebeer.sokoban.Level.Level;
import hereforthebeer.sokoban.Level.Tile;
import hereforthebeer.sokoban.Util.ServiceLocator;

/**
 * Created by James on 20/04/2017.
 */
class SokobanDeadlockMarker {
    public static boolean[] getDeadlockedTiles(Level level) {
        //Ease of access variables
        int width = level.getWidth();
        int height = level.getHeight();

        //Create array of booleans to show if that tile is deadlocked
        boolean[] deadlocked = new boolean[width * height];

        //Loop through inside tiles of the map
        for (int y = 1; y < height - 1; ++y) {
            for (int x = 1; x < width - 1; ++x) {
                Tile t = level.getTile(x, y);
                //If tile is empty, check if it is deadlocked
                if (!(t.isCollidable() || t.isGoal())) {
                    //Get if their is a collision vertically or horizontally
                    boolean horizontal = level.getTile(x - 1, y).isCollidable() || level.getTile(x + 1, y).isCollidable();
                    boolean vertical = level.getTile(x, y - 1).isCollidable() || level.getTile(x, y + 1).isCollidable();

                    //Mark the tile as deadlocked if both have a collision
                    deadlocked[x + y * width] = vertical && horizontal;
                } else {
                    deadlocked[x + y * width] = false;
                }
            }
        }

        //Return blocked array
        return deadlocked;
    }

    public static void printDeadlockedLevel(Level level) {
        boolean[] deadlocked = getDeadlockedTiles(level);
        int width = level.getWidth();
        int height = level.getHeight();

        ServiceLocator.getLog().println("Deadlocked tile layout: ");
        for (int y = 0; y < height; ++y) {
            String s = "";
            for (int x = 0; x < width; ++x) {
                s += (deadlocked[x + y * width] ? 'X' : level.getTile(x, y));
                s += ' ';
            }
            ServiceLocator.getLog().println(s);
        }
    }
}
