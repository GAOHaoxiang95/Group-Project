package hereforthebeer.sokoban.Level.MapCreators.RandomMapGenerator;

/**
 * Created by Haoxiang Gao on 2017/2/22.
 */

public class Prototypes {
    private char[][] prototype;
    private int length = 0;
    private int high = 0;

    public Prototypes(int num)
    {
        switch (num)
        {
            case 0:
                prototype1();
                break;
            case 1:
                prototype2();
                break;
        }
    }

    /*
     * prototype style 1
     */
    public void prototype1()
    {
        length = 10;
        high = 10;
        prototype = new char[high][length];
        for(int i = 0; i < high; i++)
        {
            for(int j = 0; j < length; j++)
            {
                if(i >= 3 && i <= 6 && j >= 2 && j <= 7)
                {
                    prototype[i][j] = ' ';
                }
                else
                {
                    prototype[i][j] = '#';
                }
            }
        }
        prototype[4][4] = '#';
        prototype[5][5] = '#';
    }

    /*
     * prototype style 2
     */
    public void prototype2()
    {
        length = 10;
        high = 10;
        prototype = new char[high][length];
        for(int i = 0; i < high; i++)
        {
            for(int j = 0; j < length; j++)
            {
                if(i >= 3 && i <= 6 && j >= 2 && j <= 7)
                {
                    prototype[i][j] = ' ';
                }
                else
                {
                    prototype[i][j] = '#';
                }
            }
        }
        prototype[4][3] = '#';
        prototype[5][6] = '#';
    }

    public char[][] getPrototype() {
        return prototype;
    }

    public int getLength() {
        return length;
    }

    public int getHigh() {
        return high;
    }

}

