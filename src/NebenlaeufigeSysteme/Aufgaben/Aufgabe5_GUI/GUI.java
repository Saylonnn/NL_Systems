package NebenlaeufigeSysteme.Aufgaben.Aufgabe5_GUI;

import NebenlaeufigeSysteme.Aufgaben.Interfaces.MotorInterface;
import NebenlaeufigeSysteme.Aufgaben.Interfaces.SensorInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.Format;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class GUI extends JFrame{
    //Booleans for debugging etc.
    static boolean working = true;
    static boolean printDebugging = false;

    //Object init
    static Controller controller;
    static ExecutorService exS = Executors.newFixedThreadPool(6);
    static List<SensorInterface> sensorList;
    static List<MotorInterface> engineList;
    static Sensor sensor_fl;
    static Sensor sensor_fr;
    static Sensor sensor_bl;
    static Sensor sensor_br;

    //GUI Objects
    JSpinner ta1;
    JSpinner ta2;
    JSpinner ta3;
    JSpinner ta4;
    static JLabel motorSpeed;
    static JLabel steering;
    static JLabel c_fl_label;
    static JLabel c_fr_label;
    static JLabel c_bl_label;
    static JLabel c_br_label;

    static JLabel directioLabel;

    //Constructor
    public GUI() {
        print("GUI() aufgerufen");
        this.setTitle("Car Controller");
        this.setSize(900, 540);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel main_panel = new JPanel();
        main_panel.setBackground(Color.DARK_GRAY);
        main_panel.setLayout(new GridLayout(1, 3));
        this.add(main_panel);

        JPanel panel1 = new JPanel();
        panel1.setBorder(BorderFactory.createLineBorder(Color.black));
        panel1.setLayout(new GridLayout(6,1));

        Format amountFormat = NumberFormat.getNumberInstance();
        JPanel sliderPanel1 = new JPanel();
            sliderPanel1.setLayout(new GridLayout(1,2));
            JLabel l1_fl = new JLabel("Sensor 1 (fl)");
            ta1 = new JSpinner();
            sliderPanel1.add(l1_fl);
            sliderPanel1.add(ta1);
        panel1.add(sliderPanel1);

        JPanel sliderPanel2 = new JPanel();
            sliderPanel2.setLayout(new GridLayout(1,2));
            JLabel l2_fr = new JLabel("Sensor 1 (fr)");
            ta2 = new JSpinner();
            sliderPanel2.add(l2_fr);
            sliderPanel2.add(ta2);
        panel1.add(sliderPanel2);

        JPanel sliderPanel3 = new JPanel();
            sliderPanel3.setLayout(new GridLayout(1,2));
            JLabel l3_bl = new JLabel("Sensor 3 (bl)");
            ta3 = new JSpinner();
            sliderPanel3.add(l3_bl);
            sliderPanel3.add(ta3);
        panel1.add(sliderPanel3);

        JPanel sliderPanel4 = new JPanel();
            sliderPanel4.setLayout(new GridLayout(1,2));
            JLabel l4_br = new JLabel("Sensor 4 (br)");
            ta4 = new JSpinner();
            sliderPanel4.add(l4_br);
            sliderPanel4.add(ta4);
        panel1.add(sliderPanel4);

        Button submitButtton = new Button("Submit");
        panel1.add(submitButtton);
        Button switchDirectionButton = new Button("switch driving Direction");
        panel1.add(switchDirectionButton);

        main_panel.add(panel1);

        JPanel panel2 = new JPanel();
        panel2.setBorder(BorderFactory.createLineBorder(Color.black));
        panel2.setLayout(new GridLayout(3,2));
        panel2.add(c_fl_label);
        panel2.add(c_fr_label);
        panel2.add(c_bl_label);
        panel2.add(c_br_label);
        directioLabel = new JLabel("Direction: Forward");
        panel2.add(directioLabel);

        main_panel.add(panel2);

        JPanel panel3 = new JPanel();
        panel3.setBorder(BorderFactory.createLineBorder(Color.black));
        panel3.setLayout(new GridLayout(2, 2));
        JLabel speedLabel = new JLabel("Fahrgeschwindigkeit");
        JLabel steeringLabel = new JLabel("<html><body><p>Motor eingeschlagen</p> <p>(default: 0°)</p></bodŷ></html>");

        panel3.add(speedLabel);
        panel3.add(motorSpeed);
        panel3.add(steeringLabel);
        panel3.add(steering);

        main_panel.add(panel3);

        switchDirectionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (directioLabel.getText().equals("Direction: Forward")){
                    directioLabel.setText("Direction: Backward");
                    controller.setDrivingDirection("bw");
                } else {
                    directioLabel.setText("Direction: Forward");
                    controller.setDrivingDirection("fw");
                }
                submitValues();
            }
        });
        submitButtton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                print("submitted");
                submitValues();
            }
        });
        //extend closemethod so Threads shutdown
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                close();
            }
        });
        //launche Window
        this.pack();
        this.setVisible(true);
    }

    public static void main(String[] args) {
        print("main wird ausgeführt");
        //init Objects here, not in consturctor because of the threading, Constructor is called after the main method
        motorSpeed = new JLabel("0");
        steering = new JLabel("0");
        c_fl_label = new JLabel("---");
        c_fr_label = new JLabel("---");
        c_bl_label = new JLabel("---");
        c_br_label = new JLabel("---");
        //Controller erstellen
        sensor_fl = new Sensor("fl");
        sensor_fr = new Sensor("fr");
        sensor_bl = new Sensor("bl");
        sensor_br = new Sensor("br");
        sensorList = new ArrayList<>();
        sensorList.add(sensor_fl);
        sensorList.add(sensor_fr);
        sensorList.add(sensor_bl);
        sensorList.add(sensor_br);

        Engine engine_drive = new Engine("drive");
        Engine engine_controll = new Engine("controll");
        engineList = new ArrayList<>();
        engineList.add(engine_drive);
        engineList.add(engine_controll);

        print("now Controller start");
        controller = new Controller(sensorList, engineList);

        //Launche in extra Threads
        Runnable runCont = () -> {
            controller.start();
        };
        exS.execute(runCont);

        Runnable runEngineUpdate = () -> {
            enginesValueUpdate();
        };
        exS.execute(runEngineUpdate);

        Runnable runContUp = () ->{
            updateControllerValues();
        };
        exS.execute(runContUp);

        //Launche GUI
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                GUI gui = new GUI();
                gui.setVisible(true);
            }
        });
    }

    //collect Engine Sensors and display it
    private static void enginesValueUpdate(){
        while (working){
            for(MotorInterface x : engineList){
                if(x.getID() == "drive"){
                    motorSpeed.setText(Integer.toString(x.getSpeed()));
                }
                if(x.getID() == "controll"){
                    steering.setText(Integer.toString(x.getSteering()));
                }
            }
            try{
                Thread.sleep(100);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }

    }

    //send Values to Sensor
    private void submitValues(){
        //filter empty user input
        if(ta1.getValue().toString() != "") {
            sensor_fl.changeValue(Integer.parseInt(ta1.getValue().toString()));
            print("value fl transmitted "+ ta1.getValue().toString());
        }
        if(ta2.getValue().toString() != "") {
            sensor_fr.changeValue(Integer.parseInt(ta2.getValue().toString()));
        }
        if(ta3.getValue().toString() != "") {
            sensor_bl.changeValue(Integer.parseInt(ta3.getValue().toString()));
        }
        if(ta4.getValue().toString() != "") {
            sensor_br.changeValue(Integer.parseInt(ta4.getValue().toString()));
        }
    }


    //Show Middle Panle (JPanel 2) Controller Values
    private static void updateControllerValues(){
        while(working) {
            int[] x = controller.getSensorInt();
            c_fl_label.setText("Front-Left: " + Integer.toString(x[0]));
            c_fr_label.setText("Front-right: " + Integer.toString(x[1]));
            c_bl_label.setText("Back-Left: " + Integer.toString(x[2]));
            c_br_label.setText("Back-right: " + Integer.toString(x[3]));
            try{
                Thread.sleep(100);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }

    }

    //Close and Debugging Methods
    private int close(){
        working = false;
        print("closemethod started");
        this.controller.controller_shutdown();
        endComponents();
        this.setVisible(false);
        this.dispose();
        return 0;
    }


    //Threads beenden
    public void endComponents(){
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

    //um das debugging zu erleichtern
    private static void print(String debuggingString){
        if (printDebugging == true){
            System.out.println("[GUI] " + debuggingString);
        }
    }



}
