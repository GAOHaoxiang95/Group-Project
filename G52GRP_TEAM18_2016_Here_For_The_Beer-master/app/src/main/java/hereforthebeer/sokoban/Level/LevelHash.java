package hereforthebeer.sokoban.Level;

import java.util.ArrayList;

import hereforthebeer.sokoban.Entity.Entity;
import hereforthebeer.sokoban.Util.Position;

/**
 * Created by James on 13/02/2017 <p\>
 * Wrapper class to handle position data more consistently
 */
public class LevelHash {
    long hash;
    Level level;

    /** Default constructor for object pool use */
    public LevelHash() {
        hash = 0;
        level = null;
    }

    /** Constructor for initializing with a hash */
    public LevelHash(Level l, ArrayList<Entity> boxes) {
        init(l, boxes);
    }

    /** Initializes level state for use with an object pool */
    public void init(Level l, ArrayList<Entity> boxes) {
        level = l;
        hash = 0;
        for (Entity b : boxes) {
            Position p = b.getPosition();
            hash |= 1 << (p.x - 1) + (p.y - 1) * l.getWidth();
        }
    }

    /** Returns an array of successor box states */
    public long[] getSuccessors() {
        long[] successors = new long[4];
        successors[0] = 0;
        successors[1] = 0;
        successors[2] = 0;
        successors[3] = 0;
        return successors;
    }

    /** Checks if one level state is equal to another. USE THIS INSTEAD OF '==' PLEASE */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || !(o instanceof LevelHash))
            return false;
        LevelHash p = (LevelHash) o;
        return (p.hash == this.hash);
    }

    /** Creates a hash for this Level state */
    @Override
    public int hashCode() {
        return (int) ((hash >> 32) ^ hash);
    }

    /** Prints the hash to console in binary */
    @Override
    public String toString() {
        return "{" + Long.toBinaryString(hash) + "}";
    }
}