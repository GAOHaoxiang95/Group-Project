package hereforthebeer.sokoban.Util;

/**
 * Created by James on 30/11/2016. <p\>
 * Generic Pair class implementation for ease of use
 */
public class Pair<O1, O2> {
    //Store the objects
    private O1 o1;
    private O2 o2;

    /** Standard constructor to set onjects */
    public Pair(O1 o1, O2 o2) {
        this.o1 = o1;
        this.o2 = o2;
    }

    /** Set the first object */
    public void setFirst(O1 o) {
        o1 = o;
    }

    /** Set the second object */
    public void setSecond(O2 o) {
        o2 = o;
    }

    /** Get the first object stored */
    public O1 first() {
        return o1;
    }

    /** Get the second object stored */
    public O2 second() {
        return o2;
    }
}
