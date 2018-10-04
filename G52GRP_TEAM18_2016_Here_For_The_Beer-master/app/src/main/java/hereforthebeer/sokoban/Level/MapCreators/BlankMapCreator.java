package hereforthebeer.sokoban.Level.MapCreators;

import hereforthebeer.sokoban.Level.Level;

/**
 * Created by James on 18/11/2016 <p\>
 * Creates a blank map for use inside the Level object
 */
public class BlankMapCreator extends MapCreationInterface {
    //Docs inside interface
    public void generateMap(Level level, int seed) {
        //Fill the level with walls around the outside and floor on the inside
        for (int y = 0; y < height; ++y)
            for (int x = 0; x < width; ++x)
                if ((x + 1) % width <= 1 || (y + 1) % height <= 1)
                    level.setTile(x, y, Level.WALL_TILE);
                else
                    level.setTile(x, y, Level.FLOOR_TILE);

        level.setPlayerStart(width / 2, height / 2);
        level.addBox(2, 1);
        level.setTile(1, 1, Level.GOAL_TILE);
    }
}
