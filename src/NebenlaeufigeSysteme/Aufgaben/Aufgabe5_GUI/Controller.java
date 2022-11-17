package NebenlaeufigeSysteme.Aufgaben.Aufgabe5_GUI;

import NebenlaeufigeSysteme.Aufgaben.Interfaces.MotorInterface;
import NebenlaeufigeSysteme.Aufgaben.Interfaces.ObserverInterface;
import NebenlaeufigeSysteme.Aufgaben.Interfaces.SensorInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Controller implements ObserverInterface {
    ExecutorService exS;
    List<SensorInterface> sensors;
    List<MotorInterface> engines;
    Controller(List<SensorInterface> sensors, List<MotorInterface> engines){
        this.sensors = sensors;
        this.engines = engines;
        for(SensorInterface sensor: this.sensors){
            sensor.addObserver(this);
        }

        exS = Executors.newFixedThreadPool(6);
        List<Runnable> funcs = new ArrayList<>();

        for (SensorInterface sens: sensors){
            Runnable runableTask = () -> {
                sens.start();
            };
            funcs.add(runableTask);
        }
        for(MotorInterface engine: engines){
            Runnable runableTask = () ->{
                engine.start();
            };
            funcs.add(runableTask);
        }
        for(Runnable x : funcs){
            exS.execute(x);
        }



    }

    //Der Controller übernimmt die Ansteuerung der Motoren. Hier entscheidet er dass, wenn der Wert des
    //Sensors über 0 liegt, der Motor angesprochen wird.
    @Override
    public void update(String source, int value) {
        if(value > 0){
            for(MotorInterface engine: this.engines){
                engine.lenken(value);
                engine.fahren(value);
            }
        }
    }
    public void endComponents(){
        try {
            if (!exS.awaitTermination(1000, TimeUnit.MILLISECONDS)) {
                exS.shutdownNow();
                System.out.println("executor shuteddown");
            }
        } catch (InterruptedException e) {
            exS.shutdownNow();
            System.out.println("Executor Failure");
        }
        System.out.println("Exit");
        System.exit(0);
    }
}
