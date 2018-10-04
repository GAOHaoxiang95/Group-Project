package hereforthebeer.sokoban.Entity.Components.Input;

/**
 * Created by domanic on 22/03/17.
 */

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
public class NewPlayerInputComponent implements InputComponent, InputObserver {
    private int turn, canvasSize;
    private Entity player;
    private Level level;
    private ArrayList<Entity> boxes;

    public NewPlayerInputComponent(int canvasSize) {
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
        int screenSpaceX = level.offsetx + player.getPosition().x * level.CELL_SIZE + level.CELL_SIZE / 2;
        int screenSpaceY = level.offsety + player.getPosition().y * level.CELL_SIZE + level.CELL_SIZE / 2;
        int distX = screenSpaceX - eventPos.x;
        int distY = screenSpaceY - eventPos.y;
        Position delta = null;
        Position screenSpace = new Position (screenSpaceX, screenSpaceY);

        if (distance2(eventPos, screenSpace) < Math.pow(level.CELL_SIZE / 2.0, 2.0))
            return;

        if(Math.abs(distX) > Math.abs(distY)) {
            if(eventPos.x > screenSpaceX) {
                delta = TranslateCommand.Right;
            }
            else if (eventPos.x < screenSpaceX) {
                delta = TranslateCommand.Left;
            }
        } else {
            if(eventPos.y > screenSpaceY) {
                delta = TranslateCommand.Down;
            } else if (eventPos.y < screenSpaceY) {
                delta = TranslateCommand.Up;
            }
        }

        player.getCommandStream().addCommand(turn + 1, new TranslateCommand(player, delta, level, boxes));
    }
}