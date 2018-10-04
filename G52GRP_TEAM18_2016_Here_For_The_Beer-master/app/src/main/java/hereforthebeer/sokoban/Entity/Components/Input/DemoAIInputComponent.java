package hereforthebeer.sokoban.Entity.Components.Input;

import java.util.ArrayList;

import hereforthebeer.sokoban.Entity.Commands.CommandStream;
import hereforthebeer.sokoban.Entity.Commands.TranslateCommand;
import hereforthebeer.sokoban.Entity.Entity;
import hereforthebeer.sokoban.Level.Level;
import hereforthebeer.sokoban.Util.Position;

/**
 * Created by James on 21/11/2016 <p\>
 * Provides an example of how to implement a simple AI to move
 * the entity around. This implementation moves the player up
 * indefinitely
 */
public class DemoAIInputComponent implements InputComponent {
    //Docs provided in interface
    public void update(Entity e, int turn, Level level, ArrayList<Entity> boxes) {
        //Grab the command stream from the entity
        CommandStream cs = e.getCommandStream();

        //Add a move command if the player can move there
        Position newP = new Position(e.getPosition().x + 1, e.getPosition().y);
        if (!level.getTile(newP).isCollidable())
            cs.addCommand(turn, new TranslateCommand(e, TranslateCommand.Right, level, boxes));
    }

    //Docs provided in interface
    public void reset(Entity e) {
        //No reset needed
    }
}
