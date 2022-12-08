package NebenlaeufigeSysteme.Classes.Launcher;


import NebenlaeufigeSysteme.Classes.NXT.NXTMotor;
import NebenlaeufigeSysteme.Classes.NXT.NXTSensor;
import NebenlaeufigeSysteme.Interfaces.EngineInterface;
import NebenlaeufigeSysteme.Interfaces.SensorInterface;

public class RoboLauncher {
    public static void main(String[] args) {
        if (args[0].equals("nxt") && args[1].equals("nxt")){
            SensorInterface sensor1 = new NXTSensor("fl");
            SensorInterface sensor2 = new NXTSensor("fr");
            SensorInterface sensor3 = new NXTSensor("bl");
            SensorInterface sensor4 = new NXTSensor("br");
            EngineInterface engine = new NXTMotor();
            Controller controller = new Controller(false, sensor1, sensor2, sensor3, sensor4, engine);
            controller.start();
        }


    }
}
