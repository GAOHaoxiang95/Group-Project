package hereforthebeer.sokoban.Entity.Components.Physics;

import hereforthebeer.sokoban.Entity.Entity;
import hereforthebeer.sokoban.Level.Level;

/** Created by James on 18/11/2016 <p\> Interface to allow multiple types of physics to effect Entities */
public interface PhysicsComponent {
    /**
     * Updates the physics of the Entity
     * @param e The Entity you want to update
     * @param turn The turn number that this update is being called at
     */
    void update(Entity e, int turn);

    /** Resets the physics for the given Entity */
    void reset(Entity e);
}