package Testumgebung.KlassenOld;
/*

import NebenlaeufigeSysteme.Interfaces.EngineInterface;

import java.util.concurrent.TimeUnit;

public class Engine implements EngineInterface {
    public Engine(){};
    static boolean printDebugging = false;
    String id;

    Engine(String id){
        this.id = id;
    }
    private int steering = 0;
    private int speed = 0;
    @Override
    public void lenken(int percent) {
        this.steering = percent;
        print("steering " + percent);
    }

    @Override
    public void fahren(int percent) {
        this.speed = percent;
        print("driving " + percent);
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
    private void print(String s){
        if (printDebugging) {
            System.out.println("[Engine] " + s);
        }
    }
}
*/