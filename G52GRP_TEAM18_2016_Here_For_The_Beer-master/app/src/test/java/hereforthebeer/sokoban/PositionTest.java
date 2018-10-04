package hereforthebeer.sokoban;

import org.junit.Test;

import hereforthebeer.sokoban.Util.Position;

import static org.junit.Assert.*;

public class PositionTest {
    @Test
    public void translate() throws Exception {
        Position p = new Position(1, 5);
        p.translate(3, 1);
        assertEquals(new Position(4, 6), p);
    }

    @Test
    public void translate1() throws Exception {
        Position p = new Position(4, 2);
        p.translate(new Position(8, 3));
        assertEquals(new Position(12, 5), p);
    }

    @Test
    public void constructor1() {
        Position p = new Position();
        assertEquals(0, p.x);
        assertEquals(0, p.y);
    }

    @Test
    public void constructor2() {
        Position p = new Position(2, 1);
        assertEquals(2, p.x);
        assertEquals(1, p.y);
    }

    @Test
    public void constructor3() {
        Position p1 = new Position(1, 6);
        Position p2 = new Position(p1);
        assertEquals(1, p2.x);
        assertEquals(6, p2.y);
    }

    @Test
    public void set1() {
        Position p = new Position();
        p.set(new Position(2, 3));
        assertEquals(2, p.x);
        assertEquals(3, p.y);
    }

    @Test
    public void set2() {
        Position p = new Position();
        p.set(2, 3);
        assertEquals(2, p.x);
        assertEquals(3, p.y);
    }

    @Test
    public void isEquals() {
        Position p1 = new Position(3, 4);
        Position p2 = new Position(3, 4);
        assertTrue(p1.equals(p2));
        assertTrue(p2.equals(p1));
    }

    @Test
    public void hashCodeConsistent() {
        Position p1 = new Position(3, 4);
        Position p2 = new Position(3, 4);
        assertEquals(p2.hashCode(), p1.hashCode());

        p1.set(8, 0);
        p2.set(8, 0);
        assertEquals(p2.hashCode(), p1.hashCode());
    }
}