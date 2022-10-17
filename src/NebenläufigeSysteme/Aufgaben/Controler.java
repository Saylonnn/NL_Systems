package NebenläufigeSysteme.Aufgaben;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Controler implements ObserverInterface{
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
    public void update(SensorInterface source, int value) {
        if(value > 0){
            for(MotorInterface engine: this.engines){
                engine.lenken(value);
            }
        }
    }
}
