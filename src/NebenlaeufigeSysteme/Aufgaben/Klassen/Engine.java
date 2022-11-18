package NebenlaeufigeSysteme.Aufgaben.Klassen;

import NebenlaeufigeSysteme.Aufgaben.Interfaces.MotorInterface;

import java.util.concurrent.TimeUnit;

public class Engine implements MotorInterface {
    private int eingeschlagen = 0;
    private int speed = 0;
    @Override
    public void lenken(int percent) {
        this.eingeschlagen = percent;
        System.out.println("Motor lenkt");
    }

    @Override
    public void fahren(int percent) {
        this.speed = percent;
        System.out.println("Motor fährt");
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
        return null;
    }

    //Um die Tests zu ermöglichen
    public int getEingeschlagen(){
        return this.eingeschlagen;
    }
    public int getSpeed(){
        return this.speed;
    }

    @Override
    public int getSteering() {
        return 0;
    }
}
