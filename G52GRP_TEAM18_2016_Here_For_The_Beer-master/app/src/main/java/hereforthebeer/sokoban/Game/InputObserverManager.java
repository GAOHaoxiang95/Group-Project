package hereforthebeer.sokoban.Game;

import android.view.MotionEvent;

import java.util.ArrayList;

import hereforthebeer.sokoban.Entity.Components.Input.InputObserver;

/**
 * Created by domanic on 22/03/17.
 */

public class InputObserverManager {
    private ArrayList<InputObserver> observers = new ArrayList<>();

    public void addObserver(InputObserver observer) {
        if(!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    public void removeObserver(InputObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers(MotionEvent event) {
        for (InputObserver io : observers) {
            io.onNotify(event);
        }
    }
}
