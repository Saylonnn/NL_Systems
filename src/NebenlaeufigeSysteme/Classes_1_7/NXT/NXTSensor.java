package NebenlaeufigeSysteme.Classes_1_7.NXT;

import NebenlaeufigeSysteme.Classes.Classes.Sensor;
import NebenlaeufigeSysteme.Interfaces.ObserverInterface;
import NebenlaeufigeSysteme.Interfaces.SensorInterface;
import lejos.nxt.LCD;
import lejos.nxt.LCDOutputStream;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.objectdetection.Feature;
import lejos.robotics.objectdetection.FeatureDetector;
import lejos.robotics.objectdetection.RangeFeatureDetector;


import java.util.ArrayList;
import java.util.List;


public class NXTSensor extends Sensor {
    private static UltrasonicSensor sonic;

    boolean firstValue = true;
    boolean sensorON = true;
    private List<ObserverInterface> observers = new ArrayList<>();
    /*
    [0] measurement
    [1] lastemeasurement
    [2] secondlastmesurement
    [3] thirdlastmeasurement
    [4] lasttransmittedValue
    [5] big_abs_count
     */
    private int[] mValues = {0,0,0,0,0,0};
    String sensorID;


    public NXTSensor(String id){

        super(id);
        sensorID = id;
    }

    @Override
    public void addObserver(ObserverInterface observer) {
        //System.out.println("observer added");
        this.observers.add(observer);
    }

    // if lasttransmitted Value != current value send to all observers
    @Override
    public void notifyObservers(int value) {
        for(int i = 0; i < observers.size(); i++){
            observers.get(i).update(this.sensorID, value);
        }
    }

    public void changeValue(int newValue){
        if(firstValue){
            mValues[3] = newValue;
            mValues[2] = newValue;
            mValues[1] = newValue;
            mValues[0] = newValue;
            firstValue = false;
            mValues[4] = newValue;
        }
        mValues[3] =  mValues[2];
        mValues[2] =  mValues[1];
        mValues[1] =  mValues[0];
        mValues[0] = newValue;
    }

    @Override
    public void run(){

        Thread thread = new Thread(){
            public void run(){
                SensorPort port = SensorPort.S1;
                if(sensorID.equals("FL")){
                    port = SensorPort.S1;
                }
                if(sensorID.equals("FR")){
                    port = SensorPort.S2;
                }
                if(sensorID.equals("BL")){
                    port = SensorPort.S3;
                }
                if(sensorID.equals("BR")){
                    port = SensorPort.S4;
                }
                LCD.clear();
                LCD.drawString(sensorID + " " + port.toString(), 0, 0);
                sonic = new UltrasonicSensor(port);
                sonic.reset();
                sonic.continuous();
                LCD.drawString(String.valueOf(sonic.getActualMode()), 3,4);
                int value;
                while(sensorON) {

                    value = sonic.getDistance();

                    if (value > 255) {
                        value = 255;
                    } else if (value < 12) {
                        value = 0;
                    }
                    if (sensorID.equals("FL")) {
                        LCD.drawString(String.valueOf(value), 3, 2);
                    }
                    mValues[0] = value;

                    int[] mValues2 = faultTolerantMeasurement(mValues);
                    if (mValues[4] != mValues2[4]) {
                        notifyObservers(mValues2[4]);
                        mValues = mValues2;
                    }
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                }

        };
        thread.start();
    }

    public void shutdownSensor(){
        sensorON = false;
    }


}
