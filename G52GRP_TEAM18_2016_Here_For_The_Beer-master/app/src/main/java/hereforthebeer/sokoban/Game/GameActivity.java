package hereforthebeer.sokoban.Game;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;

import hereforthebeer.sokoban.Level.MapCreators.RandomMapGenerator.RandomMapGenerator;
import hereforthebeer.sokoban.Pop;
import hereforthebeer.sokoban.R;
import hereforthebeer.sokoban.level_complete;

public class GameActivity extends AppCompatActivity {

    private GameThread gameThread;
    private InputStream mapStream = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //set XML view to focus
        setContentView(R.layout.activity_game);

        //Get level name to load
        if (!getIntent().getStringExtra("LOAD").contains("random")) {
            System.out.println("not a random level");
            mapStream = getResources().openRawResource(getId(getIntent().getStringExtra("LOAD"), R.raw.class));
        }

        //Get the level difficulty
        char difficulty = getIntent().getCharExtra("DIFFICULTY", 'M');
        System.out.println("Diff: " + difficulty);
        boolean toggleAI = getIntent().getBooleanExtra("AI", true);

        //Get reference to game view widget
        GameView gv = (GameView) findViewById(R.id.GameView);
        gv.addActivityReference(this);
        gameThread = new GameThread(this, gv, mapStream, difficulty, toggleAI);

        //Start the game thread
        gameThread.setRunning(true);
        gameThread.start();
    }

    /** Stops the game and waits for game thread to end */
    public void stopGame() {
        //Tell game thread to stop
        System.out.println("Stopping game thread");
        gameThread.setRunning(false);

        //Wait for game thread to stop
        boolean retry = true;
        while (retry) {
            try {
                gameThread.join();
                retry = false;
            } catch (InterruptedException e) {}
        }

        System.out.println("Going back to previous activity");
        if (gameThread.hasPlayerWon()) {

            if (gameThread.hasAiWon()) {
            } else {
                startActivity(new Intent(this, level_complete.class));
            }
        } else {
            this.finish();
        }

    }

    @Override
    protected void onDestroy() {
        //Call super
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        System.out.println("Stopping...");

        if (gameThread.hasPlayerWon()) {
            if (gameThread.hasAiWon()) {
                System.out.println("AI WON");
                //startActivity(new Intent(level_complete.class));
            } else {
                System.out.println("YOU WON");
            }
        }

        super.onStop();
    }

    /** Undo tap */
    public void onUndoTap(View v) {
        gameThread.undoLastPlayerMove();
    }

    /** Get resource ID from the name of the resource */
    public static int getId(String resourceName, Class<?> c) {
        try {
            Field idField = c.getDeclaredField(resourceName);
            return idField.getInt(idField);
        } catch (Exception e) {
            throw new RuntimeException("No resource ID found for: "
                    + resourceName + " / " + c, e);
        }
    }
}
