package hereforthebeer.sokoban.Entity;

import hereforthebeer.sokoban.Entity.Commands.CommandStream;
import hereforthebeer.sokoban.Entity.Components.Graphics.GraphicsComponent;
import hereforthebeer.sokoban.Entity.Components.Input.InputComponent;
import hereforthebeer.sokoban.Entity.Components.Physics.PhysicsComponent;
import hereforthebeer.sokoban.Util.Position;

/**
 * Created by James on 18/11/2016 <p\>
 * This object is a collection of Components that make up an in-game entity.
 */
public class Entity {
    //Components
    private InputComponent inputComponent;
    private PhysicsComponent physicsComponent;
    private GraphicsComponent graphicsComponent;

    //Private data about the entity
    private Position pos = new Position();
    private Position startPosition = new Position();
    private CommandStream commands = new CommandStream();

    //TEMPORARY
    public int replayIndex = 0;

    /** Constructor for creating an Entity with its components */
    public Entity(InputComponent input, PhysicsComponent physics, GraphicsComponent graphics, Position start) {
        inputComponent = input;
        physicsComponent = physics;
        graphicsComponent = graphics;
        startPosition = start;
    }

    /** Resets the entity and all its components */
    public void reset() {
        inputComponent.reset(this);
        physicsComponent.reset(this);
        graphicsComponent.reset(this);
        setPosition(startPosition);
    }

    /** Returns the InputComponent used by this entity */
    public InputComponent getInput() { return inputComponent; }

    /** Returns the PhysicsComponent used by this entity */
    public PhysicsComponent getPhysics() { return physicsComponent; }

    /** Returns the GraphicsComponent used by this entity */
    public GraphicsComponent getGraphics() { return graphicsComponent; }

    /** Returns the CommandStream used by this entity */
    public CommandStream getCommandStream() { return commands; }

    /** Gets the current position of the Entity */
    public Position getPosition() { return new Position(pos); }

    /** Sets the position of the Entity on the map */
    public void setPosition(Position pos) { this.pos.set(pos); }

    /** Set the start position of the Entity */
    public void setStartPosition(Position pos) { startPosition = pos; }

    /** Get the start position of the Entity */
    public Position getStartPosition() { return new Position(startPosition); }
}
