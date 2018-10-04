package hereforthebeer.sokoban.Entity.Components.Input;

import java.util.ArrayList;

import hereforthebeer.sokoban.Entity.Commands.CommandStream;
import hereforthebeer.sokoban.Entity.Commands.TranslateCommand;
import hereforthebeer.sokoban.Entity.Entity;
import hereforthebeer.sokoban.Level.Level;
import hereforthebeer.sokoban.Util.Position;

/**
 * Created by James on 23/11/2016.
 * Provides an example of how to implement a simple AI to move
 * the entity around. This implementation moves the player up
 * indefinitely
 */
public class DemoBatchInputComponent implements InputComponent {
    //Remember what turn we are on
    private int turnCounter = 0;
    private Position pos;

    //Docs provided in interface
    public void update(Entity e, int turn, Level level, ArrayList<Entity> boxes) {
        //Check if first turn
        if (turn != 0)
            return;

        //Get the command stream for the entity
        CommandStream cs = e.getCommandStream();


        //Setup a bunch of preset movement commands for completing the test map
        cs.addCommand(turnCounter++, new TranslateCommand(e, TranslateCommand.Down, level, boxes));
        cs.addCommand(turnCounter++, new TranslateCommand(e, TranslateCommand.Left, level, boxes));
        cs.addCommand(turnCounter++, new TranslateCommand(e, TranslateCommand.Up, level, boxes));
        cs.addCommand(turnCounter++, new TranslateCommand(e, TranslateCommand.Left, level, boxes));
        cs.addCommand(turnCounter++, new TranslateCommand(e, TranslateCommand.Up, level, boxes));
        cs.addCommand(turnCounter++, new TranslateCommand(e, TranslateCommand.Right, level, boxes));
    }

    //Docs provided in interface
    public void reset(Entity e) {
        //Reset turn counter
        turnCounter = 0;
    }
}