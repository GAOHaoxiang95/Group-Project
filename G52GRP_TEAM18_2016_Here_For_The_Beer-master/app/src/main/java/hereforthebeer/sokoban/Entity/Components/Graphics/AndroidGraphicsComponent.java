package hereforthebeer.sokoban.Entity.Components.Graphics;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import hereforthebeer.sokoban.Entity.Entity;

/**
 *
 */

/** Created by James on 18/11/2016 <p\> A type of GraphicsComponent that allows rendering of Entities on android devices */
public class AndroidGraphicsComponent implements GraphicsComponent {
    private Drawable sprite;
    private int cellSize;

    public AndroidGraphicsComponent(int cellSize, Drawable sprite) {
        this.cellSize = cellSize;
        this.sprite = sprite;
    }

    //Docs defined in interface
    public void update(Entity e, Canvas c, int offsetx, int offsety) {
        int x = e.getPosition().x * cellSize + offsetx;
        int y = e.getPosition().y * cellSize + offsety;
        sprite.setBounds(x, y, x + cellSize, y + cellSize);
        sprite.draw(c);
    }

    //Docs defined in interface
    public void reset(Entity e) {
        //To implement
    }
}
