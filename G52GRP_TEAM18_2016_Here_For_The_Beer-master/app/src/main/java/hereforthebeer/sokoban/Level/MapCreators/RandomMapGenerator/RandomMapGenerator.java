package hereforthebeer.sokoban.Level.MapCreators.RandomMapGenerator;

import hereforthebeer.sokoban.Level.Level;
import hereforthebeer.sokoban.Level.MapCreators.MapCreationInterface;
import hereforthebeer.sokoban.Level.Tile;
import hereforthebeer.sokoban.Util.ServiceLocator;

/**
 * Created by Haoxiang Gao on 08/03/2017.
 */
public class RandomMapGenerator extends MapCreationInterface {
    private char diff = 'M';

    /** Default constructor that sets the boundaries of the map */
    public RandomMapGenerator(char diff) {
        setMapSize(10, 10);
        this.diff = diff;
    }

    //Docs inside interface
    public void generateMap(Level level, int seed) {
        RandomGame r = new RandomGame(diff);
        char[][] map = r.getMap();

        for (int y = 0; y < 10; ++y) {
            for (int x = 0; x < 10; ++x) {
                Tile tile = Level.WALL_TILE;
                switch (map[x][y]) {
                    case '#':
                        tile = Level.WALL_TILE;
                        break;
                    case ' ':
                        tile = Level.FLOOR_TILE;
                        break;
                    case '.':
                        tile = Level.GOAL_TILE;
                        level.addGoal(x, y);
                        break;
                    case '@':
                        tile = Level.FLOOR_TILE;
                        level.setPlayerStart(x, y);
                        break;
                    case '$':
                        tile = Level.FLOOR_TILE;
                        level.addBox(x, y);
                        break;
                    case 'X':
                        tile = Level.GOAL_TILE;
                        level.addBox(x, y);
                        level.addGoal(x, y);
                        break;
                    case 'o':
                        tile = Level.GOAL_TILE;
                        level.setPlayerStart(x, y);
                        level.addGoal(x, y);
                        break;
                    default:
                        System.out.println("Invalid tile received at " + x + ", " + y + ": '" + map[x][y] + "'");
                        break;
                }
                level.setTile(x, y, tile);
            }
        }
    }
}
