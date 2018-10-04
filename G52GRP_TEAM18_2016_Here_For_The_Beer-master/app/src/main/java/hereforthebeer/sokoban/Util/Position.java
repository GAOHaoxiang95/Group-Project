package hereforthebeer.sokoban.Util;

/**
 * Created by James on 18/11/2016 <p\>
 * Wrapper class to handle position data more consistently
 */
public class Position {
    public final static Position LEFT = new Position(-1, 0);
    public final static Position RIGHT = new Position(-1, 0);
    public final static Position UP = new Position(0, 1);
    public final static Position DOWN = new Position(0, -1);
    public static Position[] getAdjacent(Position p) {
        return new Position[] { new Position(p, UP), new Position(p, RIGHT), new Position(p, DOWN), new Position(p, LEFT) };
    }

    public int x;
    public int y;

    /** Defaults position to (0, 0) */
    public Position() { this(0, 0); }
    /** Sets position to be the same as p */
    public Position(Position p) { this(p.x, p.y); }
    /** Sets position to be (x, y) */
    public Position(int x, int y) {
        set(x, y);
    }
    /** Sets position to be a translated from an x, y pair */
    public Position(Position p, Position delta) {
        set(p.x + delta.x, p.y + delta.y);
    }
    /** Sets position to be a translated from an x, y pair */
    public Position(Position p, int dx, int dy) {
        set(p.x + dx, p.y + dy);
    }

    /** Sets position to be the same as p */
    public Position set(Position p) {
        return set(p.x, p.y);
    }
    /** Sets position to be (x, y) */
    public Position set(int x, int y) {
        this.x = x;
        this.y = y;
        return this;
    }

    /** Translates this position by another position */
    public Position translate(Position p) {
        return translate(p.x, p.y);
    }
    /** Translates this position by an (x, y) pair */
    public Position translate(int x, int y) {
        this.x += x;
        this.y += y;
        return this;
    }

    /** Returns the inverted vector (-x, -y) */
    public Position inverted() {
        return new Position(-this.x, -this.y);
    }

    /** Gets the difference between this and another position (this - that) **/
    public Position difference(Position p) {
        return new Position(this, -p.x, -p.y);
    }

    /** Calculates the manhattan distance between two positions */
    public static int manhattan(Position p1, Position p2) {
        return Math.abs(p1.x - p2.x) + Math.abs(p1.y - p2.y);
    }

    /** Returns a String representation of this position in the format: {x, y} */
    @Override
    public String toString() {
        return "{" + x + ", " + y + "}";
    }


    /** Checks if one position is equal to another. USE THIS INSTEAD OF '==' PLEASE */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || !(o instanceof Position))
            return false;
        Position p = (Position) o;
        return (p.x == this.x && p.y == this.y);
    }

    /** Quick equals check to see if this position equals a given x, y pair */
    public boolean equals(int x, int y) {
        return this.x == x && this.y == y;
    }

    /** Creates a hash for this Position */
    @Override
    public int hashCode() {
        return x * y + x + y;
    }
}
