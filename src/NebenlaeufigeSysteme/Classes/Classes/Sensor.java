package NebenlaeufigeSysteme.Classes.Classes;

import NebenlaeufigeSysteme.Interfaces.ObserverInterface;
import NebenlaeufigeSysteme.Interfaces.SensorInterface;

import java.util.ArrayList;
import java.util.List;

public class Sensor extends Thread implements SensorInterface {
    boolean stringDebugging = false;
    private boolean first_value = false;

    // measurement, lastemeasurement, secondlastmesurement, thirdlastmeasurement, lasttransmittedValue, big_abs_count
    private int[] mValues = {0,0,0,0,0,0};



    private String sensorID;
    private boolean on = true;

    private List<ObserverInterface> observers = new ArrayList<>();

    //Damit ein neuer Messwert simuliert werden kann
    public void changeValue(int newValue){
        if(first_value){
            mValues[3] = newValue;
            mValues[2] = newValue;
            mValues[1] = newValue;
            mValues[0] = newValue;
            first_value = false;
            mValues[4] = newValue;
        }
        mValues[3] =  mValues[2];
        mValues[2] =  mValues[1];
        mValues[1] =  mValues[0];
        mValues[0] = newValue;
    }
    @Override
    public void addObserver(ObserverInterface observer) {
        //System.out.println("observer added");
        this.observers.add(observer);
    }

    @Override
    public void notifyObservers(int value) {

        if(mValues[4] != value) {
            System.out.println("sensor called notifyOberservers");
            mValues[4] = value;

            for (ObserverInterface observer : this.observers) {
                observer.update(this.sensorID, value);
                System.out.println("observers notified" + value);
            }
        }

    }

    @Override
    public void setID(String id) {
        sensorID = id;
    }
    //Regelschleife - notify observers, wenn sich der Wert ändert

    @Override
    public void run(){
        int big_abs_count = 0;
        while(on) {
            faultTolerantMeasurement(mValues);
            //damit nicht durchgehend überprüft wird
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public int[] faultTolerantMeasurement(int[] values){
        //everything over 255 cant be send by the sensor -> wrong gui / SilTest input
        if(values[0] < 256){

            //Abstand <= 10 && unter 80
            if (Math.abs(values[0] - values[1]) <= 10 && values[0] <= 80) {
                values[4] = values[0];
                values[5] = 0;

                //sensorPrint("case 1");
            }
            //Abstand < 10 && über 80 aber nicht 255
            if (Math.abs(values[0] - values[1]) <= 10 && values[0] > 80 && values[0] != 255) {
                values[4] = 80;
                values[5] = 0;
                //sensorPrint("case 2");
            }
            //Abstand > 10 --> Höchstwarscheinlich ein Fehler oder eine Kurve (3er Kette prüfen)
            //Nicht 255, wenn counter 2 --> messwert übermitteln
            if (Math.abs(values[0] - values[1]) > 10 && values[0] != 255 && values[5] == 2) {
                values[4] = values[0];
                values[5] = 0;
                //sensorPrint("case 3");
            }
            //Großer Abstand Counter unter 2 -> hochzählen
            if (Math.abs(values[0] - values[1]) > 10 && values[0] != 255 && values[5] < 2) {
                values[5]++;
                //sensorPrint("case 4");
            }
            //Zu nah an der Wand
            if (values[0] == 255 && values[4] <= 10) {
                values[4] = 4;
                values[5] = 0;
                //sensorPrint("case 5");
            }

            // Nächstes Objekt zu weit entfernt
            if (values[0] == 255 && values[3] < values[2] && values[2] < values[1]) {
                values[4] = 80;
                values[5] = 0;
                //sensorPrint("case 6");
            }
        }
        return values;
    }


    public void sensorPrint(String s){
        if (stringDebugging){
            System.out.println("[Sensor] " + s);
        }
    }

}


