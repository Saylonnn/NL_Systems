package NebenlaeufigeSysteme.Aufgaben.Aufgabe5_GUI;

import NebenlaeufigeSysteme.Aufgaben.Interfaces.MotorInterface;
import NebenlaeufigeSysteme.Aufgaben.Interfaces.SensorInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class GUI extends JFrame  {
    JLabel label;
    public GUI() {
        this.setTitle("Car Controller");
        this.setSize(900, 540);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Menueleiste
        JMenuBar menuBar = new JMenuBar();
        menuBar.setOpaque(true);


        this.label = new JLabel("Car Controller");
        this.add(label);
        JButton button = new JButton("moin");
        this.add(button);
        //button.addActionListener(this);

        //this.pack();
        this.setVisible(true);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                GUI m = new GUI();


            }
        });

    }
    public void actionPerformed(ActionEvent e){


    }}
