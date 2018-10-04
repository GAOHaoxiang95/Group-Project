package hereforthebeer.sokoban.Level.MapCreators.RandomMapGenerator;
import java.util.Random;

/**
 * Created by Haoxiang Gao on 2017/2/22.
 */


public class EmptyMapGenerator {

    Templates template;
    Prototypes prototype;
    private int high;
    private int length;
    private char[][] map;
    private Random random;

    public EmptyMapGenerator()
    {
        random = new Random();
        int p = random.nextInt(2);
        prototype = new Prototypes(p);

        this.high = prototype.getHigh();
        this.length = prototype.getLength();
        this.map = prototype.getPrototype();
        generateMap();
    }

    /*
     * @param newTemplate The new Templates is added to the current map
     */
    private void addTemplate(Templates newTemplate)
    {
        char[][] t = newTemplate.getTemplate();
        int y = (int)(Math.random()*(this.high - newTemplate.getHigh() - 1)) + 1;
        int x = (int)(Math.random()*(this.length - newTemplate.getLength() - 1)) + 1;
        for(int i = y; i < y + newTemplate.getHigh(); i++)
        {
            for(int j = x; j < x + newTemplate.getLength(); j++)
            {
                map[i][j] = t[i-y][j-x];
            }
        }
    }

    /*
     * random generate new map
     */
    public void generateMap()
    {
        int t;
        for(int i = 0; i < 3; i++)
        {
            t = random.nextInt(3);
            this.addTemplate(new Templates(t));
        }

    }

    public int getHigh() {
        return high;
    }

    public int getLength() {
        return length;
    }

    public char[][] getMap() {
        return map;
    }



}