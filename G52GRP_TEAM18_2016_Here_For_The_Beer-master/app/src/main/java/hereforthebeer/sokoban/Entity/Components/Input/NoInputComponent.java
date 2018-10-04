package hereforthebeer.sokoban.Entity.Components.Input;

import java.util.ArrayList;

import hereforthebeer.sokoban.Entity.Entity;
import hereforthebeer.sokoban.Level.Level;

/**
 * Created by James on 23/11/2016.
 * This InputComponent intentionally does nothing
 */
public class NoInputComponent implements InputComponent {
    //Docs defined in interface
    public void update(Entity e, int turn, Level level, ArrayList<Entity> boxes) {
        //Intentionally Empty
    }

    //Docs defined in interface
    public void reset(Entity e) {
        //Intentionally Empty
    }
}
