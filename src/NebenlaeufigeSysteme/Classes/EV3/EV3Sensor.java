package NebenlaeufigeSysteme.Classes.EV3;

import NebenlaeufigeSysteme.Classes.Classes.Sensor;
import NebenlaeufigeSysteme.Interfaces.ObserverInterface;
import NebenlaeufigeSysteme.Interfaces.SensorInterface;
import lejos.robotics.SampleProvider;
import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.NXTUltrasonicSensor;

import java.util.ArrayList;
import java.util.List;


public class EV3Sensor extends Sensor implements SensorInterface {
    NXTUltrasonicSensor sensor;
    SampleProvider distance;
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
    private String sensorID;


    public EV3Sensor(String id){
        super(id);

    }

    @Override
    public void addObserver(ObserverInterface observer) {
        //System.out.println("observer added");
        this.observers.add(observer);
    }

    // if lasttransmitted Value != current value send to all observers
    @Override
    public void notifyObservers(int value) {
        for (ObserverInterface observer : this.observers) {
            observer.update(this.sensorID, value);
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

    public void run(){
        Thread thread = new Thread(){
            public void run() {
                Port port = SensorPort.S1;
                if (sensorID.equals("FL")) {
                    port = SensorPort.S1;
                }
                if (sensorID.equals("FR")) {
                    port = SensorPort.S2;
                }
                if (sensorID.equals("BL")) {
                    port = SensorPort.S3;
                }
                if (sensorID.equals("BR")) {
                    port = SensorPort.S4;
                }
                int newSensorValue;
                sensor = new NXTUltrasonicSensor(port);
                sensor.getDistanceMode();
                sensor.enable();
                sensor.getMode("Distance");
                while (sensorON) {
                    float[] sample = new float[distance.sampleSize()];
                    distance.fetchSample(sample, 0);
                    newSensorValue = (int) Math.round(sample[0]);

                    changeValue(newSensorValue);
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
