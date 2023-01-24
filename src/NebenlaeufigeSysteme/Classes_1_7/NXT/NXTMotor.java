package NebenlaeufigeSysteme.Classes_1_7.NXT;

import NebenlaeufigeSysteme.Interfaces.EngineInterface;
import lejos.nxt.LCD;
import lejos.nxt.MotorPort;
import lejos.nxt.NXTRegulatedMotor;



public class NXTMotor implements EngineInterface {

    String direction = "stop";
    NXTRegulatedMotor antrieb_r = new NXTRegulatedMotor(MotorPort.A);
    NXTRegulatedMotor antrieb_l = new NXTRegulatedMotor(MotorPort.C);

    NXTRegulatedMotor lenkung = new NXTRegulatedMotor(MotorPort.B);
    @Override
    public void lenken(int percent){
        percent = percent * 10;
        lenkung.rotateTo(percent);
    }
    @Override
    public void fahren(int speed){
        if(direction.equals("fw")) {
            if (speed > 0) {
                //antrieb_l.setSpeed(-speed);
                //antrieb_r.setSpeed(-speed);
            } else if (speed == 0) {
                direction = "stop";
                antrieb_l.setSpeed(0);
                antrieb_r.setSpeed(0);
                antrieb_l.stop();
                antrieb_r.stop();
            } else if (speed < 0) {
                direction = "bw";
                antrieb_l.setSpeed(speed);
                antrieb_r.setSpeed(speed);
                antrieb_r.backward();
                antrieb_l.backward();
            }
        }


        else if(direction.equals("stop")) {
                if (speed > 0) {
                    direction = "fw";
                    antrieb_l.setSpeed(-speed);
                    antrieb_r.setSpeed(-speed);
                    antrieb_l.backward();
                    antrieb_r.backward();
                } else if (speed < 0) {
                    direction = "bw";
                    antrieb_l.setSpeed(speed);
                    antrieb_r.setSpeed(speed);
                    antrieb_l.forward();
                    antrieb_r.forward();
                }
            }
        else if (direction.equals("bw")){
            if(speed > 0){
                direction = "fw";
                antrieb_l.setSpeed(-speed);
                antrieb_r.setSpeed(-speed);
                antrieb_l.backward();
                antrieb_r.backward();
            }else if (speed == 0){
                direction = "stop";
                antrieb_r.stop();
                antrieb_l.stop();
            }else if(speed < 0){
                //antrieb_l.setSpeed(speed);
                //antrieb_r.setSpeed(speed);
            }
        }
    }
}
