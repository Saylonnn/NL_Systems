package NebenläufigeSysteme.Aufgaben;

import java.util.ArrayList;
import java.util.List;

public class Sensor implements SensorInterface{
    private int value = 0;
    private boolean on = true;
    List<ObserverInterface> observers = new ArrayList<>();


    public void changeValue(int newValue){
        this.value = newValue;
    }
    @Override
    public void addObserver(ObserverInterface observer) {
        this.observers.add(observer);
    }

    @Override
    public void notifyObservers(int value) {
        for(ObserverInterface observer: this.observers){
            observer.update(this, value);
        }
    }
    @Override
    public void start(){
        while(on){
            if(this.value > 0){
                notifyObservers(this.value);
            }
        }
    }
}
