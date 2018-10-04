package hereforthebeer.sokoban.Level;

/**
 * Created by James on 18/11/2016 <p\>
 * Level stores a one dimentional array of tiles that correspond to
 * each space on the map.
 */

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;

import hereforthebeer.sokoban.Entity.Entity;
import hereforthebeer.sokoban.Level.MapCreators.MapCreationInterface;
import hereforthebeer.sokoban.Util.Position;
import hereforthebeer.sokoban.Util.ServiceLocator;

/**
 * Stores a configuration of Tile objects to represent a 2 dimensional map
 */
public class Level {
    //Create member variables
    private int width, height;
    private Tile[] map;
    public int offsetx, offsety;

    //Keep a reference to cell size
    public int CELL_SIZE = -1;

    //Save where the player starts
    private Position playerStart = new Position();

    //Save the positions of the boxes
    private ArrayList<Position> boxPositions = new ArrayList<>();

    //Save the positions of the goals
    private ArrayList<Position> goalPositions = new ArrayList<>();

    //Create tile objects for map to reference
    public static Tile WALL_TILE;
    public static Tile FLOOR_TILE;
    public static Tile GOAL_TILE;

    /**
     * Standard constructor for creating a new map
     * @param mapGenerator An object that implements the MapCreationInterface to create map layouts
     * @param seed An integer to seed random generators for consistent layout design
     */
    public Level(MapCreationInterface mapGenerator, int seed) {
        //Assign values for map width and height
        width = mapGenerator.getWidth();
        height = mapGenerator.getHeight();

        //Allocate memory for the map tiles
        map = new Tile[width * height];

        //Generate the map based on the map generator
        mapGenerator.generateMap(this, seed);
    }

    public void render(Canvas c) {
        for (int i = 0; i < width * height; ++i) {
            map[i].render(c, i % width, i / width, CELL_SIZE, offsetx, offsety);
        }

    }

    /**
     * Returns a reference to the Tile objects stored at a specific x, y position
     * @param x X co-ordinate of tile in Level
     * @param y Y co-ordinate of tile in Level
     * @return Returns a reference to the Tile object
     */
    public Tile getTile(int x, int y) {
        return map[y * width + x];
    }

    /** Returns a reference to the Tile objects stored at a specific position
     * @see #getTile(int x, int y)*/
    public Tile getTile(Position p) {
        return getTile(p.x, p.y);
    }

    /** Returns the reference to the map for ease of iteration */
    public Tile[] getMap() { return map; }

    /**
     * Sets a tile on the map
     * @param x X co-ordinate of tile in Level
     * @param y Y co-ordinate of tile in Level
     * @param t Tile reference you wish to use
     */
    public void setTile(int x, int y, Tile t) {
        map[y * width + x] = t;
    }

    /** Adds a box to the level at Position p */
    public void addBox(Position p) {
        boxPositions.add(p);
    }
    /** Adds a box to the level at Position p */
    public void addBox(int x, int y) {
        addBox(new Position(x, y));
    }

    /** Adds a goal position to the level */
    public void addGoal(int x, int y) {
        addGoal(new Position(x, y));
    }
    /** Adds a goal position to the level */
    public void addGoal(Position pos) {
        goalPositions.add(pos);
    }

    /** Returns all the box starting positions */
    public ArrayList<Position> getBoxStartPositions() {
        return new ArrayList<Position>(boxPositions);
    }

    /** Returns the positions of all the goal tiles */
    public ArrayList<Position> getGoalPositions() {
        return new ArrayList<Position>(goalPositions);
    }

    /** Returns the Position that the player starts at */
    public Position getPlayerStart() {
        return playerStart;
    }

    /** Sets the player start position for this level */
    public void setPlayerStart(int x, int y) {
        playerStart.set(x, y);
    }
    /** Sets the player start position for this level */
    public void setPlayerStart(Position p) {
        playerStart.set(p);
    }

    /** Gets the width of the level */
    public int getWidth() {
        return width;
    }

    /** Gets the height of the level */
    public int getHeight() {
        return height;
    }

    public void printMapToConsole(boolean verbose, Position[] boxes, Position p) {
        if (verbose) {
            ServiceLocator.getLog().print("  ");
            for (int i = 0; i < width; ++i)
                ServiceLocator.getLog().print(i + " ");
            ServiceLocator.getLog().println("");
            ServiceLocator.getLog().print("0 ");
        }

        Position thisPos = new Position();
        for (int i = 0, n = width * height; i < n; ++i) {
            String c = map[i].toString();
            thisPos.set(i % width, i / width);

            for (Position b : boxes)
                if (b.equals(thisPos))
                    c = "b";

            if (p.equals(thisPos))
                c = "p";

            ServiceLocator.getLog().print(c + " ");
            if ((i + 1) % width == 0) {
                ServiceLocator.getLog().println("");
                if (verbose && i != width * height - 1)
                    ServiceLocator.getLog().print((i + 1) / width + " ");
            }
        }
    }

    /**
     * Draws the map stored in Level to console based on each Tile's toString()
     */
    public void printMapToConsole(boolean verbose, ArrayList<Entity> boxes, Entity p) {
        if (verbose) {
            ServiceLocator.getLog().print("  ");
            for (int i = 0; i < width; ++i)
                ServiceLocator.getLog().print(i + " ");
            ServiceLocator.getLog().println("");
            ServiceLocator.getLog().print("0 ");
        }

        Position thisPos = new Position();
        for (int i = 0, n = width * height; i < n; ++i) {
            String c = map[i].toString();
            thisPos.set(i % width, i / width);

            for (Entity b : boxes)
                if (b.getPosition().equals(thisPos))
                    if (c == "_")
                        c = "B";
                    else
                        c = "b";

            if (p.getPosition().equals(thisPos))
                if (c == "_")
                    c = "P";
                else
                    c = "p";

            ServiceLocator.getLog().print(c + " ");
            if ((i + 1) % width == 0) {
                ServiceLocator.getLog().println("");
                if (verbose && i != width * height - 1)
                    ServiceLocator.getLog().print((i + 1) / width + " ");
            }
        }
    }

    /**
     * Draws the map stored in Level to console based on each Tile's toString()
     */
    public void printMapToConsole(boolean verbose, ArrayList<Position> boxes, Position p) {
        if (verbose) {
            ServiceLocator.getLog().print("  ");
            for (int i = 0; i < width; ++i)
                ServiceLocator.getLog().print(i + " ");
            ServiceLocator.getLog().println("");
            ServiceLocator.getLog().print("0 ");
        }

        Position thisPos = new Position();
        for (int i = 0, n = width * height; i < n; ++i) {
            String c = map[i].toString();
            thisPos.set(i % width, i / width);

            for (Position b : boxes)
                if (b.equals(thisPos))
                    if (c == "_")
                        c = "B";
                    else
                        c = "b";

            if (p.equals(thisPos))
                if (c == "_")
                    c = "P";
                else
                    c = "p";

            ServiceLocator.getLog().print(c + " ");
            if ((i + 1) % width == 0) {
                ServiceLocator.getLog().println("");
                if (verbose && i != width * height - 1)
                    ServiceLocator.getLog().print((i + 1) / width + " ");
            }
        }
    }
}