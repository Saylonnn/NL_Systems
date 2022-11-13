package NebenlaeufigeSysteme.Aufgaben.Aufgabe4;

import NebenlaeufigeSysteme.Aufgaben.Interfaces.ObserverInterface;
import NebenlaeufigeSysteme.Aufgaben.Interfaces.SensorInterface;

import java.util.ArrayList;
import java.util.List;

public class Sensor implements SensorInterface  {
    boolean stringDebugging = false;
    boolean first_value = true;
    int lastTransmittedValue = 0;
    int measurement = 0;
    int lastmeasurement = 0;
    int secondlastmeasurement = 0;
    int thirdlastmeasurement = 0;


    private boolean on = true;
    private String id;
    private List<ObserverInterface> observers = new ArrayList<>();

    //Constructor
    Sensor(String source){
        this.id = source;
    }

    //Damit ein neuer Messwert simuliert werden kann
    public void changeValue(int newValue){
        if(first_value){
            thirdlastmeasurement = newValue;
            secondlastmeasurement = newValue;
            lastmeasurement = newValue;
            measurement = newValue;
            first_value = false;
            lastTransmittedValue = newValue;
        }
        thirdlastmeasurement = secondlastmeasurement;
        secondlastmeasurement = lastmeasurement;
        lastmeasurement = measurement;
        measurement = newValue;
        sensorPrint("---------------------------------------");
        sensorPrint("Value update:");
        sensorPrint("measurement: "+ measurement);
        sensorPrint("lastmeasurement: "+ lastmeasurement);
        sensorPrint("secondlastmeasurement: "+ secondlastmeasurement);
        sensorPrint("thirdlastmeasurement: "+ thirdlastmeasurement);
        sensorPrint("---------------------------------------");

    }
    @Override
    public void addObserver(ObserverInterface observer) {
        sensorPrint("observer added");
        this.observers.add(observer);
    }

    @Override
    public void notifyObservers(int value) {
        lastTransmittedValue = value;
        /*
        for(ObserverInterface observer: this.observers){
            //observer.update(this.id, value);
        }
         */
    }
    //Regelschleife - notify observers, wenn sich der Wert ändert
    @Override
    public void start(){
        int big_abs_count = 0;
        while(on) {
            //Abstand <= 10 && unter 80
            if (Math.abs(measurement - lastmeasurement) <= 10 && measurement <= 80) {
                notifyObservers(measurement);
                big_abs_count = 0;
                sensorPrint("case 1");
            }
            //Abstand < 10 && über 80 aber nicht 255
            if (Math.abs(measurement - lastmeasurement) <= 10 && measurement > 80 && measurement != 255) {
                notifyObservers(80);
                big_abs_count = 0;
                sensorPrint("case 2");
            }
            //Abstand > 10 --> Höchstwarscheinlich ein Fehler oder eine Kurve (3er Kette prüfen)
            //Nicht 255, wenn counter 2 --> messwert übermitteln
            if (Math.abs(measurement - lastmeasurement) > 10 && measurement != 255 && big_abs_count == 2) {
                ///WTF was macht das?
                notifyObservers(measurement);
                sensorPrint("case 3");
            }
            //Großer Abstand Counter unter 2 -> hochzählen
            if (Math.abs(measurement - lastmeasurement) > 10 && measurement != 255 && big_abs_count < 2) {
                big_abs_count++;
                sensorPrint("case 4");
            }
            //Zu nah an der Wand
            if (measurement == 255 && lastTransmittedValue <= 10) {
                notifyObservers(0);
                sensorPrint("case 5");
            }

            // Nächstes Objekt zu weit entfernt
            if (measurement == 255 && thirdlastmeasurement < secondlastmeasurement && secondlastmeasurement < lastmeasurement) {
                notifyObservers(80);
                sensorPrint("case 6");
            }
            //damit nicht durchgehend überprüft wird
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }
    public void sensorPrint(String s){
        if (stringDebugging){
            System.out.println(s);
        }
    }
}
