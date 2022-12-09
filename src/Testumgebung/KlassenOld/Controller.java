package Testumgebung.KlassenOld;
/*

import NebenlaeufigeSysteme.Interfaces.EngineInterface;
import NebenlaeufigeSysteme.Interfaces.ObserverInterface;
import NebenlaeufigeSysteme.Interfaces.SensorInterface;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Controller implements ObserverInterface {
    //global sensor values
    int fl = 0;
    int fr = 0;
    int bl = 0;
    int br = 0;

    String drivingDirection = "fw";

    //debugging and working booleans
    static boolean printDebugging = false;
    boolean on = true;

    //Object Init
    static ExecutorService exS;
    static List<SensorInterface> sensors;
    static List<EngineInterface> engines;

    //Constructor
    Controller(List<SensorInterface> sensors, List<EngineInterface> engines) {
        //Create Sensors and Engines -> start them in extra Thread
        this.sensors = sensors;
        this.engines = engines;

        for (SensorInterface sensor : this.sensors) {
            sensor.addObserver(this);
        }
    }

    public static void main(String[] args) {
        exS = Executors.newFixedThreadPool(6);

        if(args[0].equals("gui") && args[1].equals("gui")){

        }


        for (SensorInterface sens: sensors){
            exS.submit((Callable<String>) () -> {
                sens.start();
                return null;
            });
        }
        for(EngineInterface engine: engines){
            exS.submit((Callable<String>) () -> {
                    engine.start();
                    return null;
            });
        }
    }

    //Keep Thread alive function
    public void start(){
        print("contgroller started");
        while(on){

            try{
                Thread.sleep(100);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    //Update function, set global values fl, fr, bl, br -> GUI can grab it with func: getSensorInt();
    @Override
    public void update(String source, int value) {
        System.out.println("observer updated by " + source);
        if (source.equals("fl")) {
            fl = value;
            System.out.println("fl = " + Integer.toString(fl));
            decideMovement(fl, fr, bl, br);
        }
        if (source.equals("fr")) {
            fr = value;
            decideMovement(fl, fr, bl, br);
        }

        if (source.equals("bl")) {
            bl = value;
            decideMovement(fl, fr, bl, br);
        }
        if (source.equals("br")){
            br = value;
            decideMovement(fl, fr, bl, br);
        }
    }

    //Make the decision how to steer and how to drive
    private void decideMovement(int fl, int fr, int bl, int br){
        EngineInterface steeringEngine = engines.get(1);
        EngineInterface driveEngine = engines.get(0);


        Implemented Rules:
           Halte an wenn einer der forderen Sensoren unter 10 cm zur Wand hat
           ansonsten fahre vollgas
           lenke links wenn der rechte front sensor näher an der wand ist als der linke
           lenke rechts wenn der linke front sensor näher an der wand ist als der rechte
           fahr gerade aus wenn beide front sensoren das gleiche angeben (bestenfalls 80 als keine Wand voraus)


        //Driving Forward
        if (drivingDirection.equals("fw")){
            //Stop infront of a wall
            if ((fl < 10 | fr < 10 )){
                print("case 1");
                driveEngine.fahren(0);
            }
            //drive if there is no wall
            if (fl > 10 & fr > 10){
                print("case 2");
                driveEngine.fahren(255);
            }

            //steering
            //steering left if right Object is nearer
            if (fl > fr){
                steeringEngine.lenken(-10);
                print("Steering 1");
            }
            //steering right if left object is nearer
            if(fl < fr ){
                steeringEngine.lenken(10);
                print("Steering 2");
            }
            if(fl == fr){
                steeringEngine.lenken(0);
                print("Steering 3");
            }
        }
        if(drivingDirection.equals("bw")){
            //Stop infront of a wall
            if ((bl < 10 | br < 10 )){
                print("case b1");
                driveEngine.fahren(0);
            }
            //drive if there is no wall
            if (bl > 10 & br > 10){
                print("case b2");
                driveEngine.fahren(-255);
            }

            //steering
            //steering left if left Object is nearer
            if (bl > br){
                steeringEngine.lenken(-10);
                print("Steering 1");
            }
            //steering right if right object is nearer
            if(bl < br ){
                steeringEngine.lenken(10);
                print("Steering 2");
            }
            if(bl == br){
                steeringEngine.lenken(0);
                print("Steering 3");
            }
        }



    }



    public void controller_shutdown(){
        this.on = false;
        endComponents();
        System.out.println("Finally Closed");
    }

    //shutdown Threads correctly
    public void endComponents(){
        try {
            if (!exS.awaitTermination(1000, TimeUnit.MILLISECONDS)) {
                exS.shutdownNow();
                System.out.println("executor shutdown");
            }
        } catch (InterruptedException e) {
            exS.shutdownNow();
            System.out.println("Executor Failure");
        }
        System.out.println("Exit");
        System.exit(0);
    }

    //debugging
    public static void print(String debuggingString){
        if (printDebugging){
            System.out.println("[Controller] "+ debuggingString);
        }
    }

    //Gui can easy track values inside the controller - before decisionmaking
    public int[] getSensorInt(){
        return new int[]{fl,fr,bl,br};
    }

    public void setDrivingDirection(String s){
        drivingDirection = s;
        decideMovement(fl,fr,bl,br);
    }

}
*/