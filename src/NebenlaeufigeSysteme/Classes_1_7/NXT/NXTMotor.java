package NebenlaeufigeSysteme.Classes_1_7.NXT;

import NebenlaeufigeSysteme.Interfaces.EngineInterface;
import lejos.nxt.LCD;
import lejos.nxt.MotorPort;
import lejos.nxt.NXTRegulatedMotor;



public class NXTMotor implements EngineInterface {


    NXTRegulatedMotor antrieb = new NXTRegulatedMotor(MotorPort.A);
    NXTRegulatedMotor lenkung = new NXTRegulatedMotor(MotorPort.B);
    @Override
    public void lenken(int percent){
        lenkung.rotate(percent);
    }
    @Override
    public void fahren(int percent){
        if (percent > 0) {
            antrieb.setSpeed(percent);
            antrieb.forward();

            LCD.drawString("fahren"+percent , 2, 2);
        }
        if (percent < 0){
            antrieb.setSpeed(Math.abs(percent));
            antrieb.backward();
        }
        if (percent == 0){
            antrieb.setSpeed(0);
            antrieb.stop();
        }
    }
}
