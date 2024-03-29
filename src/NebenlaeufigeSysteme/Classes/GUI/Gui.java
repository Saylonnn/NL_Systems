package NebenlaeufigeSysteme.Classes.GUI;

import NebenlaeufigeSysteme.Classes.Classes.Sensor;
import NebenlaeufigeSysteme.Interfaces.EngineInterface;
import NebenlaeufigeSysteme.Interfaces.ObserverInterface;
import NebenlaeufigeSysteme.Interfaces.SensorInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;


public class Gui extends JFrame implements SensorInterface, EngineInterface {
    // ---------------------------------------- GUI Objects and Params --------------------------------
    JSpinner ta1;
    JSpinner ta2;
    JSpinner ta3;
    JSpinner ta4;
    JLabel motorSpeed;
    JLabel steering;

    // ---------------------------------------- SensorInterface Values --------------------------------
    private List<ObserverInterface> observers = new ArrayList<>();

    // ---------------------------------------- Controll params ---------------------------------------

    String sensorID;


    // -------------------------------------- Constructor Build Gui -----------------------------------
    public Gui(){
        super("all");
        // --------------------------------- left gui side --------------------------------------------

        this.setTitle("Car Controller");
        this.setSize(900, 540);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel main_panel = new JPanel();
        main_panel.setBackground(Color.DARK_GRAY);
        main_panel.setLayout(new GridLayout(1, 2));
        this.add(main_panel);

        JPanel panel1 = new JPanel();
        panel1.setBorder(BorderFactory.createLineBorder(Color.black));
        panel1.setLayout(new GridLayout(5,1));

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
        System.out.println("all Panels init");

        Button submitButtton = new Button("Submit");
        panel1.add(submitButtton);

        main_panel.add(panel1);


        // ---------------------------------------- right gui side -------------------------------------------
        JPanel panel2 = new JPanel();
        panel2.setBorder(BorderFactory.createLineBorder(Color.black));
        panel2.setLayout(new GridLayout(2, 2));
        JLabel speedLabel = new JLabel("Fahrgeschwindigkeit");
        JLabel steeringLabel = new JLabel("<html><body><p>Motor eingeschlagen</p> <p>(default: 0°)</p></bodŷ></html>");
        motorSpeed = new JLabel("0");
        steering = new JLabel("0");

        panel2.add(speedLabel);
        panel2.add(motorSpeed);

        panel2.add(steeringLabel);
        panel2.add(steering);
        main_panel.add(panel2);
        submitButtton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                notifyObservers(0);
            }
        });
        run();
    }

    public void run(){
        pack();
        setVisible(true);
        System.out.println("pack and visible");
    }


    @Override
    public void lenken(int percent) {
        System.out.println("steering adjusted " + percent);
        steering.setText(Integer.toString(percent));
    }

    @Override
    public void fahren(int percent) {
        motorSpeed.setText(Integer.toString(percent));
    }

    @Override
    public void addObserver(ObserverInterface observer) {
        observers.add(observer);
    }

    @Override
    public void notifyObservers(int value) {
        for(ObserverInterface x: observers){
            x.update("fl", (Integer) ta1.getValue());
            x.update("fr", (Integer) ta2.getValue());
            x.update("bl", (Integer) ta3.getValue());
            x.update("br", (Integer) ta4.getValue());
        }
    }
}
