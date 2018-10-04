package hereforthebeer.sokoban.Entity.Components.Input;

import java.util.ArrayList;

import hereforthebeer.sokoban.Entity.Entity;
import hereforthebeer.sokoban.Level.Level;

/** Created by James on 18/11/2016 <p\> Interface to allow input across many different medium (Android, Scripts, ect) */
public interface InputComponent {
    /**
     * Updates the physics of the Entity
     * @param e The Entity you want to update
     * @param turn The turn number that this update is being called at
     * @param level The Level that you are moving the entity around
     * @param boxes A list of the boxes you want the entity to interact with
     */
    void update(Entity e, int turn, Level level, ArrayList<Entity> boxes);

    /** Resets the input for the given Entity */
    void reset(Entity e);
}
