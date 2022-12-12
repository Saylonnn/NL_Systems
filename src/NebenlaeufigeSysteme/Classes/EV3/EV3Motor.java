package NebenlaeufigeSysteme.Classes.EV3;

import NebenlaeufigeSysteme.Interfaces.EngineInterface;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.NXTRegulatedMotor;
import lejos.hardware.port.MotorPort;


public class EV3Motor implements EngineInterface {

    NXTRegulatedMotor antrieb_l = new NXTRegulatedMotor(MotorPort.C);
    NXTRegulatedMotor antrieb_r = new NXTRegulatedMotor(MotorPort.B);

    NXTRegulatedMotor lenkung = new NXTRegulatedMotor(MotorPort.A);
    int currentAngle = Math.round(lenkung.getPosition());
    int MAX_ANGLE = currentAngle + 20;
    int MIN_ANGLE = currentAngle - 20;
    @Override
    public void lenken(int percent){
        int targetAngle = currentAngle + percent;
        if(targetAngle < MIN_ANGLE){
            lenkung.rotateTo(MIN_ANGLE);
        }
        else if (targetAngle > MAX_ANGLE){
            lenkung.rotateTo(MAX_ANGLE);
        }else {
            lenkung.rotate(currentAngle + currentAngle);
        }
    }
    @Override
    public void fahren(int percent){
        if (percent > 0) {
            antrieb_l.setSpeed(percent);
            antrieb_r.setSpeed(percent);
            antrieb_l.forward();
            antrieb_r.forward();

            LCD.drawString("fahren"+percent , 2, 2);
        }
        if (percent < 0){
            antrieb_l.setSpeed(Math.abs(percent));
            antrieb_r.setSpeed(Math.abs(percent));
            antrieb_l.backward();
            antrieb_r.backward();
        }
        if (percent == 0){
            antrieb_l.setSpeed(0);
            antrieb_r.setSpeed(0);
            antrieb_l.stop();
            antrieb_r.stop();
        }
    }
}
