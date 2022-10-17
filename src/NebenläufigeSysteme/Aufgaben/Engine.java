package NebenläufigeSysteme.Aufgaben;

import java.util.concurrent.TimeUnit;

public class Engine implements MotorInterface{
    private int eingeschlagen = 0;
    private int speed = 0;
    @Override
    public void lenken(int percent) {
        this.eingeschlagen = percent;
    }

    @Override
    public void fahren(int percent) {
        this.speed = percent;
    }

    @Override
    public void start() {
        try{
            TimeUnit.MILLISECONDS.sleep(1);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
    public int getEingeschlagen(){
        return this.eingeschlagen;
    }
    public int getSpeed(){
        return this.speed;
    }
}
