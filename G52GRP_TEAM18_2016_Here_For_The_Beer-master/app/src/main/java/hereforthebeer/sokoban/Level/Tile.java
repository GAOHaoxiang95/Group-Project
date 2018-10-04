package hereforthebeer.sokoban.Level;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

/**
 * Created by James on 18/11/2016 <p\>
 * Contains data about a specific square type found on the map
 */
public class Tile {
    //Member variables
    private boolean collidable;
    private boolean isGoal;
    private Drawable sprite;

    /**
     * Standard constructor for creating a new Tile type
     * @param collideable Does the Tile collide with Entities
     * @param isGoal Is this Tile a goal for Box entities
     *               (Tile cannot be both collidable and a goal)
     */
    public Tile(boolean collideable, boolean isGoal, Drawable sprite) {
        this.collidable = collideable;
        this.isGoal = (!collideable && isGoal);
        this.sprite = sprite;
    }

    public void render(Canvas c, int x, int y, int cellSize, int offsetx, int offsety) {
        x *= cellSize;
        y *= cellSize;
        sprite.setBounds(x + offsetx, y + offsety, x + cellSize + offsetx, y + cellSize + offsety);
        sprite.draw(c);
    }

    /** Boolean check for if this Tile type collides with Entities */
    public boolean isCollidable() {
        return this.collidable;
    }

    /** Boolean check for if this Tile type is a goal or not */
    public boolean isGoal() { return this.isGoal; }

    /**
     * Allows easy way to print out Tiles in text only interfaces
     * @return String containing a single char representation for this Tile type
     */
    public String toString() {
        return (this.collidable ? "#" : this.isGoal ? "_" : ".");
    }
}
