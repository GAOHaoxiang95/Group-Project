package hereforthebeer.sokoban.Entity.Components.Input.SokobanSolver;

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
 * Created by James on 18/04/2017.
 */
public class SolverComponent implements InputComponent {

    //Store the updates per second of the engine
    //to disperse movement correctly for the AI
    private int ups;

    /** Only constructor for passing in how fast the engine updates */
    public SolverComponent(int engineUPS) {
        this.ups = engineUPS;
    }

    //Docs defined in interface
    public void update(Entity e, int turn, Level level, ArrayList<Entity> boxes) {
        //Only do computation if on the first turn
        if (turn > 0) {
            return;
        }

        //Obtain a reference to the command stream for the entity
        CommandStream cs = e.getCommandStream();

        //Create an array of all box positions
        ArrayList<Position> boxPositions = new ArrayList<>(boxes.size());
        for (Entity b : boxes) {
            boxPositions.add(b.getPosition());
        }

        //Calculate the deadlocked areas of the map
        boolean[] deadlocked = SokobanDeadlockMarker.getDeadlockedTiles(level);

        //Print out what the deadlocked tiles are
        SokobanDeadlockMarker.printDeadlockedLevel(level);

        //Obtain a copy of the entities position
        Position simPosition = e.getPosition();

        //Run sokoban solver to attempt to obtain a solution to the level
        ArrayList<SokobanSolver.SolutionStep> solution = SokobanSolver.solve(level, boxPositions, simPosition, deadlocked);

        //Check if a solution wasn't found
        if (solution == null) {
            ServiceLocator.getLog().println("ERROR: No possible solution to level found!");
            return;
        }

        //Create a random object
        Random rand = new Random();

        //Keep track of a simulated turn counter and initialize it to add a delay to the AI
        int turnCounter = (rand.nextInt(3) + 4) * ups;

        //Store the last moved box for adding delays
        int lastMovedBox = -1;

        //Loop through all steps in the solution
        for (SokobanSolver.SolutionStep step : solution) {
            //Get reference to the box's position
            Position origBoxPosition = boxPositions.get(step.box);

            //Get the destination for the box
            Position simDestination = new Position(origBoxPosition).translate(step.move.inverted());

            //Get the AStar path from the entity position to the entity destination
            ArrayList<Position> simPath = SokobanAStar.getPath(simPosition, simDestination, level, boxPositions);

            //Check if a path was not found
            if (simPath == null) {
                ServiceLocator.getLog().println("ERROR: Entity cannot path to location: " + simDestination);
                level.printMapToConsole(true, boxPositions, simPosition);
                return;
            }

            //Generate movement commands based on found path (0.25s - 0.35s between each movement)
            for (Position delta : simPath) {
                cs.addCommand(turnCounter, new TranslateCommand(e, delta, level, boxes));
                turnCounter += ((rand.nextDouble() * 0.1) + 0.25) * ups;
            }

            //Add command to move entity onto box position to push the box
            cs.addCommand(turnCounter, new TranslateCommand(e, step.move, level, boxes));
            turnCounter += ((rand.nextDouble() * 0.1) + 0.25) * ups;

            //Adjust turn counter to delay AI by 1.0s - 2.0s when switching boxes
            if (lastMovedBox != step.box) {
                turnCounter += (rand.nextDouble() + 0.7) * ups;
            }

            //Update simulated state
            simPosition.set(origBoxPosition);
            origBoxPosition.translate(step.move);
            lastMovedBox = step.box;
        }
    }

    //Docs defined in interface
    public void reset(Entity e) {
        //Intentionally empty
    }
}
