package NebenlaeufigeSysteme.KlassenOld;

import NebenlaeufigeSysteme.Interfaces.ObserverInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class SilTest implements ObserverInterface {
    static boolean first_update = true;
    static Sensor sensor = new Sensor("main");

    static int sensor_update_value;

    public static void main(String[] args){

        ExecutorService executorService = Executors.newFixedThreadPool(1);
        List<Callable<String>> callableParts = new ArrayList<>();
        Callable<String> callableSensor = () -> {sensor.start(); return null;};
        Future<String> future1 = executorService.submit(callableSensor);
        try{
            List<Future<String>> result = executorService.invokeAll(callableParts);
        }catch(InterruptedException e){
            e.printStackTrace();
        }


        //80 als Maximal wert wird immer zurück gegeben wenn das Auto nichts vor sich hat
        //Testreihe: Geradeaus von Wand wegfahren
        //soll wenn die Wand weiter als 80 entfernt ist nur noch 80 zurück geben
        int[] testReihe1 = {20,20,80,20,30,40,50,60,70,80,90,25,10,255,255,255,255,255};
        int[] sollReihe1 = {20,20,20,20,30,40,50,60,70,80,80,80,80,80,80,80,80,80};
        testReiheGleich(sollReihe1, testReihe1);

        //Testreihe: Geradeaus nah an wand heran fahren
        int[] testReihe2 = {20,15,10,5,255,255,255,255,255,255,255,255,255};
        int[] sollReihe2 = {20,15,10,5,0,0,0,0,0,0,0,0,0};
        testReiheGleich(sollReihe2, testReihe2);

        //Testreihe: Kurve fahren - wand gerät außer sicht
        int[] testReihe3 = {20,15,10,70,90,100,160,255,255,255,255};
        int[] sollReihe3 = {20,15,10,10,10,80,80,80,80,80,80};
        testReiheGleich(sollReihe3, testReihe3);




        //test der funktionalität des Testskipts
        int[] prüftestreihe2 = {20,30,40,45,50};
        int[] prüfsollreihe2 = {20,30,40,45,50};
        //testReiheGleich(prüfsollreihe2, prüftestreihe2);


        endComponents(executorService);
    }

    public static void testReiheGleich(int[] sollReihe, int[] testReihe){
        int errorCounter = 0;
        sensor.first_value = true;
        if(testReihe.length == sollReihe.length){
            for(int i = 0; i < testReihe.length; i++){
                sensor.changeValue(testReihe[i]);
                try{
                    //ein kleines bisschen mehr als die Abtastzeit des Sensors
                    Thread.sleep(110);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                if(sensor.lastTransmittedValue != sollReihe[i]){
                    System.out.println("Fehlertoleranz nicht ausreichend: Ist: "+ Integer.toString(sensor.lastTransmittedValue)+ " soll: "+ Integer.toString(sollReihe[i]));
                    errorCounter = errorCounter + 1;
                }

            }
            System.out.println("testReihe abgeschlossen mit "+ errorCounter + " fehlern, von " + testReihe.length + " Tests.");
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
        sensor_update_value = value;
        System.out.println("sensor_update_value updated0");
    }

    public static void endComponents(ExecutorService exS){
        try {
            if (!exS.awaitTermination(1000, TimeUnit.MILLISECONDS)) {
                exS.shutdownNow();
                System.out.println("executor shuteddown");
            }
        } catch (InterruptedException e) {
            exS.shutdownNow();
            System.out.println("Executor Failure");
        }
        System.out.println("Exit");
        System.exit(0);
    }
}
