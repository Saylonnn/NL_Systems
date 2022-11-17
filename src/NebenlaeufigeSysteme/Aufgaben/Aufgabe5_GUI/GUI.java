package NebenlaeufigeSysteme.Aufgaben.Aufgabe5_GUI;

import NebenlaeufigeSysteme.Aufgaben.Interfaces.MotorInterface;
import NebenlaeufigeSysteme.Aufgaben.Interfaces.SensorInterface;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class GUI extends JFrame{
    static boolean printDebugging = true;
    static Controller controller;
    static ExecutorService exS;
    JLabel l1;
    JLabel l2;
    JLabel l3;
    JLabel l4;
    public GUI() {
        exS = Executors.newFixedThreadPool(6);
        this.setTitle("Car Controller");
        this.setSize(900, 540);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(close());


        JPanel main_panel = new JPanel();
        main_panel.setBackground(Color.DARK_GRAY);
        main_panel.setLayout(new GridLayout(1, 3));
        this.add(main_panel);


        JPanel panel1 = new JPanel();
        panel1.setBorder(BorderFactory.createLineBorder(Color.black));
        panel1.setLayout(new GridLayout(5,1));


        JPanel sliderPanel1 = new JPanel();
            sliderPanel1.setLayout(new GridLayout(1,2));
            l1 = new JLabel("Sensor 1 (fl)");
            JTextArea ta1 = new JTextArea("0");
            sliderPanel1.add(l1);
            sliderPanel1.add(ta1);
        panel1.add(sliderPanel1);

        JPanel sliderPanel2 = new JPanel();
            sliderPanel2.setLayout(new GridLayout(1,2));
            l2 = new JLabel("Sensor 1 (fr)");
            JTextArea ta2 = new JTextArea("0");
            sliderPanel2.add(l2);
            sliderPanel2.add(ta2);
        panel1.add(sliderPanel2);

        JPanel sliderPanel3 = new JPanel();
            sliderPanel3.setLayout(new GridLayout(1,2));
            l3 = new JLabel("Sensor 3 (bl)");
            JTextArea ta3 = new JTextArea("0");
            sliderPanel3.add(l3);
            sliderPanel3.add(ta3);
        panel1.add(sliderPanel3);

        JPanel sliderPanel4 = new JPanel();
            sliderPanel4.setLayout(new GridLayout(1,2));
            l4 = new JLabel("Sensor 4 (br)");
            JTextArea ta4 = new JTextArea("0");
            sliderPanel4.add(l4);
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
        main_panel.add(panel3);

        this.setVisible(true);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                //Controller erstellen
                Sensor sensor_fl = new Sensor("fl");
                Sensor sensor_fr = new Sensor("fr");
                Sensor sensor_bl = new Sensor("bl");
                Sensor sensor_br = new Sensor("br");
                List<SensorInterface> sensorList = new ArrayList<>();
                sensorList.add(sensor_fl);
                sensorList.add(sensor_fr);
                sensorList.add(sensor_bl);
                sensorList.add(sensor_br);


                Engine engine_drive = new Engine("drive");
                Engine engine_controll = new Engine("control");
                List<MotorInterface> engineList = new ArrayList<>();
                engineList.add(engine_drive);
                engineList.add(engine_controll);

                print("now Controller start");

                exS.execute(runCont(sensorList, engineList));
                print("jetzt sollte die GUI laden");

                GUI gui = new GUI();
                gui.setVisible(true);
            }


        });
    }

    private int close(){
        this.controller.controller_shutdown();
        this.setVisible(false);
        this.dispose();
        return 0;
    }

    private static void print(String debuggingString){
        if (printDebugging == true){
            System.out.println(debuggingString);
        }
    }
    private static Runnable runCont(List<SensorInterface> x, List<MotorInterface> y) {
        print("runnable started");
        controller = new Controller(x, y);
        print("controller init");
        controller.start();
        return null;
    };
}
