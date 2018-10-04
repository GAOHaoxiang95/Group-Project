package hereforthebeer.sokoban.Util.Audio;

/**
 * Created by James on 30/11/2016. <p\>
 * Audio interface to allow us to build different audio systems
 * if we desire later on
 */
public interface Audio {
    /** Plays a given sound */
    void playSound(int soundID);

    /** Stops playing a sound */
    void stopSound(int soundID);

    /** Stops playing all sounds */
    void stopAllSounds();
}
