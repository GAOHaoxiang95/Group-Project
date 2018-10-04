package hereforthebeer.sokoban.Game;

/**
 * Created by domanic on 07/03/17.
 */

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

import java.lang.reflect.Field;

import hereforthebeer.sokoban.Entity.Commands.TranslateCommand;
import hereforthebeer.sokoban.Entity.Entity;
import hereforthebeer.sokoban.Level.Level;
import hereforthebeer.sokoban.R;
import hereforthebeer.sokoban.Util.Position;
import hereforthebeer.sokoban.Util.ServiceLocator;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private boolean surfaceAvailable = false;
    private GameEngine ge;
    private GameActivity ga;

    public GameView(final Context context, AttributeSet attrs) {
        super(context, attrs);

        getHolder().addCallback(this);

        setFocusable(true);
    }

    public void addListener(GameEngine ge) {
        this.ge = ge;
    }

    public void addActivityReference(GameActivity ga) {
        this.ga = ga;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (ge == null) {
            return true;
        }

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            //Pass touch info into InputManager
            int canvasSize = Math.min(getWidth(), getHeight());
            ge.touchEvent(event, canvasSize);
        }
        return true;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        //Stub
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        surfaceAvailable = true;
        ServiceLocator.getLog().println("Surface Available");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        surfaceAvailable = false;
        //ga.stopGame();
        ServiceLocator.getLog().println("Surface Unavailable");
    }

    /** Returns if the surface has been created */
    public boolean isSurfaceAvailable() {
        return surfaceAvailable;
    }
}