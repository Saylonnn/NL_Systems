package NebenlaeufigeSysteme.Classes;

import NebenlaeufigeSysteme.Interfaces.EngineInterface;
import NebenlaeufigeSysteme.Interfaces.ObserverInterface;
import NebenlaeufigeSysteme.Interfaces.SensorInterface;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Gui extends Sensor implements SensorInterface, EngineInterface {
    // ---------------------------------------- GUI Objects and Params --------------------------------
    JFrame jFrame;
    JSpinner ta1;
    JSpinner ta2;
    JSpinner ta3;
    JSpinner ta4;
    static JLabel motorSpeed;
    static JLabel steering;

    // ---------------------------------------- SensorInterface Values --------------------------------
    private List<ObserverInterface> observers = new ArrayList<>();

    // ---------------------------------------- Controll params ---------------------------------------


    // -------------------------------------- Constructor Build Gui -----------------------------------
    Gui(){
        // --------------------------------- left gui side --------------------------------------------
        jFrame = new JFrame();
        jFrame.setTitle("Car Controller");
        jFrame.setSize(900, 540);
        jFrame.setLocationRelativeTo(null);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel main_panel = new JPanel();
        main_panel.setBackground(Color.DARK_GRAY);
        main_panel.setLayout(new GridLayout(1, 2));
        jFrame.add(main_panel);

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

        Button submitButtton = new Button("Submit");
        panel1.add(submitButtton);

        main_panel.add(panel1);


        // ---------------------------------------- right gui side -------------------------------------------
        JPanel panel2 = new JPanel();
        panel2.setBorder(BorderFactory.createLineBorder(Color.black));
        panel2.setLayout(new GridLayout(2, 2));
        JLabel speedLabel = new JLabel("Fahrgeschwindigkeit");
        JLabel steeringLabel = new JLabel("<html><body><p>Motor eingeschlagen</p> <p>(default: 0°)</p></bodŷ></html>");

        panel2.add(speedLabel);
        panel2.add(motorSpeed);
        panel2.add(steeringLabel);
        panel2.add(steering);

        main_panel.add(panel2);
        submitButtton.addActionListener(e -> {
            notifyObservers(0);
        });

        jFrame.pack();
        jFrame.setVisible(true);
    }

    @Override
    public void lenken(int percent) {
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
            x.update("fr", (Integer) ta1.getValue());
            x.update("bl", (Integer) ta1.getValue());
            x.update("br", (Integer) ta1.getValue());
        }
    }

}
