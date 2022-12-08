package NebenlaeufigeSysteme.Classes.NXT;

import NebenlaeufigeSysteme.Interfaces.EngineInterface;
import lejos.nxt.Motor;
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