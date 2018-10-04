package hereforthebeer.sokoban.Entity.Components.Graphics;

import android.graphics.Canvas;
import android.view.View;

import hereforthebeer.sokoban.Entity.Entity;

/**
 * Created by James on 08/12/2016. <p\>
 * Allows no graphics to be used.
 */
public class NullGraphicsComponent implements GraphicsComponent {
    //Docs defined in interface
    public void update(Entity e, Canvas c, int offsetx, int offsety) {
        //Intentionally empty
    }

    //Docs defined in interface
    public void reset(Entity e) {
        //Intentionally empty
    }
}
