package hereforthebeer.sokoban.Level.MapCreators.RandomMapGenerator;

/**
 * Created by Haoxiang Gao on 2017/2/22.
 */

public class Templates {
    private char[][] template;
    private int length = 0;
    private int high = 0;

    public Templates(int num)
    {
        switch (num)
        {
            case 0:
                template1();
                break;
            case 1:
                template2();
                break;
            case 2:
                template3();
                break;
        }
    }


    /*
     * template style 1
     *
     */
    private void template1()
    {
        high = 3;
        length = 2;
        template = new char[high][length];
        for(int i = 0;i< high;i++)
        {
            for(int j = 0;j< length;j++)
            {
                template[i][j] = ' ';
            }
        }
    }

    /*
     * template style 2
     *
     */
    private void template2()
    {
        high = 3;
        length = 3;
        template = new char[high][length];
        for(int i = 0;i< high;i++)
        {
            for(int j = 0;j< length;j++)
            {
                template[i][j] = ' ';
            }
            template[1][1] = '#';
        }
    }


    /*
     * template style 3
     *
     */
    public void template3()
    {
        high = 2;
        length = 2;
        template = new char[high][length];
        for(int i = 0;i < high;i++)
        {
            for (int j = 0; j < length; j++)
            {
                template[i][j] = ' ';
            }
        }
    }


    public char[][] getTemplate() {
        return template;
    }


    public int getLength() {
        return length;
    }


    public int getHigh() {
        return high;
    }


}