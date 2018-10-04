package hereforthebeer.sokoban.Entity.Components.Physics;

import java.util.ArrayList;

import hereforthebeer.sokoban.Entity.Commands.Command;
import hereforthebeer.sokoban.Entity.Entity;
import hereforthebeer.sokoban.Level.Level;

/**
 * Created by James on 18/11/2016 <p\>
 * This PhysicsComponent just runs Command objects for the given entity for the current turn
 */
public class InteractionPhysicsComponent implements PhysicsComponent {
    //Docs defined in interface
    public void update(Entity e, int turn) {
        //Get the command that will be executed
        Command c = e.getCommandStream().getCommand(turn);

        //Execute the command
        if (c != null) {
            c.execute();
        }
    }

    //Docs defined in interface
    public void reset(Entity e) {
        //Nothing to reset
    }
}
