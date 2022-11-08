package NebenlaeufigeSysteme.Aufgaben.Klassen;

import NebenlaeufigeSysteme.Aufgaben.Interfaces.MotorInterface;
import NebenlaeufigeSysteme.Aufgaben.Interfaces.ObserverInterface;
import NebenlaeufigeSysteme.Aufgaben.Interfaces.SensorInterface;

import java.util.List;

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
                engine.fahren(value);
            }
        }
    }
}
