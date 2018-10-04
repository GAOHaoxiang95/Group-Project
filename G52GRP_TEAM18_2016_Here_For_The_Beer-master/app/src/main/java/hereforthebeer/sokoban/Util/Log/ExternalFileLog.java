package hereforthebeer.sokoban.Util.Log;

import android.os.Environment;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by James on 08/12/2016. <p\>
 * Allows logging to be done to an external file.
 */
public class ExternalFileLog implements Log {
    //Hold a reference to the file
    private File file = null;

    //Check to stop calls to write if storage is not available or no perms given
    private boolean fileCheck = false;

    /**
     * Default constructor for writing logs to file
     * @param file The File you wish to log to
     */
    public ExternalFileLog(File file) {
        this.file = file;

        //Check if the external storage is writable
        fileCheck = isExternalStorageWritable();
        if (!fileCheck) {
            System.out.println("EXTERNAL STORAGE NOT WRITABLE!");
        }
    }

    //Docs defined in interface
    public void print(String s) {
        writeToFile(s);
    }

    //Docs defined in interface
    public void println(String s) {
        writeToFile(s + '\n');
    }

    /**
     * Utility function for writing a String to a file
     * @param s String you wish to write
     * @return Returns true if successful otherwise false
     */
    public boolean writeToFile(String s) {
        //Return false if unable to write
        if (!fileCheck) {
            return false;
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

            //Write string
            bw.write(s);

            //Return that the write was successful
            return true;
        } catch (IOException e) {
            //If error somewhere on file write
            e.printStackTrace();

            //Disable file logging
            fileCheck = false;

            //Return failure
            return false;
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
            }
        }
    }

    /** Checks if the external storage is writable. <p\>
     * ADAPTED FROM: https://developer.android.com/training/basics/data-storage/files.html */
    private boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }
}
