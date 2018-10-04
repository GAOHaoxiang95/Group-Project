package hereforthebeer.sokoban.Util.Input;

/**
 * Created by James on 30/11/2016. <p\>
 * Input abstract class to allow multiple implementations of input
 */
public class Input {

    //Keep track of how many keys exist
    private static int NUM_KEYS = 4;

    //Store a list of public static references to key ID's
    public static int KEY_1     = 0;
    public static int KEY_2     = 1;
    public static int KEY_3     = 2;
    public static int KEY_4     = 3;

    //Store the state of all keys and their previous state
    private static boolean keys[] = new boolean[NUM_KEYS];
    private static boolean prevKeys[] = new boolean[NUM_KEYS];

    /** Updates input (Must be called after updating inputs in game) */
    public void update() {
        for (int i = 0; i < NUM_KEYS; ++i) {
            prevKeys[i] = keys[i];
        }
    }

    /** Presses a key
     * @param keyID The ID of a static key from this class */
    public void pressKey(int keyID) {
        if (!inRange(keyID)) {
            return;
        }
        keys[keyID] = true;
    }

    /** Checks if a key is held down right now
     * @param keyID The ID of a static key from this class */
    public boolean isKeyDown(int keyID) {
        if (!inRange(keyID)) {
            return false;
        }
        return keys[keyID];
    }

    /** Checks if a key is pressed
     * @param keyID The ID of a static key from this class */
    public boolean isKeyPressed(int keyID) {
        if (!inRange(keyID)) {
            return false;
        }
        return (keys[keyID] && !prevKeys[keyID]);
    }

    /** Checks if a key is released
     * @param keyID The ID of a static key from this class */
    public boolean isKeyReleased(int keyID) {
        if (!inRange(keyID)) {
            return false;
        }
        return (!keys[keyID] && prevKeys[keyID]);
    }

    /** Checks if a specific keyID is valid
     * @param keyID The ID of a static key from this class */
    private boolean inRange(int keyID) {
        return (keyID >= 0 && keyID < NUM_KEYS);
    }
}
