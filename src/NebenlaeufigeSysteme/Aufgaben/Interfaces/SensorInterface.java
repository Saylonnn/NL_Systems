package NebenlaeufigeSysteme.Aufgaben.Interfaces;

public interface SensorInterface {

    void addObserver(ObserverInterface observer);
    void notifyObservers(int value);
    void start();
}
