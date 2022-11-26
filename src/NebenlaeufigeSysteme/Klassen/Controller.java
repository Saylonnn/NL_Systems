package NebenlaeufigeSysteme.Klassen;

import NebenlaeufigeSysteme.Interfaces.EngineInterface;
import NebenlaeufigeSysteme.Interfaces.ObserverInterface;
import NebenlaeufigeSysteme.Interfaces.SensorInterface;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Controller implements ObserverInterface {
    // --------------------------------- Glob Values -------------------------------------------------
    //SensorValues
    int fl = 0, fr = 0, bl = 0, br = 0;
    ExecutorService exS;


    // ---------------------------- init depending on params -----------------------------------------
    public void init(String SensorClass, String EngineClass){
        exS = Executors.newFixedThreadPool(10);
        if (SensorClass.equals("gui") && EngineClass.equals("gui")){
            Gui gui = new Gui();
            gui.addObserver(this);
            SensorInterface sensor_fl = gui;
            SensorInterface sensor_fr = gui;
            SensorInterface sensor_bl =  gui;
            SensorInterface sensor_br = gui;
            EngineInterface engine = gui;
            exS.submit((Callable<String>) () ->{
                gui.init(this);
                gui.start();
                return null;
            } );

        }
        if (SensorClass.equals("SilTest") && EngineClass.equals("SilTest")){

        }
    }
    //-------------------------------- working Methodes ---------------------------------------------
    private void decideMovement(){

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
