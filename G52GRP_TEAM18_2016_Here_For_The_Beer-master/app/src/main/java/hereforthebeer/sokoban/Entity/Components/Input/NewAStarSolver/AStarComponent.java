package hereforthebeer.sokoban.Entity.Components.Input.NewAStarSolver;

import java.util.ArrayList;
import java.util.Random;

import hereforthebeer.sokoban.Entity.Commands.CommandStream;
import hereforthebeer.sokoban.Entity.Commands.TranslateCommand;
import hereforthebeer.sokoban.Entity.Components.Input.InputComponent;
import hereforthebeer.sokoban.Entity.Entity;
import hereforthebeer.sokoban.Level.Level;
import hereforthebeer.sokoban.Util.Position;
import hereforthebeer.sokoban.Util.ServiceLocator;

/**
 * Created by James on 07/03/2017.
 * Optimized version of AStarComponent.
 */
public class AStarComponent implements InputComponent {

    private int ups;

    public AStarComponent(int ups) {
        this.ups = ups;
    }

    //Docs defined in interface
    public void update(Entity e, int turn, Level level, ArrayList<Entity> boxList) {
        //Exit if the turn isn't the first one
        if (turn != 0) {
            return;
        }

        //Get a reference to the entities command stream
        CommandStream cs = e.getCommandStream();

        //Convert our array list of box entities into a fixed array containing their positions
        Position[] boxes = new Position[boxList.size()];
        for (int i = 0; i < boxes.length; ++i) {
            boxes[i] = boxList.get(i).getPosition();
        }

        //TEMPORARY
        ArrayList<Position> boxPositions = new ArrayList<Position>();
        for (Position b : boxes) {
            boxPositions.add(b);
        }

        //Get which tiles are deadlocked to save node expansions
        //boolean[] deadlocked = AStarPreProcessor.getDeadlockedTiles(level);
        boolean[] deadlocked = new boolean[64];
        for (boolean b : deadlocked)
                b = false;

        //Get the solution to the given level in terms of box pushes
        ArrayList<BoxSolution.SolutionStep> solution = BoxSolution.solve(level, boxes, e.getPosition(), deadlocked);

        //Check if a solution wasn't found
        if (solution == null) {
            ServiceLocator.getLog().println("AStar box push path not found");
            return;
        }

        //Create internal reference to the entity position
        Position ePosition = new Position(e.getPosition());

        //Create random object
        Random rand = new Random();

        //Iterate over the solution to add commands to the stream
        int turnCounter = (int) ((4.0 + rand.nextDouble() * 3.0) * ups);
        for (BoxSolution.SolutionStep step : solution) {
            //Get the destination for the entity based on deducting the delta from the box position
            Position eDestination = new Position(boxes[step.box]).translate(-step.delta.x, -step.delta.y);

            //Get the path to the destination for the entity
            ArrayList<Position> ePath = null; //AStar(ePosition, level, eDestination, boxPositions);

            //Check if a path was found
            if (ePath == null) {
                ServiceLocator.getLog().println("No AStar entity path found");
                return;
            }

            //Generate movement commands to move to destination in engine
            for (Position delta : ePath) {
                cs.addCommand(turnCounter, new TranslateCommand(e, delta, level, boxList));
                turnCounter += 0.3 * ups;
            }

            //Add the command to move the entity on top of the box to push it
            cs.addCommand(turnCounter++, new TranslateCommand(e, step.delta, level, boxList));

            //Adjust the turn counter to seem more realistic
            turnCounter += (int) ((1.0 + rand.nextDouble() * 3.0) * ups);

            //Update internal state
            ePosition.set(boxes[step.box]);
            boxes[step.box].translate(step.delta);
        }
    }

    public void reset(Entity e) {

    }
}
