package NebenlaeufigeSysteme.Classes.Launcher;

import NebenlaeufigeSysteme.Classes.Classes.Controller;
import NebenlaeufigeSysteme.Classes.Classes.SilTest;
import NebenlaeufigeSysteme.Classes_1_7.NXT.NXTMotor;
import NebenlaeufigeSysteme.Interfaces.EngineInterface;

public class SIL_NXT_Launcher {
    public static void main(String[] args) {
        SilTest sil = new SilTest();
        EngineInterface engine = new NXTMotor();
        Controller controller = new Controller(false, sil, sil, sil, sil, engine);
    }
}
