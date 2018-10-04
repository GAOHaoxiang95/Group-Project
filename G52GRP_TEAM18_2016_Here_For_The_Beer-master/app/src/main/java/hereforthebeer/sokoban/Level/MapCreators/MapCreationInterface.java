package hereforthebeer.sokoban.Level.MapCreators;

import hereforthebeer.sokoban.Level.Level;
import hereforthebeer.sokoban.Level.Tile;

/**
 * Created by James on 18/11/2016 <p\>
 * This interface allows the creation of MapCreation classes to provide Level objects with varying
 * map layouts based off a seeded random generator
 */
public abstract class MapCreationInterface {

    //Store boundaries of the map
    int width, height;

    /**
     * Generates a map layout for a Level, inserts the map, the boxes and sets the player start pos
     * @param level A reference to the level that the tiles will be put into
     * @param seed An integer to seed random generators for consistent layout design
     */
    public abstract void generateMap(Level level, int seed);

    /**
     * Sets the size of the map to be generated
     * @param width The width of the map in tiles
     * @param height The height of the map in tiles
     */
    public void setMapSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    /** Returns the width of the map to be generated */
    public int getWidth() {
        return width;
    }

    /** Returns the height of the map to be generated */
    public int getHeight() {
        return height;
    }
}
