package NebenläufigeSysteme.Aufgaben.Klassen;

import NebenläufigeSysteme.Aufgaben.Interfaces.ObserverInterface;
import NebenläufigeSysteme.Aufgaben.Interfaces.SensorInterface;

import java.util.ArrayList;
import java.util.List;

public class Sensor implements SensorInterface {
    private int current_value;
    private int old_value;
    private boolean on = true;
    private String id;
    List<ObserverInterface> observers = new ArrayList<>();

    Sensor(String source){
        this.id = source;
    }

    public void changeValue(int newValue){
        this.current_value = newValue;
    }
    @Override
    public void addObserver(ObserverInterface observer) {
        this.observers.add(observer);
    }

    @Override
    public void notifyObservers(int value) {
        for(ObserverInterface observer: this.observers){
            observer.update(this.id, value);
        }
    }
    @Override
    public void start(){
        this.current_value = 0;
        this.old_value = 0;
        while(on){
            if(this.old_value != this.current_value){
                notifyObservers(this.current_value);
                this.old_value = this.current_value;
            }
        }
    }
}
