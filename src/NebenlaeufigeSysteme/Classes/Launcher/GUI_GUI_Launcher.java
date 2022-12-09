package NebenlaeufigeSysteme.Classes.Launcher;

import NebenlaeufigeSysteme.Classes.Classes.Controller;
import NebenlaeufigeSysteme.Classes.GUI.Gui;

public class GUI_GUI_Launcher {
    public static void main(String[] args) {
        Gui gui = new Gui();
        Controller controller = new Controller(true, gui, gui, gui, gui, gui);
        controller.start();
    }
}
