package hereforthebeer.sokoban.Level.MapCreators.RandomMapGenerator;

import hereforthebeer.sokoban.Level.MapCreators.RandomMapGenerator.SolvableMapGenerator;

/**
 * Created by Haoxiang Gao on 2017/3/5.
 */

public class RandomGame {
    int height;
    int length;
    SolvableMapGenerator smg;
    public RandomGame(char level)
    {
        switch (level)
        {
            case 'E'://easy level
                do
                {
                    smg = new SolvableMapGenerator(3);
                }while(smg.getLevel() < 35 || !smg.allIsMoved());//keep reproducing games if not matches the criterion
                break;
            case 'M'://medium level
                do
                {
                    smg = new SolvableMapGenerator(4);
                }while(smg.getLevel() < 60 || !smg.allIsMoved());
                break;
            case 'H'://hard level
                do
                {
                    smg = new SolvableMapGenerator(5);
                }while(smg.getLevel() < 75 || !smg.allIsMoved());
                break;
            default:
                System.out.println("wrong parameter");
        }
    }

    public char[][] getMap()
    {
        return smg.getMap();
    }

}
