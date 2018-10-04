package hereforthebeer.sokoban.Entity.Commands;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import android.os.Environment;

import hereforthebeer.sokoban.Util.Pair;

/**
 * Created by James on 18/11/2016 <p\>
 * Stores a stream of commands for later playback / saving
 */
public class CommandStream {
    //Store commands inside array list where the front of the pair is the turn and the back is the
    //command to be executed
    private ArrayList<Pair<Integer, Command>> commandList = new ArrayList<>();
    private boolean isSorted = true;

    //Store the oldest frame currently stored (used to resort if a new frame is added out of order)
    private int oldestFrame = -1;

    //Store performance stats
    public int movesMade = 0;
    public int boxesMoved = 0;

    /** Default constructor for real-time command adding */
    public CommandStream() {}
    /** Constructor for loading straight from a file */
    public CommandStream(InputStream file) {
        loadFromFile(file);
    }



    /**
     * Adds a Command to the stream at a particular turn number
     * @param turn The turn at which this command will be executed
     * @param c The Command to execute
     */
    public void addCommand(int turn, Command c) {
        //Try to get a command if it already exists
        Command oldCommand = getCommand(turn);

        //Check if an entry exists for the given turn
        if (oldCommand == null) {
            //If not, add a new pair element with the turn and the command to the list
            commandList.add(new Pair<>(new Integer(turn), c));

            //Check if the turn is out of order
            if (turn < oldestFrame) {
                //Re-sort the array if it would disrupt the order
                isSorted = false;
            } else {
                //Otherwise update what the latest frame is
                oldestFrame = turn;
            }
        } else {
            //If the command exists already, overwrite it in the stream
            oldCommand = c;
        }

    }

    /** Returns the command for a particular turn */
    public Command getCommand(int turn) {
        //Make sure the stream is sorted
        sortStream();

        //Initialise a pointer to the current element
        Pair<Integer, Command> current;

        //Binary search for commands from turn, return the command if found
        int average, min = 0, max = commandList.size() - 1;
        while (min <= max) {
            average = (min + max) / 2;
            current = commandList.get(average);
            if (current.first() == turn)
                return current.second();
            else if (current.first() > turn)
                max = average - 1;
            else
                min = average + 1;
        }

        //If no command found for this turn return null
        return null;
    }

    /** Undo's the last command done by this steam */
    public void undoLastCommand() {
        sortStream();

        int size;
        while ((size = commandList.size()) > 0) {
            Command c = commandList.remove(size - 1).second();
            if (c.undo()) {
                return;
            }
        }
    }

    /**
     * Takes a filepath and loads the from it CommandStream
     * @param file The File object for the file you wish to load
     * @return Returns if successful
     */
    public boolean loadFromFile(InputStream file) {
        //To be implemented
        return false;
    }

    /**
     * Takes a filepath and saves the CommandStream to it
     * @param file The File object for the file you wish to load
     * @return Returns 0 if successfull and 1 for any other error
     */
    public int saveToFile(File file) {
        //Return early if not able to write
        if (!isExternalStorageWritable()) {
            return 1;
        }

        //Define file objects
        FileWriter fw = null;
        BufferedWriter bw = null;

        //Try to write to the file
        try {
            //Create the file from new
            if (!file.exists()) {
                file.createNewFile();
            }

            //Assign file objects
            fw = new FileWriter(file.getAbsoluteFile(), true);
            bw = new BufferedWriter(fw);

            //Write whole command stream to the file
            String contents = "";
            for (Pair<Integer, Command> action : commandList) {
                String turn = Integer.toString(action.first()) + "\r\n";
                String command = action.second().getFileChar() + "\r\n";
                bw.write(turn);
                bw.write(command);
                contents += turn;
                contents += command;
            }
            //System.out.println("FILE CONTENTS:\n" + contents);
        } catch (IOException e) {
            e.printStackTrace();
            return 1;
        } finally {
            //Close file objects
            try {
                //Close file objects if they are not null
                if (bw != null) {
                    bw.close();
                }

                if (fw != null) {
                    fw.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
                return 1;
            }
        }
        return 0;
    }

    /** Sorts the internal stream in order of what turn commands are executed on */
    private void sortStream() {
        //Return if the list is already sorted
        if (isSorted)
            return;

        //Sort based on what turn the commands are
        Collections.sort(commandList,new Comparator<Pair<Integer, Command>>() {
            @Override
            public int compare(Pair<Integer, Command> l1, Pair<Integer, Command> l2) {
                return l1.first() - l2.first();
            }
        });

        //Set that the list is now sorted
        isSorted = true;
    }

    /** Checks if the external storage is writable. <p\>
     * ADAPTED FROM: https://developer.android.com/training/basics/data-storage/files.html */
    private boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }
}
