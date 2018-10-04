package hereforthebeer.sokoban.Game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

import java.io.InputStream;
import java.util.ArrayList;

import hereforthebeer.sokoban.Entity.Components.Graphics.AndroidGraphicsComponent;
import hereforthebeer.sokoban.Entity.Components.Graphics.GraphicsComponent;
import hereforthebeer.sokoban.Entity.Components.Input.InputComponent;
import hereforthebeer.sokoban.Entity.Components.Input.NewPlayerInputComponent;
import hereforthebeer.sokoban.Entity.Components.Input.NoInputComponent;
import hereforthebeer.sokoban.Entity.Components.Input.PlayerInputComponent;
import hereforthebeer.sokoban.Entity.Components.Physics.InteractionPhysicsComponent;
import hereforthebeer.sokoban.Entity.Components.Physics.PhysicsComponent;
import hereforthebeer.sokoban.Entity.Entity;
import hereforthebeer.sokoban.Entity.Components.Input.InputObserver;
import hereforthebeer.sokoban.Level.Level;
import hereforthebeer.sokoban.Level.MapCreators.FromFileMapCreator;
import hereforthebeer.sokoban.Level.MapCreators.MapCreationInterface;
import hereforthebeer.sokoban.Level.MapCreators.RandomMapGenerator.RandomMapGenerator;
import hereforthebeer.sokoban.Util.Position;
import hereforthebeer.sokoban.Util.ServiceLocator;

import hereforthebeer.sokoban.Entity.Components.Input.SokobanSolver.SolverComponent;
//import hereforthebeer.sokoban.Entity.Components.Input.NewAStarSolver.AStarComponent;
//import hereforthebeer.sokoban.Entity.Components.Input.AStarSolver.AStarComponent;

/**
 * Created by domanic on 08/03/17.
 */

public class GameEngine extends InputObserverManager {
    public Level level;
    public ArrayList<Entity> boxes, aiboxes;
    public Entity player, ai;
    public int turn;
    private MapCreationInterface mapGenerator;
    private boolean aiToggle, aiWin, playerWin;

    private GameThread thread;

    public GameEngine(GameThread thread, int screenWidth, int screenHeight, InputStream mapStream, int ups, char difficulty, boolean aiToggle) {
        //Assign thread
        this.thread = thread;
        this.aiToggle = aiToggle;
        aiWin = false;
        playerWin = false;

        //Create the map generator
        if (mapStream != null) {
            mapGenerator = new FromFileMapCreator(mapStream);
        } else {
            mapGenerator = new RandomMapGenerator(difficulty);
        }

        //Create the map from the map generation interface
        level = new Level(mapGenerator, 0);

        //Set cell size based on dimensions of screen and the size of the level
        level.CELL_SIZE = Math.min(screenWidth / level.getWidth(), screenHeight / level.getHeight());

        //Position level on screen
        level.offsetx = screenWidth / 2 - level.getWidth() * level.CELL_SIZE / 2;
        level.offsety = screenHeight / 2 - level.getHeight() * level.CELL_SIZE / 2 - 125;


        //Create general box components
        InputComponent boxInput = new NoInputComponent();
        PhysicsComponent boxPhysics = new InteractionPhysicsComponent();

        //Particular graphics based on the box
        GraphicsComponent boxGraphics = new AndroidGraphicsComponent(level.CELL_SIZE, ServiceLocator.getDrawable("crate_player"));
        GraphicsComponent aiboxGraphics = new AndroidGraphicsComponent(level.CELL_SIZE, ServiceLocator.getDrawable("crate_ai"));



        //Allocate player boxes and set them to spawn at the correct positions
        boxes = new ArrayList<>();
        ArrayList<Position> boxPositions = level.getBoxStartPositions();
        for (Position p : boxPositions) {
            Entity newBox = new Entity(boxInput, boxPhysics, boxGraphics, p);
            boxes.add(newBox);
        }

        //Create player entity
        // InputComponent noInput = new NoInputComponent();
        InputComponent oldPlayerInput = new PlayerInputComponent(Math.min(screenWidth, screenHeight));
        InputComponent newPlayerInput = new NewPlayerInputComponent(Math.min(screenWidth, screenHeight));
        PhysicsComponent playerPhysics = new InteractionPhysicsComponent();
        GraphicsComponent playerGraphics = new AndroidGraphicsComponent(level.CELL_SIZE, ServiceLocator.getDrawable("player"));
        player = new Entity(newPlayerInput, playerPhysics, playerGraphics, level.getPlayerStart());

        //cast the input component to an input observer because it implements inputObserver
        addObserver((InputObserver)newPlayerInput);

        if(aiToggle) {
            //Allocate ai boxes and set them to spawn at the correct positions
            aiboxes = new ArrayList<>();
            for (Position p : boxPositions) {
                Entity newBox = new Entity(boxInput, boxPhysics, aiboxGraphics, p);
                aiboxes.add(newBox);
            }

            //Create AI entity
            InputComponent AIInput = new SolverComponent(ups);
            PhysicsComponent AIPhysics = new InteractionPhysicsComponent();
            GraphicsComponent AIGraphics = new AndroidGraphicsComponent(level.CELL_SIZE, ServiceLocator.getDrawable("ai"));

            ai = new Entity(AIInput, AIPhysics, AIGraphics, this.level.getPlayerStart());
        }
    }

    public void update(int frame) {
        //Update global key states
        ServiceLocator.getInput().update();

        //Update input
        if(aiToggle)
            ai.getInput().update(ai, frame, level, aiboxes);
        player.getInput().update(player, frame, level, boxes);

        //Update physics
        if(aiToggle)
            ai.getPhysics().update(ai, frame);
        player.getPhysics().update(player, frame);
    }

    public void render(Canvas canvas) {
        if (checkIfaiWin()) {
            aiWin = true;
            Paint paint = new Paint();
            canvas.drawPaint(paint);
            paint.setColor(Color.RED);
            paint.setTextSize(64);
            canvas.drawText("Win found by AI", 50, 100, paint);
        }

        if (checkIfWin()) {
            playerWin = true;
            if (aiWin) {
                System.out.println("You lose!");
            }
            else
                System.out.println("You win!");
            System.out.println("Your score: " + (int)(player.getCommandStream().movesMade / 2 + player.getCommandStream().boxesMoved));
            thread.setRunning(false);
        }

        //Render level
        level.render(canvas);

        if(aiToggle) {
            //Draw ai's boxes
            for (Entity box : aiboxes) {
                box.getGraphics().update(box, canvas, level.offsetx, level.offsety);
            }

            //Draw ai
            ai.getGraphics().update(ai, canvas, level.offsetx, level.offsety);
        }

        //Draw player's boxes
        for (Entity box : boxes) {
            box.getGraphics().update(box, canvas, level.offsetx, level.offsety);
        }

        //Draw player
        player.getGraphics().update(player, canvas, level.offsetx, level.offsety);
    }

    public boolean checkIfWin() {
        //Check if any boxes are not at a goal
        for (Entity box : boxes) {
            if (!level.getTile(box.getPosition()).isGoal()) {
                return false;
            }
        }
        return true;
    }

    public boolean checkIfaiWin() {
        //Check if any boxes are not at a goal
        if(aiToggle){
            for (Entity box : aiboxes) {
                if (!level.getTile(box.getPosition()).isGoal()) {
                    return false;
                }
            }
            return true;
        }
       return false;
    }


    public Entity getPlayer() {
        return player;
    }

    public int getTurn() {
        return turn;
    }

    public boolean getAIWon() {
        return aiWin;
    }

    public boolean getPlayerWon() {
        return playerWin;
    }

    public void touchEvent(MotionEvent event, int canvasSize) {
        if (!thread.isRunning()) {
            return;
        }
        float x = event.getX();
        float y = event.getY();

        notifyObservers(event);
    }
}
