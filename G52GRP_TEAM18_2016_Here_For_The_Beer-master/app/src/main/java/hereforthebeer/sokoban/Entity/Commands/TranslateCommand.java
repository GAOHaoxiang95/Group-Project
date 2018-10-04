package hereforthebeer.sokoban.Entity.Commands;

import java.util.ArrayList;

import hereforthebeer.sokoban.Entity.Entity;
import hereforthebeer.sokoban.Level.Level;
import hereforthebeer.sokoban.Util.Position;

/**
 * Created by James on 24/11/2016.
 * A wrapper for the MoveCommand object that allows the pushing of boxes
 */
public class TranslateCommand implements Command {

    //Keep the most common translates for saving later initialization
    public static final Position Up = new Position(0, -1);
    public static final Position Down = new Position(0, 1);
    public static final Position Left = new Position(-1, 0);
    public static final Position Right = new Position(1, 0);

    //Store the entity effected and its position once this executes
    Entity e;
    Position prev;

    //Store the level and the boxes
    Level l;
    ArrayList<Entity> boxes;

    //Store the translation to be applied
    Position translation;

    Position boxMovedFrom = null;
    Entity boxMoved = null;

    /** Provide the level and a list of boxes to move */
    public TranslateCommand(Entity e, Position translation, Level l, ArrayList<Entity> boxes) {
        this.e = e;
        this.translation = translation;
        this.l = l;
        this.boxes = boxes;
    }

    //Docs defined in interface
    public void execute() {
        //Track box positions
        Entity pushBox = null;
        Entity issueBox = null;

        //Get position of second tile in direction you are moving
        Position dest = new Position(e.getPosition().translate(translation));
        Position issuePosition = new Position(e.getPosition().translate(translation.x * 2, translation.y * 2));

        //Loop through all boxes and keep track of any boxes that show up
        //where we are looking to move
        for (Entity box : boxes) {
            if (pushBox == null && box.getPosition().equals(dest))
                pushBox = box;
            else if (issueBox == null && box.getPosition().equals(issuePosition))
                issueBox = box;
        }


        //Check movement conditions
        prev = e.getPosition();
        if (l.getTile(dest).isCollidable()) //If moving into a wall
            return;
        else if (pushBox == null) { //If no box where we want to move
            //just update the player location
            prev = e.getPosition();
            e.setPosition(dest);
            e.getCommandStream().movesMade += 1;
        } else if (!l.getTile(issuePosition).isCollidable() && issueBox == null) { //If can move box
            //Store the box to move and its position
            boxMoved = pushBox;
            boxMovedFrom = pushBox.getPosition();

            //Update its position
            pushBox.setPosition(issuePosition);

            //Store player position and move player
            prev = e.getPosition();
            e.setPosition(dest);

            e.getCommandStream().boxesMoved += 1;
            e.getCommandStream().movesMade += 1;
        }
    }

    //Docs defined in interface
    public boolean undo() {
        //If Player wasn't moved or no move is stored then exit
        if (prev == null || prev.equals(e.getPosition())) {
            return false;
        }

        //If a box was moved then move it back
        if (boxMoved != null) {
            boxMoved.setPosition(boxMovedFrom);
            e.getCommandStream().boxesMoved -= 1;
        }

        //Move the player
        e.setPosition(prev);

        e.getCommandStream().movesMade -= 1;
        return true;
    }

    //Docs defined in interface
    public String getFileChar() {
        //Check if the movement is a pre-set easy movement
        if (translation.equals(Up)) { return "U"; }
        if (translation.equals(Down)) { return "D"; }
        if (translation.equals(Left)) { return "L"; }
        if (translation.equals(Right)) { return "R"; }

        //Otherwise encode the movement
        return "S\n" + translation.x + "\n" + translation.y;
    }
}
