package hereforthebeer.sokoban.Util;

import android.graphics.drawable.Drawable;

import hereforthebeer.sokoban.Util.Audio.Audio;
import hereforthebeer.sokoban.Util.Audio.NullAudio;
import hereforthebeer.sokoban.Util.Input.Input;
import hereforthebeer.sokoban.Util.Log.Log;
import hereforthebeer.sokoban.Util.Log.NullLog;

/**
 * Created by James on 30/11/2016. <p\>
 * This class is used to access the singleton classes while allowing the freedom to use
 * potential inheritance/wrapper patterns to add in new functionality later
 */
public class ServiceLocator {
    //Store the currently active services
    private static Audio audioService;
    private static Log logService;
    private static Input inputService = new Input();
    private static DrawableManager drawableManager = new DrawableManager();

    //Save the storage path
    private static String storagePath = null;

    //Store null equivalent for each service
    private static Audio nullAudio = new NullAudio();
    private static Log nullLog = new NullLog();

    /** Provide the service locator with a logging service */
    public static void setLog(Log l) {
        logService = (l != null ? l : nullLog);
    }

    /** Provide the service locator with an audio service */
    public static void setAudio(Audio a) {
        audioService = (a != null ? a : nullAudio);
    }

    /** Provide a new drawable image to keep track of */
    public static void addDrawable(String key, Drawable image) {
        drawableManager.addDrawable(key, image);
    }

    /** Provide a new drawable image to keep track of, specify alpha */
    public static void addDrawable(String key, Drawable image, int alpha) {
        drawableManager.addDrawable(key, image);
        image.setAlpha(alpha);
    }

    /** Sets the alpha of an existing drawable */
    public static void setDrawableAlpha(String key, int alpha) {
        getDrawable(key).setAlpha(alpha);
    }

    /** Provide the path to the storage for the phone */
    public static void setFileStoragePath(String path) { storagePath = path; }



    /** Get the logging service stored inside the service locator */
    public static Log getLog() {
        return logService;
    }

    /** Get the audio service stored inside the service locator */
    public static Audio getAudio() {
        return audioService;
    }

    /** Gets the input service stored inside the service locator */
    public static Input getInput() {
        return inputService;
    }

    /** Get a drawable from the cache */
    public static Drawable getDrawable(String key) {
        return drawableManager.getDrawable(key);
    }

    /** Gets the absolute path to the file storage system */
    public static String getFileStoragePath() { return storagePath; }

}
