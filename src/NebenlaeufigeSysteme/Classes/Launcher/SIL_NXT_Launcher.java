package NebenlaeufigeSysteme.Classes.Launcher;

import NebenlaeufigeSysteme.Classes.Classes.Controller;
import NebenlaeufigeSysteme.Classes_1_7.NXT.NXTMotor;
import NebenlaeufigeSysteme.Classes_1_7.NXT.NXTSensor;
import NebenlaeufigeSysteme.Interfaces.EngineInterface;
import NebenlaeufigeSysteme.Interfaces.SensorInterface;

public class SIL_NXT_Launcher {
    public static void main(String[] args) {
        SensorInterface sensor1 = new NXTSensor("fl");
        SensorInterface sensor2 = new NXTSensor("fr");
        SensorInterface sensor3 = new NXTSensor("bl");
        SensorInterface sensor4 = new NXTSensor("br");
        EngineInterface engine = new NXTMotor();
        Controller controller = new Controller(false, sensor1, sensor2, sensor3, sensor4, engine);
        controller.start();

    }
}
