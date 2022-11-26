package NebenlaeufigeSysteme.Classes;

import NebenlaeufigeSysteme.Interfaces.EngineInterface;
import NebenlaeufigeSysteme.Interfaces.ObserverInterface;
import NebenlaeufigeSysteme.Interfaces.SensorInterface;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Controller implements ObserverInterface {
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
            gui.addObserver(this);
            SensorInterface sensor_fl = gui;
            SensorInterface sensor_fr = gui;
            SensorInterface sensor_bl =  gui;
            SensorInterface sensor_br = gui;
            //Add Controller as Observer (only on 1 Server, otherwise we get 4 update calls)
            EngineInterface engine = gui;
            exS.submit((Callable<String>) () ->{
                //gui.init();
                //gui.start();
                return null;
            } );
        }

        // ------------------------------- only SilTest -----------------------------------------------------
        if (SensorClass.equals("SilTest") && EngineClass.equals("SilTest")){
            //SilTest Impl.
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
