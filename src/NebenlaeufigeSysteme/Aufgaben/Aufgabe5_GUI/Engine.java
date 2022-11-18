package NebenlaeufigeSysteme.Aufgaben.Aufgabe5_GUI;

import NebenlaeufigeSysteme.Aufgaben.Interfaces.MotorInterface;

import java.util.concurrent.TimeUnit;

public class Engine implements MotorInterface {
    String id;

    Engine(String id){
        this.id = id;
    }
    private int steering = 0;
    private int speed = 0;
    @Override
    public void lenken(int percent) {
        this.steering = percent;
    }

    @Override
    public void fahren(int percent) {
        this.speed = percent;
    }

    //Dauerschleife damit der Thread aktiv bleibt.
    @Override
    public void start() {
        try{
            TimeUnit.MILLISECONDS.sleep(1);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    @Override
    public String getID() {
        return id;
    }

    //Um die Tests zu ermöglichen
    public int getSteering(){
        return this.steering;
    }
    public int getSpeed(){
        return this.speed;
    }
}
