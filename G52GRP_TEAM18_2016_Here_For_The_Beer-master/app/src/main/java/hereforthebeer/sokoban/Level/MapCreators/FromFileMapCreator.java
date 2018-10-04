package hereforthebeer.sokoban.Level.MapCreators;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

import hereforthebeer.sokoban.Level.Level;
import hereforthebeer.sokoban.Level.Tile;
import hereforthebeer.sokoban.Util.Position;
import hereforthebeer.sokoban.Util.ServiceLocator;

/**
 * Created by James on 22/11/2016.
 * This object generates a map from a given InputStream
 */
public class FromFileMapCreator extends MapCreationInterface {

    //Save the map tiles
    ArrayList<Tile> map = new ArrayList<>();

    //Save the position the player spawns
    Position playerStartPosition = new Position();

    //Save the box positions
    ArrayList<Position> boxPositions = new ArrayList<>();
    ArrayList<Position> goalPositions = new ArrayList<>();

    /**
     * This is the only constructor for this specific type of object
     * @param file The InputStream that you are loading from
     */
    public FromFileMapCreator(InputStream file) {
        //Create a scanner from the stream
        Scanner in = new Scanner(file);

        //Grab the width and the height from the file first
        String line;
        int width, height;

        //If numbers not found then return early
        try {
            line = in.nextLine();
            width = Integer.valueOf(line);

            line = in.nextLine();
            height = Integer.valueOf(line);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        //Update the map size internally
        setMapSize(width, height);

        //Loop through the width and height
        for (int y = 0; y < height; ++y) {
            line = in.nextLine();
            for (int x = 0; x < width; ++x) {
                //Generate tiles
                char t = line.charAt(x);
                switch (t) {
                    case '#': //Wall tile
                        map.add(Level.WALL_TILE);
                        break;
                    case '.': //Floor tile
                        map.add(Level.FLOOR_TILE);
                        break;
                    case 'p': //Player start
                        map.add(Level.FLOOR_TILE);
                        playerStartPosition.set(x, y);
                        break;
                    case '_': //Goal tile
                        map.add(Level.GOAL_TILE);
                        goalPositions.add(new Position(x, y));
                        break;
                    case 'b': //Box start
                        map.add(Level.FLOOR_TILE);
                        boxPositions.add(new Position(x, y));
                        break;
                    case 'B': //Box on top of a goal tile
                        map.add(Level.GOAL_TILE);
                        boxPositions.add(new Position(x, y));
                        break;
                    case 'P': //Player on top of a goal tile
                        map.add(Level.GOAL_TILE);
                        playerStartPosition.set(x, y);
                        break;
                    default: //Everything else
                        System.out.println("ERROR: Tile not recognised (" + t + ")");
                        break;
                }
            }
        }
    }

    //Docs inside interface
    public void generateMap(Level level, int seed) {
        //Loop through the stored map and set the tiles
        for (int i = 0, n = map.size(); i < n; ++i)
            level.setTile(i % width, i / width, map.get(i));

        //Set the player start point
        level.setPlayerStart(playerStartPosition);

        //Add all boxes to the level
        for (Position pos : boxPositions)
            level.addBox(pos);

        //Add all goals to the level
        for (Position pos : goalPositions)
            level.addGoal(pos);
    }
}
