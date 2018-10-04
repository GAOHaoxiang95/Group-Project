package hereforthebeer.sokoban.Entity.Components.Input;

import java.io.InputStream;
import java.util.ArrayList;

import hereforthebeer.sokoban.Entity.Commands.Command;
import hereforthebeer.sokoban.Entity.Commands.TranslateCommand;
import hereforthebeer.sokoban.Entity.Entity;
import hereforthebeer.sokoban.Level.Level;
import hereforthebeer.sokoban.Util.Position;
import hereforthebeer.sokoban.Util.ServiceLocator;


/**
 * Created by James on 27/02/2017.
 * Loads in a command stream for an AI from a file. Replays the sequence as the game updates.
 */
public class FromFileInputComponent implements InputComponent {

    private ArrayList<Character> inputCharStream;

    public FromFileInputComponent(InputStream file) {
        loadReplay(file);
    }

    //Docs provided in interface
    public void update(Entity e, int turn, Level level, ArrayList<Entity> boxes) {
        Position newPosition;
        if (turn > inputCharStream.size()) {
            return;
        }

        switch (inputCharStream.get(turn)) {
            case 'L':
                newPosition = TranslateCommand.Left;
                break;
            case 'U':
                newPosition = TranslateCommand.Up;
                break;
            case 'R':
                newPosition = TranslateCommand.Right;
                break;
            case 'D':
                newPosition = TranslateCommand.Down;
                break;
            default:
                ServiceLocator.getLog().println("Error reading char from input file");
                return;
        }
        e.getCommandStream().addCommand(turn, new TranslateCommand(e, newPosition, level, boxes));
    }

    //Docs provided in interface
    public void reset(Entity e) {
        //No reset needed
    }

    /** Loads a replay into an entities command stream */
    public void loadReplay(InputStream file) {
        inputCharStream = new ArrayList<>();

        int input;
        try {
            while ((input = file.read()) != -1) {
                char c = (char) input;
                inputCharStream.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
