package hereforthebeer.sokoban.Entity.Commands;

import org.junit.Test;

import java.util.ArrayList;

import hereforthebeer.sokoban.Entity.Components.Graphics.AndroidGraphicsComponent;
import hereforthebeer.sokoban.Entity.Components.Graphics.GraphicsComponent;
import hereforthebeer.sokoban.Entity.Components.Input.DemoAIInputComponent;
import hereforthebeer.sokoban.Entity.Components.Input.InputComponent;
import hereforthebeer.sokoban.Entity.Components.Physics.InteractionPhysicsComponent;
import hereforthebeer.sokoban.Entity.Components.Physics.PhysicsComponent;
import hereforthebeer.sokoban.Entity.Entity;
import hereforthebeer.sokoban.Level.MapCreators.BlankMapCreator;
import hereforthebeer.sokoban.Level.Level;
import hereforthebeer.sokoban.Level.MapCreators.MapCreationInterface;
import hereforthebeer.sokoban.Util.Audio.AndroidAudio;
import hereforthebeer.sokoban.Util.Log.ConsoleLog;
import hereforthebeer.sokoban.Util.Log.Log;
import hereforthebeer.sokoban.Util.Position;
import hereforthebeer.sokoban.Util.ServiceLocator;

import static junit.framework.Assert.assertEquals;

public class CommandStreamTest {
/*
    private MapCreationInterface mapGenerator = new BlankMapCreator();
    private Level level;

    private InputComponent input = new DemoAIInputComponent();
    private PhysicsComponent physics = new InteractionPhysicsComponent();
    private GraphicsComponent graphics = new AndroidGraphicsComponent();
    private Entity player = new Entity(input, physics, graphics, level.getPlayerStart());
    private CommandStream playerStream = player.getCommandStream();

    private Log log;

    public CommandStreamTest() {
        ServiceLocator.setLog(new ConsoleLog());
        ServiceLocator.setAudio(new AndroidAudio());
        log = ServiceLocator.getLog();
        player.reset();
        mapGenerator.setMapSize(15, 15);
        level = new Level(mapGenerator, 0);
    }

    @Test
    public void generalUsageTest() throws Exception {
        TranslateCommand testCommand = new TranslateCommand(player, new Position(1, 0), level, new ArrayList<Entity>());
        playerStream.addCommand(0, testCommand);
        Command command = playerStream.getCommand(0);
        assertEquals(testCommand, command);
    }

    @Test
    public void loadFromFile() throws Exception {
        //To be implemented
    }

    @Test
    public void saveToFile() throws Exception {
        //To be implemented
    }*/
}