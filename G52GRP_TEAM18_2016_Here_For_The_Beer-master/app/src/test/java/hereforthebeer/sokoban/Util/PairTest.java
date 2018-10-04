package hereforthebeer.sokoban.Util;

import org.junit.Test;

import static org.junit.Assert.*;

public class PairTest {
    private Pair<String, Integer> p = new Pair<>("Grey", 10);

    @Test
    public void setFirst() throws Exception {
        p.setFirst("Blue");
        assertEquals("Blue", p.first());
    }

    @Test
    public void setSecond() throws Exception {
        p.setSecond(3);
        assertEquals((int) 3, (int) p.second());
    }

    @Test
    public void first() throws Exception {
        assertEquals("Grey", p.first());
    }

    @Test
    public void second() throws Exception {
        assertEquals((int) 10, (int) p.second());
    }

}