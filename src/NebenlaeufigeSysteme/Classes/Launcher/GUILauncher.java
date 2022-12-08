package NebenlaeufigeSysteme.Classes.Launcher;

import NebenlaeufigeSysteme.Classes.GUI.Gui;

public class GUILauncher {
    public static void main(String[] args) {

        Gui gui = new Gui();



        Controller controller = new Controller(true, gui, gui, gui, gui, gui);
        controller.start();

    }
}
