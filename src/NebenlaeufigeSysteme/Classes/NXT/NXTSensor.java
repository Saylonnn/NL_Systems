package NebenlaeufigeSysteme.Classes.NXT;

import NebenlaeufigeSysteme.Classes.Classes.Sensor;
import NebenlaeufigeSysteme.Interfaces.ObserverInterface;
import NebenlaeufigeSysteme.Interfaces.SensorInterface;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class NXTSensor extends Sensor implements SensorInterface {
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
    ExecutorService exS = Executors.newFixedThreadPool(1);

    public NXTSensor(String id){
        sensorID = id;
        exS.submit((Callable<String>) () -> {
            start();
            return null;
        });
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

    public void start(){
        SensorPort port = SensorPort.S1;
        if(this.sensorID.equals("FL")){
            port = SensorPort.S1;
        }
        if(this.sensorID.equals("FR")){
            port = SensorPort.S2;
        }
        if(this.sensorID.equals("BL")){
            port = SensorPort.S3;
        }
        if(this.sensorID.equals("BR")){
            port = SensorPort.S4;
        }
        UltrasonicSensor sensor = new UltrasonicSensor(port);
        sensor.capture();
        while(sensorON){
            int distance = sensor.getDistance();
            changeValue(distance);
            int[] mValues2 = super.faultTolerantMeasurement(mValues);
            if (mValues[4] != mValues2[4]){
                notifyObservers(mValues2[4]);
                mValues = mValues2;
            }
            try{
                Thread.sleep(100);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        endComponents(exS);
    }

    public void shutdownSensor(){
        sensorON = false;
    }
    public static void endComponents(ExecutorService exS) {
        try {
            if (!exS.awaitTermination(1000, TimeUnit.MILLISECONDS)) {
                exS.shutdownNow();
                System.out.println("executor shuteddown");
            }
        } catch (InterruptedException e) {
            exS.shutdownNow();
            System.out.println("Executor Failure");
        }
    }

}
