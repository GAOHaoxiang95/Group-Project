package hereforthebeer.sokoban.Util.Audio;

/**
 * Created by James on 30/11/2016. <p\>
 * This class is a placeholder for if a suitable audio engine is not
 * given to the ServiceLocator
 *
 * @see hereforthebeer.sokoban.Util.ServiceLocator
 */
public class NullAudio implements Audio {
    //Docs defined in interface
    public void playSound(int soundID) {
        //Intentionally empty
    }

    //Docs defined in interface
    public void stopSound(int soundID) {
        //Intentionally empty
    }

    //Docs defined in interface
    public void stopAllSounds() {
        //Intentionally empty
    }
}
