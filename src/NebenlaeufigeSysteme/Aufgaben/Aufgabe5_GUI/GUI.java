package NebenlaeufigeSysteme.Aufgaben.Aufgabe5_GUI;

import NebenlaeufigeSysteme.Aufgaben.Interfaces.MotorInterface;
import NebenlaeufigeSysteme.Aufgaben.Interfaces.SensorInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class GUI extends JFrame{
    static boolean printDebugging = true;
    static Controller controller;
    static ExecutorService exS = Executors.newFixedThreadPool(6);
    JTextArea ta1;
    JTextArea ta2;
    JTextArea ta3;
    JTextArea ta4;

    static List<SensorInterface> sensorList;
    static List<MotorInterface> engineList;

    static JLabel motorSpeed;
    static JLabel steering;

    static Sensor sensor_fl;
    static Sensor sensor_fr;
    static Sensor sensor_bl;
    static Sensor sensor_br;

    public GUI() {
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
        panel1.setLayout(new GridLayout(5,1));

        JPanel sliderPanel1 = new JPanel();
            sliderPanel1.setLayout(new GridLayout(1,2));
            JLabel l1_fl = new JLabel("Sensor 1 (fl)");
            ta1 = new JTextArea("0");
            sliderPanel1.add(l1_fl);
            sliderPanel1.add(ta1);
        panel1.add(sliderPanel1);

        JPanel sliderPanel2 = new JPanel();
            sliderPanel2.setLayout(new GridLayout(1,2));
            JLabel l2_fr = new JLabel("Sensor 1 (fr)");
            ta2 = new JTextArea("0");
            sliderPanel2.add(l2_fr);
            sliderPanel2.add(ta2);
        panel1.add(sliderPanel2);

        JPanel sliderPanel3 = new JPanel();
            sliderPanel3.setLayout(new GridLayout(1,2));
            JLabel l3_bl = new JLabel("Sensor 3 (bl)");
            ta3 = new JTextArea("0");
            sliderPanel3.add(l3_bl);
            sliderPanel3.add(ta3);
        panel1.add(sliderPanel3);

        JPanel sliderPanel4 = new JPanel();
            sliderPanel4.setLayout(new GridLayout(1,2));
            JLabel l4_br = new JLabel("Sensor 4 (br)");
            ta4 = new JTextArea("0");
            sliderPanel4.add(l4_br);
            sliderPanel4.add(ta4);
        panel1.add(sliderPanel4);

        Button submitButtton = new Button("Submit");
        panel1.add(submitButtton);

        main_panel.add(panel1);

        JPanel panel2 = new JPanel();
        panel2.setBorder(BorderFactory.createLineBorder(Color.black));
        main_panel.add(panel2);

        JPanel panel3 = new JPanel();
        panel3.setBorder(BorderFactory.createLineBorder(Color.black));
        panel3.setLayout(new GridLayout(2, 2));
        JLabel speedLabel = new JLabel("Fahrgeschwindigkeit");
        JLabel steeringLabel = new JLabel("<html><body><p>Motor eingeschlagen</p> <p>(default: 128)</p></bodŷ></html>");
        motorSpeed = new JLabel("0");
        steering = new JLabel("0");
        panel3.add(speedLabel);
        panel3.add(motorSpeed);
        panel3.add(steeringLabel);
        panel3.add(steering);

        main_panel.add(panel3);

        submitButtton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submitValues();
            }
        });
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                close();
            }
        });
        this.setVisible(true);
    }

    public static void main(String[] args) {
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
        Engine engine_controll = new Engine("control");
        engineList = new ArrayList<>();
        engineList.add(engine_drive);
        engineList.add(engine_controll);

        print("now Controller start");
        controller = new Controller(sensorList, engineList);

        Runnable runCont = () -> {
            controller.start();
        };
        exS.execute(runCont);

        Runnable runEngineUpdate = () -> {
            enginesValueUpdate();
        };
        exS.execute(runEngineUpdate);

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                GUI gui = new GUI();
                gui.setVisible(true);
            }
        });
    }

    private static void enginesValueUpdate(){
        while (true){
            for(MotorInterface x : engineList){
                if(x.getID() == "drive"){
                    motorSpeed.setText(Integer.toString(x.getSpeed()));
                }
                if(x.getID() == "controll"){
                    steering.setText(Integer.toString(x.getSteering()));
                }
            }
        }

    }

    private void submitValues(){
        sensor_fl.changeValue(Integer.parseInt(ta1.getText()));
        sensor_fr.measurement = Integer.parseInt(ta2.getText());
        sensor_bl.measurement = Integer.parseInt(ta3.getText());
        sensor_br.measurement = Integer.parseInt(ta4.getText());
    }

    //Close and Debugging Methods
    private int close(){
        print("closemethod started");
        this.controller.controller_shutdown();
        endComponents();
        this.setVisible(false);
        this.dispose();
        return 0;
    }

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

    private static void print(String debuggingString){
        if (printDebugging == true){
            System.out.println(debuggingString);
        }
    }



}
