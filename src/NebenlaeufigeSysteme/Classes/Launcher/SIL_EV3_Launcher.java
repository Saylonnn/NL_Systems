package NebenlaeufigeSysteme.Classes.Launcher;

import NebenlaeufigeSysteme.Classes.Classes.Controller;
import NebenlaeufigeSysteme.Classes.Classes.SilTest;
import NebenlaeufigeSysteme.Classes_1_7.NXT.NXTMotor;
import NebenlaeufigeSysteme.Classes_1_7.NXT.NXTSensor;
import NebenlaeufigeSysteme.Interfaces.EngineInterface;
import NebenlaeufigeSysteme.Interfaces.SensorInterface;

public class SIL_EV3_Launcher {
    public static void main(String[] args) {

        SensorInterface sil = new SilTest();

        EngineInterface engine = new NXTMotor();
        Controller controller = new Controller(false, sil, sil, sil, sil, engine);
    }
}
