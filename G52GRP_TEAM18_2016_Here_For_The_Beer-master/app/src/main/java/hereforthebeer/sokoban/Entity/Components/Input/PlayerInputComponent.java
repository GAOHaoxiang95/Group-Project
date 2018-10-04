package hereforthebeer.sokoban.Entity.Components.Input;

import android.view.MotionEvent;

import java.util.ArrayList;

import hereforthebeer.sokoban.Entity.Commands.TranslateCommand;
import hereforthebeer.sokoban.Entity.Entity;
import hereforthebeer.sokoban.Level.Level;
import hereforthebeer.sokoban.Util.Position;

/**
 * Created by James on 18/11/2016 <p\>
 * This InputComponent will handle user input from Android devices and put Command objects into the
 * player's CommandStream
 */
public class PlayerInputComponent implements InputComponent, InputObserver {
    private int turn, canvasSize;
    private Entity player;
    private Level level;
    private ArrayList<Entity> boxes;

    public PlayerInputComponent(int canvasSize) {
        this.canvasSize = canvasSize;
    }

    //Docs defined in interface
    public void update(Entity e, int turn, Level level, ArrayList<Entity> boxes) {
        player = e;
        this.level = level;
        this.boxes = boxes;
        this.turn = turn;
    }

    //Docs defined in interface
    public void reset(Entity e) {
        //To be implemented
    }

    /** Calculates the euclidean squared distance between two points */
    private double distance2(Position p1, Position p2) {
        return Math.pow(p1.x - p2.x, 2.0) + Math.pow(p1.y - p2.y, 2.0);
    }

    @Override
    public void onNotify(MotionEvent event) {

        Level l = level;
        int x0 = l.offsetx;
        int y0 = l.offsety;


        Position[] centers = {
                new Position(x0 + canvasSize / 2, y0),
                new Position(x0 + canvasSize / 2, y0 + canvasSize),
                new Position(x0, y0 + canvasSize / 2),
                new Position(x0 + canvasSize, y0 + canvasSize / 2)
        };

        Position eventPos = new Position((int) event.getX(), (int) event.getY());

        int minIndex = 0;
        double minDist = distance2(eventPos, centers[0]);
        for (int i = 1; i < 4; ++i) {
            double dist = distance2(eventPos, centers[i]);
            if (dist < minDist) {
                minDist = dist;
                minIndex = i;
            }
        }

        Position delta = null;

        switch (minIndex) {
            case 0:
                delta = TranslateCommand.Up;
                break;
            case 1:
                delta = TranslateCommand.Down;
                break;
            case 2:
                delta = TranslateCommand.Left;
                break;
            default:
                delta = TranslateCommand.Right;
                break;
        }

        player.getCommandStream().addCommand(turn + 1, new TranslateCommand(player, delta, level, boxes));
    }
}
