package NebenlaeufigeSysteme.Classes.Classes;


import NebenlaeufigeSysteme.Interfaces.EngineInterface;
import NebenlaeufigeSysteme.Interfaces.ObserverInterface;
import NebenlaeufigeSysteme.Interfaces.SensorInterface;

public class    Controller extends Thread implements ObserverInterface {
    boolean working = true;
    // --------------------------------- Glob Values -------------------------------------------------
    // SensorValues and navigation Values
    int fl = 0, fr = 0, bl = 0, br = 0;
    String drivingDirection = "fw";
    SensorInterface sensor_fl;
    SensorInterface sensor_fr;
    SensorInterface sensor_bl;
    SensorInterface sensor_br;
    EngineInterface engine;


    // ---------------------------- Constructor depending on params -----------------------------------------
    public Controller(boolean isGUI, SensorInterface sensor1, SensorInterface sensor2, SensorInterface sensor3, SensorInterface sensor4, EngineInterface engine){
        sensor_fl = sensor1;
        sensor_fr = sensor2;
        sensor_bl = sensor3;
        sensor_br = sensor4;
        this.engine = engine;
        if (isGUI){
            sensor1.addObserver(this);
        }else{
            sensor_fl.addObserver(this);
            sensor_fr.addObserver(this);
            sensor_bl.addObserver(this);
            sensor_br.addObserver(this);
        }
        this.start();

    }

    // -------------------------------- control loop -----------------------------------------------
    @Override
    public void run(){
        engine.fahren(500);
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

        if(fl < 10 || fr < 10){
            engine.fahren(0);

        }
        /*
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
        */
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

    public void shutdown(){
        this.working = false;
    }

}
