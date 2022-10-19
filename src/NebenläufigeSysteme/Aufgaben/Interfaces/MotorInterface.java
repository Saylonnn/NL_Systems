package NebenläufigeSysteme.Aufgaben.Interfaces;

public interface MotorInterface {
    void lenken(int percent);
    void fahren(int percent);
    void start();
}
