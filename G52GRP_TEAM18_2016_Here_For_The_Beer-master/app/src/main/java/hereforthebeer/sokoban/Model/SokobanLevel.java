package hereforthebeer.sokoban.Model;

import android.view.View;

import java.io.InputStream;
import java.util.ArrayList;

import hereforthebeer.sokoban.Entity.Components.Graphics.AndroidGraphicsComponent;
import hereforthebeer.sokoban.Entity.Components.Graphics.GraphicsComponent;
import hereforthebeer.sokoban.Entity.Components.Input.InputComponent;
import hereforthebeer.sokoban.Entity.Components.Input.NoInputComponent;
import hereforthebeer.sokoban.Entity.Components.Physics.InteractionPhysicsComponent;
import hereforthebeer.sokoban.Entity.Components.Physics.PhysicsComponent;
import hereforthebeer.sokoban.Entity.Entity;
import hereforthebeer.sokoban.Level.Level;
import hereforthebeer.sokoban.Level.MapCreators.MapCreationInterface;
import hereforthebeer.sokoban.Util.Position;
import hereforthebeer.sokoban.Util.ServiceLocator;

//import hereforthebeer.sokoban.Entity.Components.Input.AStarSolver.AStarComponent;

/**
 * Created by James on 14/02/2017 <p\>
 * Class that handles running a level on the device
 */
public class SokobanLevel {

    View view;

    Level level;
    ArrayList<Entity> boxes;
    Entity player;

    public SokobanLevel(MapCreationInterface mapGenerator, View v) {/*
        //Store the view to draw things to
        view = v;

        //Create the map from the map generation interface
        level = new Level(mapGenerator, 0);

        //Create box state variables
        InputComponent boxInput = new NoInputComponent();
        PhysicsComponent boxPhysics = new InteractionPhysicsComponent();
        GraphicsComponent boxGraphics = new AndroidGraphicsComponent();

        //Allocate boxes and set them to spawn at the correct positions
        boxes = new ArrayList<>();
        ArrayList<Position> boxPositions = level.getBoxStartPositions();
        for (Position p : boxPositions) {
            Entity newBox = new Entity(boxInput, boxPhysics, boxGraphics, p);
            boxes.add(newBox);
            newBox.reset();
        }

        //Create player entity
        InputComponent input = new DemoAIInputComponent();
        PhysicsComponent physics = new InteractionPhysicsComponent();
        GraphicsComponent graphics = new AndroidGraphicsComponent();
        player = new Entity(input, physics, graphics, level.getPlayerStart());
    */}

    /** Runs game loop for the game */
    public void run() {
        //Reset all of players components
        player.reset();

        //Print the level
        ServiceLocator.getLog().println("Map Init:");
        level.printMapToConsole(false, boxes, player);

        //Game loop
        boolean finished = false;
        int frame = 0;
        while (!finished) {
            //Update global key states
            ServiceLocator.getInput().update();

            //Update entities
            player.getInput().update(player, frame, level, boxes);
            player.getPhysics().update(player, frame);
            //player.getGraphics().update(player, view);

            //Print the map state to console
            ServiceLocator.getLog().println("Map Frame " + frame + ":");
            level.printMapToConsole(false, boxes, player);

            //If all boxes are at a goal
            if (checkIfWin()) {
                //Print that all boxes made it to their goals
                ServiceLocator.getLog().println("GONGRATULATIONS, YOU WIN!");
                finished = true;
            }

            //Move onto the next frame
            ++frame;

            //TEMP BREAK CODE
            //if (frame > 0)
            //    finished = true;
        }
    }

    private boolean checkIfWin() {
        //Check if any boxes are not at a goal
        for (Entity box : boxes) {
            if (!level.getTile(box.getPosition()).isGoal()) {
                return false;
            }
        }
        return true;
    }
}
