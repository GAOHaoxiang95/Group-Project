package hereforthebeer.sokoban.Util;

import android.graphics.drawable.Drawable;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by psyjk1 on 19/03/2017.
 */
public class DrawableManager {

    private HashMap<String, Drawable> images = new HashMap<>();

    public void addDrawable(String key, Drawable image) {
        images.put(key, image);
    }

    public Drawable getDrawable(String key) {
        return images.get(key);
    }
}
