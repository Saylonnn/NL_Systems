package NebenläufigeSysteme.Aufgaben;

import java.util.List;

public interface SensorInterface {

    void addObserver(ObserverInterface observer);
    void notifyObservers(int value);
    void start();
}
