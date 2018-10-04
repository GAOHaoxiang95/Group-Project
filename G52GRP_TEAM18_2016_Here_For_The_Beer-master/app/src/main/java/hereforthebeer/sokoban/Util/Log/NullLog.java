package hereforthebeer.sokoban.Util.Log;

/**
 * Created by James on 30/11/2016. <p\>
 * This class is a placeholder for if a suitable logging class is not
 * given to the ServiceLocator
 *
 * @see hereforthebeer.sokoban.Util.ServiceLocator
 */
public class NullLog implements Log {
    //Docs defined in interface
    public void print(String s) {
        //Intentionally empty
    }

    //Docs defined in interface
    public void println(String s) {
        //Intentionally empty
    }
}
