package hereforthebeer.sokoban.Level;

import org.junit.Test;

import hereforthebeer.sokoban.Level.MapCreators.BlankMapCreator;
import hereforthebeer.sokoban.Level.MapCreators.MapCreationInterface;

import static org.junit.Assert.*;

public class LevelTest {
    //Default stuff
    /*private MapCreationInterface mapGenerator = new BlankMapCreator();
    private Level level = new Level(16, 16, mapGenerator, 0);

    @Test
    public void getTile() throws Exception {
        assertEquals(Level.WALL_TILE, level.getTile(0, 0));
    }

    @Test
    public void setTile() throws Exception {
        level.setTile(1, 1, Level.WALL_TILE);
        assertEquals(Level.WALL_TILE, level.getTile(1, 1));

        level.setTile(1, 1, Level.FLOOR_TILE);
        assertEquals(Level.FLOOR_TILE, level.getTile(1, 1));
    }

    @Test
    public void getPlayerStart() throws Exception {
        assertEquals(8, level.getPlayerStart().x);
        assertEquals(8, level.getPlayerStart().y);
    }

    @Test
    public void setPlayerStart() throws Exception {
        level.setPlayerStart(3, 2);
        assertEquals(3, level.getPlayerStart().x);
        assertEquals(2, level.getPlayerStart().y);
    }*/
}