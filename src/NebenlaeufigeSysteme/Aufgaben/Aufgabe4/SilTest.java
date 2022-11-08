package NebenlaeufigeSysteme.Aufgaben.Aufgabe4;

import NebenlaeufigeSysteme.Aufgaben.Interfaces.ObserverInterface;

public class SilTest implements ObserverInterface {
    Sensor sensor = new Sensor("main");
    int sensor_update_value = 0;
    SilTest() {
        sensor.addObserver(this);
    }
    public static void main(String[] args){
        int[] testReihe1 = {20,20,80,20,30,40,50,60,55,45,90,25,10,255,255,255,255,255};
        int[] sollReihe1 = {20,20,20,20,30,40,50,60,55,45,45,25,10,0,0,0,0,0};

    }

    public void testReiheGleich(int[] sollReihe, int[] testReihe){
        int errorCounter = 0;
        if(testReihe.length == sollReihe.length){
            for(int i = 0; i < testReihe.length; i++){
                this.sensor.changeValue(testReihe[i]);
                try{
                    Thread.sleep(100);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                if(this.sensor_update_value != sollReihe[i]){
                    System.out.println("Fehlertoleranz nicht ausreichend: Ist: "+ Integer.toString(this.sensor_update_value)+ " soll: "+ Integer.toString(sollReihe[i]));
                    errorCounter = errorCounter + 1;
                }

            }
            System.out.println("testReihe abgeschlossen mit "+ errorCounter + " fehlern.");
        }
        else{
            System.out.println("Reihen nicht gleich lang");
        }
    }
    public static void testGleich(int erwartungsWert, int istWert){
        if(erwartungsWert==istWert){
            System.out.println("Test erfolgreich");
        }
        else {
            System.out.println("Test fehlgeschlagen");
        }
    }

    @Override
    public void update(String source, int value) {

    }
}
