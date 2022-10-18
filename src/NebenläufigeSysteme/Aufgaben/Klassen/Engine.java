package NebenläufigeSysteme.Aufgaben.Klassen;

import NebenläufigeSysteme.Aufgaben.Interfaces.MotorInterface;

import java.util.concurrent.TimeUnit;

public class Engine implements MotorInterface {
    private int eingeschlagen = 0;
    private int speed = 0;
    @Override
    public void lenken(int percent) {
        this.eingeschlagen = percent;
        System.out.println("motor lenkt");
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

    //Um die Tests zu ermöglichen
    public int getEingeschlagen(){
        return this.eingeschlagen;
    }
    public int getSpeed(){
        return this.speed;
    }
}
