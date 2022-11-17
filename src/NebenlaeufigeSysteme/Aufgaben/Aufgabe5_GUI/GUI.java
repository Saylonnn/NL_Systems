package NebenlaeufigeSysteme.Aufgaben.Aufgabe5_GUI;

import NebenlaeufigeSysteme.Aufgaben.Interfaces.MotorInterface;
import NebenlaeufigeSysteme.Aufgaben.Interfaces.SensorInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class GUI extends JFrame{

    JLabel label;
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

/*
        JPanel sliderPanel1 = new JPanel();
        sliderPanel1.setLayout(new GridLayout(1,2));
        JSlider ss1 = new JSlider(1, 255);
        sliderPanel1.add(ss1);
        JLabel valueLabel1 = new JLabel(Integer.toString(ss1.getValue()));
        sliderPanel1.add(valueLabel1);
        panel1.add(sliderPanel1);

        JPanel sliderPanel2 = new JPanel();
        sliderPanel2.setLayout(new GridLayout(1,2));
        JSlider ss2 = new JSlider(1, 255);
        sliderPanel2.add(ss1);
        JLabel valueLabel2 = new JLabel(Integer.toString(ss2.getValue()));
        sliderPanel1.add(valueLabel2);
        panel1.add(sliderPanel2);

        JPanel sliderPanel3 = new JPanel();
        sliderPanel3.setLayout(new GridLayout(1,2));
        JSlider ss3 = new JSlider(1, 255);
        sliderPanel3.add(ss3);
        JLabel valueLabel3 = new JLabel(Integer.toString(ss3.getValue()));
        sliderPanel1.add(valueLabel3);
        panel1.add(sliderPanel3);

        JPanel sliderPanel4 = new JPanel();
        sliderPanel4.setLayout(new GridLayout(1,2));
        JSlider ss4 = new JSlider(1, 255);
        sliderPanel4.add(ss4);
        JLabel valueLabel4 = new JLabel(Integer.toString(ss4.getValue()));
        sliderPanel1.add(valueLabel4);
        panel1.add(sliderPanel4);

        Button submitButtton = new Button("Submit");
        panel1.add(submitButtton);
*/
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
                GUI gui = new GUI();
                gui.setVisible(true);
            }
        });

    }
    public void actionPerformed(ActionEvent e){


    }}
