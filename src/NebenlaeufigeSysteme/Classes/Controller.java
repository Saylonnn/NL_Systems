package NebenlaeufigeSysteme.Classes;

import NebenlaeufigeSysteme.Interfaces.EngineInterface;
import NebenlaeufigeSysteme.Interfaces.ObserverInterface;
import NebenlaeufigeSysteme.Interfaces.SensorInterface;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Controller implements ObserverInterface {
    boolean working = true;
    // --------------------------------- Glob Values -------------------------------------------------
    // SensorValues and navigation Values
    int fl = 0, fr = 0, bl = 0, br = 0;
    String drivingDirection = "fw";
    ExecutorService exS;
    SensorInterface sensor_fl;
    SensorInterface sensor_fr;
    SensorInterface sensor_bl;
    SensorInterface sensor_br;
    EngineInterface engine;


    // ---------------------------- Constructor depending on params -----------------------------------------
    Controller(String SensorClass, String EngineClass){
        exS = Executors.newFixedThreadPool(10);

        // ----------------------------------- only GUI -----------------------------------------------------
        if (SensorClass.equals("gui") && EngineClass.equals("gui")){

            Gui gui = new Gui();
            exS.submit((Callable<String>) () ->{
                gui.startGUI();
                return null;
            } );
            gui.addObserver(this);
            sensor_fl = gui;
            sensor_fr = gui;
            sensor_bl =  gui;
            sensor_br = gui;
            //Add Controller as Observer (only on 1 Server, otherwise we get 4 update calls)
            engine = gui;
            System.out.println("Objects created");

        }

        // ------------------------------- only SilTest -----------------------------------------------------
        if (SensorClass.equals("SilTest") && EngineClass.equals("SilTest")){
            //SilTest Impl.
        }
    }
    // -------------------------------- control loop -----------------------------------------------
    public void start(){
        while(working){
            try{
                Thread.sleep(100);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }


    //-------------------------------- working Methodes ---------------------------------------------
    // Decide Movement based on Sensor Input Values --> Stored in Global Variables
    private void decideMovement(){
        if (drivingDirection.equals("fw")){
            //Stop infront of a wall
            if ((fl < 10 | fr < 10 )){
                engine.fahren(0);
            }
            //drive if there is no wall
            if (fl > 10 & fr > 10){
                engine.fahren(255);
            }

            //steering
            //steering left if right Object is nearer
            if (fl > fr){
                engine.lenken(-10);
            }
            //steering right if left object is nearer
            if(fl < fr ){
                engine.lenken(10);
            }
            if(fl == fr){
                engine.lenken(0);
            }
        }
        if(drivingDirection.equals("bw")) {
            //Stop infront of a wall
            if ((bl < 10 | br < 10)) {
                engine.fahren(0);
            }
            //drive if there is no wall
            if (bl > 10 & br > 10) {
                engine.fahren(-255);
            }

            //steering
            //steering left if left Object is nearer
            if (bl > br) {
                engine.lenken(-10);
            }
            //steering right if right object is nearer
            if (bl < br) {
                engine.lenken(10);
            }
            if (bl == br) {
                engine.lenken(0);
            }
        }
    }

    //-------------------------------- Override Methodes ---------------------------------------------
    @Override
    public void update(String source, int value) {
        if (source.equals("fl")) {
            fl = value;
            decideMovement();
        }
        if (source.equals("fr")) {
            fr = value;
            decideMovement();
        }

        if (source.equals("bl")) {
            bl = value;
            decideMovement();
        }
        if (source.equals("br")){
            br = value;
            decideMovement();
        }
    }



}
