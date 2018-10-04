package hereforthebeer.sokoban.Util.Log;

/**
 * Created by James on 30/11/2016. <p\>
 * Logging interface to allow multiple implementations of logging
 */
public interface Log {
    /** Prints a string to the log */
    void print(String s);

    /** Prints a string to the log with a line break */
    void println(String s);
}
