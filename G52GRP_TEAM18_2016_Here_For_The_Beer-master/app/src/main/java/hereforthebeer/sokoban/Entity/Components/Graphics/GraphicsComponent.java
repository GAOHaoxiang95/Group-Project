package hereforthebeer.sokoban.Entity.Components.Graphics;

/**
 *
        */

import android.graphics.Canvas;
import android.view.View;

import hereforthebeer.sokoban.Entity.Entity;

/** Created by James on 18/11/2016 <p\> Interface to allow rendering of Entities across multiple devices */
public interface GraphicsComponent {
    /**
     * Updates the graphics of the Entity
     * @param e The Entity you want to draw
     * @param c The Canvas reference to draw to
     */
    void update(Entity e, Canvas c, int offsetx, int offsety);

    /** Resets the graphics for the given Entity */
    void reset(Entity e);
}
