package NebenlaeufigeSysteme.Classes.Launcher;

import NebenlaeufigeSysteme.Classes.Classes.Controller;
import NebenlaeufigeSysteme.Classes.Classes.SilTest;
import NebenlaeufigeSysteme.Classes.EV3.EV3Motor;
import NebenlaeufigeSysteme.Interfaces.EngineInterface;
import NebenlaeufigeSysteme.Interfaces.SensorInterface;

public class SIL_EV3_Launcher {
    public static void main(String[] args) {

        SensorInterface sil = new SilTest();

        EngineInterface engine = new EV3Motor();
        Controller controller = new Controller(false, sil, sil, sil, sil, engine);
    }
}
