package hereforthebeer.sokoban.Entity;

import org.junit.Test;

import hereforthebeer.sokoban.Entity.Components.Graphics.AndroidGraphicsComponent;
import hereforthebeer.sokoban.Entity.Components.Graphics.GraphicsComponent;
import hereforthebeer.sokoban.Entity.Components.Input.DemoAIInputComponent;
import hereforthebeer.sokoban.Entity.Components.Input.InputComponent;
import hereforthebeer.sokoban.Entity.Components.Physics.InteractionPhysicsComponent;
import hereforthebeer.sokoban.Entity.Components.Physics.PhysicsComponent;
import hereforthebeer.sokoban.Level.MapCreators.BlankMapCreator;
import hereforthebeer.sokoban.Level.Level;
import hereforthebeer.sokoban.Level.MapCreators.MapCreationInterface;
import hereforthebeer.sokoban.Util.Position;

import static org.junit.Assert.*;

public class EntityTest {/*
    private MapCreationInterface mapGenerator = new BlankMapCreator();
    private Level level = new Level(15, 15, mapGenerator, 0);

    private InputComponent input = new DemoAIInputComponent();
    private PhysicsComponent physics = new InteractionPhysicsComponent();
    private GraphicsComponent graphics = new AndroidGraphicsComponent();
    private Entity player = new Entity(input, physics, graphics, level.getPlayerStart());

    @Test
    public void reset() throws Exception {
        player.setStartPosition(new Position(0, 0));
        player.setPosition(new Position(6, 6));
        player.reset();
        assertEquals(0, player.getPosition().x);
        assertEquals(0, player.getPosition().y);
    }

    @Test
    public void getInput() throws Exception {
        assertEquals(input, player.getInput());
    }

    @Test
    public void getPhysics() throws Exception {
        assertEquals(physics, player.getPhysics());
    }

    @Test
    public void getGraphics() throws Exception {
        assertEquals(graphics, player.getGraphics());
    }

    @Test
    public void getPosition() throws Exception {
        player.reset();
        player.setPosition(new Position(1, 3));
        assertEquals(1, player.getPosition().x);
        assertEquals(3, player.getPosition().y);
    }

    @Test
    public void setPosition() throws Exception {
        player.reset();
        player.setPosition(new Position(4, 1));
        assertEquals(4, player.getPosition().x);
        assertEquals(1, player.getPosition().y);
    }

    @Test
    public void setStartPosition() throws Exception {
        Position startPos = new Position(4, 4);
        player.setStartPosition(startPos);
        player.setPosition(new Position(1, 6));
        player.reset();
        assertEquals(startPos, player.getPosition());
    }

    @Test
    public void getStartPosition() throws Exception {
        Position startPos = new Position(4, 4);
        player.setStartPosition(startPos);
        assertEquals(startPos, player.getStartPosition());
    }
*/
}