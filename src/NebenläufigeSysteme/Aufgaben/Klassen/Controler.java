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

    //Der Controller übernimmt die Ansteuerung der Motoren. Hier entscheidet er dass, wenn der Wert des
    //Sensors über 0 liegt, der Motor angesprochen wird.
    @Override
    public void update(String source, int value) {
        if(value > 0){
            for(MotorInterface engine: this.engines){
                engine.lenken(value);
            }
        }
    }
}
