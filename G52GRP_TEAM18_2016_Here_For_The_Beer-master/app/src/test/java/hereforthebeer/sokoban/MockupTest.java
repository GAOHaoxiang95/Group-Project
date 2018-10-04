package hereforthebeer.sokoban;

import org.junit.Test;

import java.util.ArrayList;

import hereforthebeer.sokoban.Entity.Components.Graphics.AndroidGraphicsComponent;
import hereforthebeer.sokoban.Entity.Components.Graphics.GraphicsComponent;
import hereforthebeer.sokoban.Entity.Components.Graphics.NullGraphicsComponent;
import hereforthebeer.sokoban.Entity.Components.Input.InputComponent;
import hereforthebeer.sokoban.Entity.Components.Input.NoInputComponent;
import hereforthebeer.sokoban.Entity.Components.Physics.InteractionPhysicsComponent;
import hereforthebeer.sokoban.Entity.Components.Physics.PhysicsComponent;
import hereforthebeer.sokoban.Entity.Entity;
import hereforthebeer.sokoban.Level.Level;
import hereforthebeer.sokoban.Level.MapCreators.BlankMapCreator;
import hereforthebeer.sokoban.Level.MapCreators.MapCreationInterface;
import hereforthebeer.sokoban.Util.Audio.AndroidAudio;
import hereforthebeer.sokoban.Util.Audio.NullAudio;
import hereforthebeer.sokoban.Util.Log.ConsoleLog;
import hereforthebeer.sokoban.Util.Position;
import hereforthebeer.sokoban.Util.ServiceLocator;

import static org.junit.Assert.*;

public class MockupTest {
    /*
    @Test
    public void testRun() throws Exception {
        //Handle service locator needs
        ServiceLocator.setLog(new ConsoleLog());
        ServiceLocator.setAudio(new NullAudio());

        //Create the map
        int mapWidth = 6;
        int mapHeight = 6;
        MapCreationInterface mapGenerator = new BlankMapCreator();
        Level level = new Level(mapWidth, mapHeight, mapGenerator, 0);
        assertNotNull("Level should exist", level);

        //Initialize the box components
        InputComponent boxInput = new NoInputComponent();
        PhysicsComponent boxPhysics = new InteractionPhysicsComponent();
        GraphicsComponent boxGraphics = new AndroidGraphicsComponent();
        assertNotNull("Box InputComponent should exist", boxInput);
        assertNotNull("Box PhysicsComponent should exist", boxPhysics);
        assertNotNull("Box GraphicsComponent should exist", boxGraphics);

        //Create boxes and set them to spawn at the correct positions
        ArrayList<Position> boxPositions = level.getBoxStartPositions();
        ArrayList<Entity> boxes = new ArrayList<>();
        for (Position p : boxPositions) {
            Entity newBox = new Entity(boxInput, boxPhysics, boxGraphics, p);
            boxes.add(newBox);
            newBox.reset();
            assertEquals("New boxes should be placed correctly", newBox.getPosition(), p);
        }
        assertEquals("Box count should be 1", 1, boxes.size());

        //Player components
        InputComponent input = new DemoBatchInputComponent();
        PhysicsComponent physics = new InteractionPhysicsComponent();
        GraphicsComponent graphics = new NullGraphicsComponent();
        assertNotNull("Player InputComponent should exist", input);
        assertNotNull("Player PhysicsComponent should exist", physics);
        assertNotNull("Player GraphicsComponent should exist", graphics);

        Entity player = new Entity(input, physics, graphics, level.getPlayerStart());
        assertNotNull("Player should exist", player);
        player.reset();

        //Print player position before any updates
        Position p = player.getPosition();
        assertEquals("Player not starting in right place", new Position(mapWidth / 2, mapHeight / 2), p);

        for (int i = 0; i < 14; ++i) {
            //Update the players components once
            input.update(player, i, level, boxes);
            physics.update(player, i, level);
            graphics.update(player, null);

            //Check if all boxes are at a goal point
            boolean allBoxesAtGoal = true;
            for (Entity box : boxes) {
                if (!level.getTile(box.getPosition()).isGoal()) {
                    allBoxesAtGoal = false;
                    break;
                }
            }
            if (allBoxesAtGoal) {
                System.out.println("GONGRATULATIONS, YOU WIN!");
                break;
            }
        }
        assertEquals("Player finished in correct place", new Position(2, 2), player.getPosition());
    }
    */
}
