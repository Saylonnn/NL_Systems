package NebenlaeufigeSysteme.Classes;

import NebenlaeufigeSysteme.Interfaces.EngineInterface;
import NebenlaeufigeSysteme.Interfaces.ObserverInterface;
import NebenlaeufigeSysteme.Interfaces.SensorInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SilTest implements SensorInterface, EngineInterface {
    String sensorID;
    private List<ObserverInterface> observers = new ArrayList<>();
    int lenken = 0;
    int speed = 0;

    int sensor_fl = 0;
    int sensor_fr = 0;
    int sensor_bl = 0;
    int sensor_br = 0;

    ExecutorService exS = Executors.newFixedThreadPool(1);
    SilTest(){
        exS.submit((Callable<String>) () ->{
            start();
            return null;
        });
    }

    @Override
    public void setID(String id){
        sensorID = id;
    }

    @Override
    public void lenken(int percent) {
        lenken = percent;
    }

    @Override
    public void fahren(int percent) {
        speed = percent;
    }

    @Override
    public void addObserver(ObserverInterface observer) {
        observers.add(observer);
    }

    @Override
    public void notifyObservers(int value) {
        for(ObserverInterface x: observers){
            x.update("fl", sensor_fl);
            x.update("fr", sensor_fr);
            x.update("bl", sensor_bl);
            x.update("br", sensor_br);
        }
    }

    private void testGleich(int ist, int soll){
        if (ist == soll){
            System.out.println("[Test successfull] ist: "+ist + " soll: "+ soll);
        }
        else{
            System.out.println("[Test failed] ist: "+ist + " soll: "+ soll);
        }
    }

    private void test1(){
        sensor_fr = 50;
        sensor_fl = 60;
        notifyObservers(0);
        try{
            Thread.sleep(100);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        testGleich(speed, 255);
        testGleich(lenken, -10);
    }
    private void test2(){
        sensor_fr = 60;
        sensor_fl = 50;
        notifyObservers(0);
        try{
            Thread.sleep(100);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        testGleich(speed, 255);
        testGleich(lenken, 10);
    }

    public void start(){
        while(true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            test1();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            test2();
        }
    }
}
