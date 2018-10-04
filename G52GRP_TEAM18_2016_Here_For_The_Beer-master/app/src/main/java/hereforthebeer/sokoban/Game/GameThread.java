package hereforthebeer.sokoban.Game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import java.io.File;
import java.io.InputStream;
import java.util.Date;

import hereforthebeer.sokoban.Entity.Entity;
import hereforthebeer.sokoban.Util.Input.Input;
import hereforthebeer.sokoban.Util.ServiceLocator;

/**
 * Created by domanic on 07/03/17.
 */


public class GameThread extends Thread {
    private final int UPS = 30;
    private final long DELAY = 1000 / UPS; //Milliseconds



    private GameEngine engine;
    private GameActivity gameActivity;

    private InputStream mapStream;

    private GameView gameView;
    private SurfaceHolder surfaceHolder;
    private char difficulty;
    private boolean running;
    private boolean aiToggle;
    public boolean isRunning() { return running; }
    public void setRunning(boolean running) {
        this.running = running;
    }

    public boolean hasPlayerWon () {
        return engine.getPlayerWon();
    }

    public boolean hasAiWon() {
        return engine.getAIWon();
    }

    public GameThread(GameActivity gameActivity, GameView gameView, InputStream mapStream, char difficulty, boolean toggleAI) {
        super();

        //Store reference to game activity
        this.gameActivity = gameActivity;

        //Store reference to GameView class
        this.gameView = gameView;

        //Get surface holder
        surfaceHolder = gameView.getHolder();

        //Store map stream
        this.mapStream = mapStream;

        //Store Difficulty
        this.difficulty = difficulty;

        //Store ai toggle
        this.aiToggle = toggleAI;
    }

    @Override
    public void run() {
        //Wait for canvas to be ready
        while (!gameView.isSurfaceAvailable() && running) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {}
        }

        //aiToggle = true;
        //Create the game engine
        engine = new GameEngine(this, gameView.getWidth(), gameView.getHeight(), mapStream, UPS, difficulty, aiToggle);

        //Add the engine to receive input events
        gameView.addListener(engine);

        //Set turn to start
        engine.turn = 0;

        //Reset entities to their starting positions
        for (Entity box : engine.boxes) {
            box.reset();
        }
        if(aiToggle){
            for (Entity box : engine.aiboxes) {
                box.reset();
            }

            engine.ai.reset();
        }
        engine.player.reset();

        //Print map to console
        engine.level.printMapToConsole(false, engine.boxes, engine.player);

        //Create reference to canvas for drawing
        Canvas canvas;
        System.out.println("STARTING GAME LOOP");

        //Process game loop until stopped externally
        while (running) {
            //get the time at the start of the loop
            long startTime = System.currentTimeMillis();

            //Null the canvas reference
            canvas = null;

            //Check if the surface is available to draw on
            if (gameView.isSurfaceAvailable()) {
                //Try to lock the canvas for exclusive pixel editing on the surface
                try {
                    //Get canvas for drawing
                    canvas = surfaceHolder.lockCanvas();
                    // update game state
                    engine.update(engine.turn);
                    //Synchronize with the drawable surface
                    synchronized (surfaceHolder) {
                        //Wrap drawing in try catch in-case canvas becomes null
                        try {
                            // draws the canvas on the panel
                            engine.render(canvas);
                        } catch (NullPointerException e) {
                            e.printStackTrace();
                        }

                    }
                } finally {
                    // in case of an exception the surface is not left in
                    // an inconsistent state
                    if (canvas != null) {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                }

                //Check if player won the game
                //if (engine.getPlayerWon() && engine.getAIWon()) {
                //    System.out.println("WIN FOUND by both");
                //    //Set game loop to end asa
                //    setRunning(false);
                //}

                //Increment turn number
                engine.turn += 1;
            }

            //getting the delay offset
            long frameTime = System.currentTimeMillis() - startTime;

            //In case the needed delay is less than 0
            if (DELAY - frameTime > 0) {
                //Delay the thread to cap FPS
                try {
                    Thread.sleep(DELAY - frameTime);
                } catch (InterruptedException ex) {
                }
            }
        }

        System.out.println("Thread Terminated");
        gameActivity.finish();
    }

    /** Undoes the last movement by the player */
    public void undoLastPlayerMove() {
        engine.player.getCommandStream().undoLastCommand();
    }
}

