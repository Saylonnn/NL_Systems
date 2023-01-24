package NebenlaeufigeSysteme.Classes.Launcher;

import NebenlaeufigeSysteme.Classes.Classes.Controller;
import NebenlaeufigeSysteme.Classes_1_7.NXT.NXTMotor;
import NebenlaeufigeSysteme.Classes_1_7.NXT.NXTSensor;
import NebenlaeufigeSysteme.Interfaces.EngineInterface;
import NebenlaeufigeSysteme.Interfaces.SensorInterface;

public class NXT_NXT_Launcher {
    public static void main(String[] args) {
        SensorInterface sensor1 = new NXTSensor("FL");
        SensorInterface sensor2 = new NXTSensor("FR");
        SensorInterface sensor3 = new NXTSensor("BL");
        SensorInterface sensor4 = new NXTSensor("BR");
        EngineInterface engine = new NXTMotor();
        Controller controller = new Controller(false, sensor1, sensor2, sensor3, sensor4, engine);
    }
}
