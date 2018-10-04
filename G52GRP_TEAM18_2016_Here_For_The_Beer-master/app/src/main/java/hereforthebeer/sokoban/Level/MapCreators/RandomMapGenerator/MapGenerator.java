package hereforthebeer.sokoban.Level.MapCreators.RandomMapGenerator;
import java.util.LinkedList;
import java.util.Random;

import hereforthebeer.sokoban.Level.MapCreators.RandomMapGenerator.EmptyMapGenerator;

/**
 * Created by Haoxiang Gao on 2017/2/27.
 */


public class MapGenerator {
    private int length = 0;
    private int high = 0;
    private char map[][];
    int[] playerPos;
    private LinkedList<int[]> floor;
    private LinkedList<int[]> boxes;
    Random random;

    public MapGenerator(EmptyMapGenerator emg, int numberOfGoals)
    {
        random = new Random();
        this.length = emg.getLength();
        this.high = emg.getHigh();
        map = emg.getMap();
        floor = new LinkedList<int[]>();
        boxes = new LinkedList<int[]>();
        getFloor();
        randomAddGoal(numberOfGoals);
        randomAddPlayer();
    }

    /*
     * Add empty floor to a linked list
     */
    private void getFloor()
    {
        int[] position;
        for(int i = 0; i < high; i++)
        {
            for(int j = 0; j < length; j++)
            {
                if(map[i][j] == ' ' && !isDeadAtCorner(i,j))
                {
                    position = new int[2];
                    position[0] = i;
                    position[1] = j;
                    floor.offer(position);
                }
            }
        }
    }

    /*
     * @param numberOfGoals The number of goals would be placed in the map.
     */
    private void randomAddGoal(int numberOfGoals)
    {
        int numberOfFloor = floor.size();
        int goal = 0;
        int[] position;
        int row;
        int col;
        int i = 0;
        while(i < numberOfGoals)
        {
            goal = random.nextInt(numberOfFloor);
            position = floor.get(goal);
            row = position[0];
            col = position[1];
            if(numberOfFloor <= 0)
            {
                System.out.println("No more empty floor");
                System.exit(0);
            }

            numberOfFloor--;

            if(isDeadAtCorner(row, col))
            {
                floor.remove(goal);
                continue;
            }
            boxes.offer(position);//add the chosen goal into the list

            map[row][col] = '$';
            floor.remove(goal);
            i++;
        }
    }
    /*
    * add a player in the game
    */
    private void randomAddPlayer()
    {
        int row;
        int col;

        int numberOfFloor = floor.size();
        if(numberOfFloor <= 0)
        {
            System.out.println("No more empty floor");
            System.exit(0);
        }
        int player = random.nextInt(numberOfFloor);
        playerPos = floor.get(player);
        row = playerPos[0];
        col = playerPos[1];
        map[row][col] = '@';
        floor.remove(player);
    }

    /*
    * @param row the position wanted to be tested
    * @param col the position wanted to be tested
    * @return whether this position is dead at corner
    */
    private Boolean isDeadAtCorner(int row, int col)
    {
        if((map[row-1][col] == '#' || map[row-1][col] == '$')
                && (map[row][col-1] == '#' || map[row][col-1] == '$'))
        {
            return true;
        }

        if((map[row-1][col] == '#' || map[row-1][col] == '$')
                && (map[row][col+1] == '#' || map[row][col+1] == '$'))
        {
            return true;
        }

        if((map[row+1][col] == '#' || map[row+1][col] == '$')
                && (map[row][col+1] == '#' || map[row][col+1] == '$'))
        {
            return true;
        }

        if((map[row+1][col] == '#' || map[row+1][col] == '$')
                && (map[row][col-1] == '#' || map[row][col-1] == '$'))
        {
            return true;
        }
        return false;
    }


    public int getLength() {
        return length;
    }

    public int getHigh() {
        return high;
    }

    public char[][] getMap() {
        return map;
    }

    public LinkedList<int[]> getBoxes() {
        return boxes;
    }
//    public void test()
//    {
//        for(int a = 0 ;a < 10;a++)
//        {
//            for(int b = 0 ;b < 10;b++){
//                System.out.print(map[a][b]);
//            }
//            System.out.println();
//        }
//    }
}
