package NebenlaeufigeSysteme.Interfaces;

public interface SensorInterface {

    void addObserver(ObserverInterface observer);
    void notifyObservers(int value);

    void setID(String id);
}
