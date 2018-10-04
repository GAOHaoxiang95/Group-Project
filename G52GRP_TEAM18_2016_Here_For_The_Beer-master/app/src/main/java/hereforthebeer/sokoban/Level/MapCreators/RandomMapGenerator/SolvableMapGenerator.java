package hereforthebeer.sokoban.Level.MapCreators.RandomMapGenerator;
import java.util.LinkedList;
import java.util.Random;

/**
 * Created by Haoxiang Gao on 2017/2/27.
 */


public class SolvableMapGenerator {
    private int p = 1;
    private int level = 0;
    private char[][] charMap;
    private int[][] intMap;
    private int high;
    private int length;
    private Random r;
    int[] playerPos;
    private LinkedList<int[]> boxes;
    private LinkedList<int[]> originalBoxes;
    private int[] direction;
    private int numberOfGoals;
    private int[] result;
    boolean notEnd = true;
    public SolvableMapGenerator(int numberOfGoal)
    {
        EmptyMapGenerator emg = new EmptyMapGenerator();//empty map
        MapGenerator mg = new MapGenerator(emg, numberOfGoal);//add goals and player
        this.numberOfGoals = numberOfGoal;

        direction = new int[numberOfGoal];
        result = new int[numberOfGoal];
        for(int i = 0; i < numberOfGoal; i++)//initialize
        {
            direction[i] = 0;
            result[i] = 0;
        }
        high = mg.getHigh();
        length = mg.getLength();
        charMap = mg.getMap();
        intMap = new int[high][length];

        boxes = mg.getBoxes();
        originalBoxes = this.getOriginalBox();
        translateCharToInt();

        while(notEnd)//keep push till no boxes can move
        {
            push();
        }
        addGoals();
    }

    /*
     * push a box to move
     */
    private void push()
    {
        cancelMovement();//erase the mark in last turn

        movement(playerPos[0], playerPos[1], intMap);//mark those position can be reached

        int[][] backup = copyMap(intMap);
        int max = 0;
        int backupIndex = -1;
        int backupSolution = -1;
        int backupDirection = -1;
        boolean flag = false;
        for(int i = 0; i < boxes.size(); i++)
        {
            int[] pos = boxes.get(i);
            int L = pushable(pos[0], pos[1], backup, i, 'l');//try to push to the left
            if(L >= max)
            {
                intMap = copyMap(backup);
                intMap[pos[0]][pos[1]-1] = 2;
                intMap[pos[0]][pos[1]] = 0;
                playerPos[0] = pos[0];
                playerPos[1] = pos[1];

                max = L;

                backupSolution = L;
                backupIndex = i;
                backupDirection = 1;
            }
            int R = pushable(pos[0], pos[1], backup, i, 'r');//try to push to the right
            if(R >= max)
            {
                intMap = copyMap(backup);
                intMap[pos[0]][pos[1]+1] = 2;
                intMap[pos[0]][pos[1]] = 0;
                playerPos[0] = pos[0];
                playerPos[1] = pos[1];

                max = R;

                backupSolution = R;
                backupIndex = i;
                backupDirection = 2;
            }
            int U = pushable(pos[0], pos[1], backup, i, 'u');//try to push to the up
            if(U >= max)
            {
                intMap = copyMap(backup);
                intMap[pos[0]-1][pos[1]] = 2;
                intMap[pos[0]][pos[1]] = 0;
                playerPos[0] = pos[0];
                playerPos[1] = pos[1];

                max = U;

                backupSolution = U;
                backupIndex = i;
                backupDirection = 3;
            }
            int D = pushable(pos[0], pos[1], backup, i, 'd');//try to push to the down
            if(D >= max)
            {
                intMap = copyMap(backup);
                intMap[pos[0]+1][pos[1]] = 2;
                intMap[pos[0]][pos[1]] = 0;
                playerPos[0] = pos[0];
                playerPos[1] = pos[1];

                max = D;

                backupSolution = D;
                backupIndex = i;
                backupDirection = 4;
            }
        }
        if(max == 0)
        {
            notEnd = false;
        }
        result[backupIndex] = backupSolution;
        if(direction[backupIndex] != backupDirection)//change the direction
        {
            direction[backupIndex] = backupDirection;
            level += numberOfGoals*2;
        }
        p = p + 1;
        level++;
    }



    /*
     * to check whether a box can be push, if can be pushed, give a cost use evaluate function
     * @param row box is on which row
     * @param col box is on which column
     * @param intMapt tempt int map
     * @param direction which direction to push
     * @return if cannot be pushed, return 0; else return a cost
     */
    private int pushable(int row, int col, int[][] intMapt, int box, char direction)
    {
        switch (direction)
        {
            case 'l':
                if ((intMapt[row][col-1] == 0 || intMapt[row][col-1] == 1 || intMapt[row][col-1] == 4 || intMapt[row][col-1] == 5)
                        && (intMapt[row][col+1] == 4 || intMapt[row][col+1] == 5))
                {
                    return evaluateFunction(row, col - 1, box, 1);
                }

                return 0;

            case 'r':
                if ((intMapt[row][col+1] == 0 || intMapt[row][col+1] == 1 || intMapt[row][col+1] == 4 || intMapt[row][col+1] == 5)
                        && (intMapt[row][col-1] == 4 || intMapt[row][col-1] == 5))
                {
                    return evaluateFunction(row, col + 1, box, 2);
                }
                return 0;

            case 'u':
                if ((intMapt[row-1][col] == 0 || intMapt[row-1][col] == 1 || intMapt[row-1][col] == 4 || intMapt[row-1][col] == 5)
                        && (intMapt[row+1][col] == 4 || intMapt[row+1][col] == 5))
                {
                    return evaluateFunction(row - 1, col, box, 3);
                }
                return 0;
            case 'd':
                if ((intMapt[row+1][col] == 0 || intMapt[row+1][col] == 1 || intMapt[row+1][col] == 4 || intMapt[row+1][col] == 5)
                        && (intMapt[row-1][col] == 4 || intMapt[row-1][col] == 5))
                {
                    return evaluateFunction(row+1, col, box, 4);
                }
                return 0;
            default:
                return 0;
        }
    }

    //manhattanDistance could be used in evaluation function in future
    public int manhattanDistance()
    {
        int d = 0;
        int distance = 0;
        for(int j = 0; j < originalBoxes.size(); j++) {
            int row = (originalBoxes.get(j))[0];
            int col = (originalBoxes.get(j))[1];
            int min = Math.abs(row - boxes.get(0)[0]) + Math.abs(col - boxes.get(0)[1]);
            for (int i = 0; i < boxes.size(); i++) {
                int[] pos = boxes.get(i);

                distance = Math.abs(row - pos[0]) + Math.abs(col - pos[1]);
                if(distance < min)
                {
                    min = distance;
                }
            }
            d = d + min;
        }
        return d;
    }
    
    private int evaluateFunction(int row, int col, int j, int d)
    {
        int h = 0;
        r = new Random();
        int a = r.nextInt(1000);//use random value now, could change in future
        h = a;
        return h;
    }


    /*
     * translate char Map to Int Map
     */
    private void translateCharToInt()
    {
        for(int i = 0; i < high; i++)
        {
            for(int j = 0; j < length; j++)
            {
                //translate the original map
                switch(charMap[i][j])
                {
                    case '#':
                        intMap[i][j] = 8;
                        break;
                    case ' ':
                        intMap[i][j] = 0;
                        break;
                    case '$':
                        intMap[i][j] = 2;
                        break;
                    case '@':
                        intMap[i][j] = 4;
                        playerPos = new int[2];
                        playerPos[0] = i;
                        playerPos[1] = j;
                        break;
                }
            }
        }
    }

    /*
     * recursive DFS, fill in the position that player can move
     * @param row Player is on which row
     * @param col Player is on which column
     * @param temp The map need to be filled
     */
    public void movement(int row, int col, int[][] temp)
    {
        int flag = 0;

        if(temp[row+1][col] == 0 || temp[row+1][col] == 1)
        {
            temp[row+1][col] = temp[row+1][col] + 4;
            flag = 1;
            movement(row+1, col, temp);
        }
        if(temp[row-1][col] == 0 || temp[row-1][col] == 1)
        {
            temp[row-1][col] = temp[row-1][col] + 4;
            flag = 1;
            movement(row-1, col, temp);
        }
        if(temp[row][col+1] == 0 || temp[row][col+1] == 1)
        {
            temp[row][col+1] = temp[row][col + 1] + 4;
            flag = 1;
            movement(row, col+1, temp);
        }
        if(temp[row][col - 1] == 0 || temp[row][col - 1] == 1)
        {
            temp[row][col - 1] = temp[row][col - 1] + 4;
            flag = 1;
            movement(row, col-1, temp);
        }

        if(flag == 0)
        {
            return;
        }
    }

    /*
     * clear the position that player can reach
     */
    public void cancelMovement()
    {
        LinkedList<int[]> currentBoxes = new LinkedList<int[]>();
        for(int i = 0; i < high; i++)
        {
            for(int j = 0; j < length; j++)
            {
                if(intMap[i][j] == 4 || intMap[i][j] == 5)
                {
                    intMap[i][j] = intMap[i][j] - 4;
                }
                if(intMap[i][j] == 2)
                {
                    int[] pos = new int[2];
                    pos[0] = i;
                    pos[1] = j;

                    currentBoxes.offer(pos);
                }
            }
        }
        boxes = currentBoxes;
    }


    /*
     * clone a list of original position
     */
    public LinkedList<int[]> getOriginalBox()
    {
        LinkedList<int[]> box = new LinkedList<int[]>();
        for(int i = 0; i < high; i++)
        {
            for(int j = 0; j < length; j++)
            {
                if(charMap[i][j] == '$')
                {
                    int[] pos = new int[2];
                    pos[0] = i;
                    pos[1] = j;
                    box.offer(pos);
                }
            }
        }
        return box;
    }

    /*
     * add goals on the char Map
     */
    private void addGoals()
    {
        for(int i = 0; i < boxes.size(); i++)
        {
            int[] pos = boxes.get(i);
            if(charMap[pos[0]][pos[1]] == ' ')
            {
                charMap[pos[0]][pos[1]] = '.';
            }
            else if(charMap[pos[0]][pos[1]] == '$')
            {
                charMap[pos[0]][pos[1]] = 'X';
            }
            else
            {
                charMap[pos[0]][pos[1]] = 'o';
            }
        }
    }

    /*
     * clone a int map
     */
    private int[][] copyMap(int[][] map)
    {
        int[][] backup = new int[high][length];
        for(int i = 0; i < high;i++)
        {
            for(int j = 0;j < length;j++)
            {
                backup[i][j] = map[i][j];
            }
        }
        return backup;
    }

    public char[][] getMap()
    {
        return this.charMap;
    }

    /*
     * make sure all boxes moved and make sure the game is not so easy
     */
    public Boolean allIsMoved()
    {
        for(int i = 0; i < boxes.size(); i++)
        {
            for(int j = 0; j < boxes.size(); j++) {
                int[] pos = boxes.get(i);
                int[] originalPos = originalBoxes.get(j);
                if (isEasy(pos, originalPos)) {
                    return false;
                }
            }
        }
        return true;

    }

    public Boolean isEasy(int[] a, int[] b) {
        if ((a[0] == b[0] && a[1] == b[1])
                || (a[0] + 1 == b[0] && a[1] == b[1] && (charMap[b[0]+1][a[1]] == ' ' || charMap[b[0]+1][a[1]] == '@'))
                || (a[0] - 1 == b[0] && a[1] == b[1] && (charMap[b[0]-1][a[1]] == ' ' || charMap[b[0]-1][a[1]] == '@' ) )
                || (a[0] == b[0] && a[1] + 1 == b[1] && (charMap[b[0]][b[1]+1] == ' ' || charMap[b[0]][b[1]+1] == '@'))
                || (a[0] == b[0] && a[1] - 1 == b[1] && (charMap[b[0]][b[1]-1] == ' ' || charMap[b[0]][b[1]-1] == '@')))
        {
            return true;
        }
        return false;
    }

    public int getLevel()
    {
        return level;
    }

//    public void test()
//    {
//        for(int a = 0 ;a < 10;a++)
//        {
//            for(int b = 0 ;b < 10;b++){
//                System.out.print(charMap[a][b]);
//            }
//            System.out.println();
//        }
//    }
}
