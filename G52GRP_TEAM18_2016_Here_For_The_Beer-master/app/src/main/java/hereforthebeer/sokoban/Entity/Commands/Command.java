package hereforthebeer.sokoban.Entity.Commands;

/**
 * Created by James on 18/11/2016 <p\>
 * An interface to allow entity non-specific actions to be executed later and undone at any point
 */
public interface Command {
    /** Call this to execute the contents of the command */
    void execute();

    /** Call this to undo the execution of the command, returns if valid undo was performed */
    boolean undo();

    /** This converts the command to a symbol to write to a file */
    String getFileChar();
}
