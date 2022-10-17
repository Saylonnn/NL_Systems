package NebenläufigeSysteme.Aufgaben.Klassen;

import NebenläufigeSysteme.Aufgaben.Interfaces.MotorInterface;
import NebenläufigeSysteme.Aufgaben.Interfaces.ObserverInterface;
import NebenläufigeSysteme.Aufgaben.Interfaces.SensorInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Controler implements ObserverInterface {
    List<SensorInterface> sensors;
    List<MotorInterface> engines;
    Controler(List<SensorInterface> sensors, List<MotorInterface> engines){
        this.sensors = sensors;
        this.engines = engines;
        for(SensorInterface sensor: this.sensors){
            sensor.addObserver(this);
        }
    }

    @Override
    public void update(String source, int value) {
        System.out.println(source + " changed Value");
        if(value > 0){
            for(MotorInterface engine: this.engines){
                engine.lenken(value);
            }
        }
    }
}
