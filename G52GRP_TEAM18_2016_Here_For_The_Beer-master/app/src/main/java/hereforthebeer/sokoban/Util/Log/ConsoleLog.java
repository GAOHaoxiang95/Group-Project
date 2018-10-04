package hereforthebeer.sokoban.Util.Log;

import android.os.Environment;

import java.io.File;

/**
 * Created by James on 30/11/2016. <p\>
 * Logs all given information to the console. <p\>
 * Used by ServiceLocator {@link hereforthebeer.sokoban.Util.ServiceLocator}
 */
public class ConsoleLog implements Log {
    //Docs defined in interface
    public void print(String s) {
        System.out.print(s);
    }

    //Docs defined in interface
    public void println(String s) {
        System.out.println(s);
    }
}
