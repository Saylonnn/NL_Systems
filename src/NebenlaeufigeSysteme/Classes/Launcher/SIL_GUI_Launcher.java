package NebenlaeufigeSysteme.Classes.Launcher;

import NebenlaeufigeSysteme.Classes.Classes.Controller;
import NebenlaeufigeSysteme.Classes.Classes.SilTest;
import NebenlaeufigeSysteme.Classes.GUI.Gui;

public class SIL_GUI_Launcher {
    public static void main(String[] args) {
        Gui gui = new Gui();
        SilTest sil = new SilTest();

        Controller controller = new Controller(true, sil, sil, sil, sil, gui);
        controller.start();
    }
}
