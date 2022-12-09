package NebenlaeufigeSysteme.Classes_1_7.NXT;

import NebenlaeufigeSysteme.Interfaces.EngineInterface;
import lejos.nxt.LCD;
import lejos.nxt.MotorPort;
import lejos.nxt.NXTRegulatedMotor;



public class NXTMotor implements EngineInterface {


    NXTRegulatedMotor antrieb_r = new NXTRegulatedMotor(MotorPort.A);
    NXTRegulatedMotor antrieb_l = new NXTRegulatedMotor(MotorPort.C);

    NXTRegulatedMotor lenkung = new NXTRegulatedMotor(MotorPort.B);
    @Override
    public void lenken(int percent){
        percent = percent * 10;
        lenkung.rotateTo(percent);
    }
    @Override
    public void fahren(int percent){
        percent = - percent;
        if (percent > 0) {
            antrieb_l.setSpeed(percent);
            antrieb_r.setSpeed(percent);
            antrieb_l.forward();
            antrieb_r.forward();

            LCD.drawString("fahren"+percent , 2, 2);
        }
        if (percent < 0){
            antrieb_l.setSpeed(-percent);
            antrieb_r.setSpeed(-percent);
            antrieb_l.backward();
            antrieb_r.backward();
        }
        if (percent == 0){
            LCD.drawString("STOP",1,1);
            antrieb_l.setSpeed(0);
            antrieb_r.setSpeed(0);
            antrieb_l.stop();
            antrieb_r.stop();
        }
    }
}
