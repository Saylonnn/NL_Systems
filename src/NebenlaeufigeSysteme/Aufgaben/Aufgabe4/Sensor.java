package NebenlaeufigeSysteme.Aufgaben.Aufgabe4;

import NebenlaeufigeSysteme.Aufgaben.Interfaces.ObserverInterface;
import NebenlaeufigeSysteme.Aufgaben.Interfaces.SensorInterface;

import java.util.ArrayList;
import java.util.List;

public class Sensor implements SensorInterface {
    int lastTransmittedValue = 0;
    int messwert = 0;
    int letzterWert = 0;
    int vorletzterWert = 0;
    int vorvorletzterWert = 0;


    private boolean on = true;
    private String id;
    private List<ObserverInterface> observers = new ArrayList<>();

    //Constructor
    Sensor(String source){
        this.id = source;
    }

    //Damit ein neuer Messwert simuliert werden kann
    public void changeValue(int newValue){
        this.messwert = newValue;
    }
    @Override
    public void addObserver(ObserverInterface observer) {
        this.observers.add(observer);
    }

    @Override
    public void notifyObservers(int value) {
        for(ObserverInterface observer: this.observers){
            observer.update(this.id, value);
            this.lastTransmittedValue = value;
        }
    }
    //Regelschleife - notify observers, wenn sich der Wert ändert
    @Override
    public void start(){
        while(on){
            //wenn messwert == 255
            //beim fahren
            if(messwert == 255 && lastTransmittedValue < 80){
                //kommt näher
                if(vorletzterWert < letzterWert){
                    //observer nur informieren wenn er nah an der wand ist, dann stehenbleiben
                    if (lastTransmittedValue < 10){
                        notifyObservers(0);
                    }
                }
            }
            // beim Fahren erkennen in hoher entfernung
            if(messwert == 255 && lastTransmittedValue > 80){
                //kommt näher
                if(vorletzterWert < letzterWert){
                    //observer nur informieren wenn er nah an der wand ist, dann stehenbleiben
                    if (lastTransmittedValue < 10){
                        notifyObservers(0);
                    }
                }
            }
            //wenn das auto nicht steht und etwas erkennt
            if(messwert != letzterWert && messwert != 255){
                //auslassen zu hoher Abweichungen
                if (Math.abs(messwert - letzterWert) > 10){

                }
            }
            try{
                Thread.sleep(100);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            //implementierung Fehlertoleranz

        }
    }
}
