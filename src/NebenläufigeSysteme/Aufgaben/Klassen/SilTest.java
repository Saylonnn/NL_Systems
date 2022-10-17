package NebenläufigeSysteme.Aufgaben.Klassen;

import NebenläufigeSysteme.Aufgaben.Interfaces.MotorInterface;
import NebenläufigeSysteme.Aufgaben.Interfaces.SensorInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class SilTest{
    ExecutorService executorService;

    public static void main(String[] args) {
        Sensor sensor1 = new Sensor("vorne_1");

        Engine motor1 = new Engine();


        List<SensorInterface> sensors = new ArrayList<>();
        List<MotorInterface> engines = new ArrayList<>();
        sensors.add(sensor1);
        engines.add(motor1);
        Controler controler = new Controler(sensors, engines);


        //start Executorservice, add sensor1, launche
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        List<Callable<String>> callableParts = new ArrayList<>();
        //Sensor1 - runable, add List
        Callable<String> callableSensor1 = () -> {sensor1.start();return null;};
        Future<String> future1 = executorService.submit(callableSensor1);
        //Motor1 - runable, add List
        Callable<String> callableMotor1 = () -> {motor1.start();return null;};
        Future<String> future2 = executorService.submit(callableMotor1);

        try{
            List<Future<String>> result = executorService.invokeAll(callableParts);
        }catch(InterruptedException e){
            e.printStackTrace();
        }

        testGleich(motor1.getEingeschlagen(), 0);
        sensor1.changeValue(5);
        testGleich(motor1.getEingeschlagen(), 0);
        endComponents(executorService);
    }

    public static void testGleich(int erwartungsWert, int istWert){
        if(erwartungsWert==istWert){
            System.out.println("Test erfolgreich");
        }
        else {
           System.out.println("Test fehlgeschlagen");
        }
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
