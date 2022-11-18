package NebenlaeufigeSysteme.Aufgaben.Interfaces;

public interface MotorInterface {

    void lenken(int percent);
    void fahren(int percent);
    void start();
    String getID();
    int getSpeed();
    int getSteering();
}
