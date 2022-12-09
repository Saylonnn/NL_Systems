package NebenlaeufigeSysteme.Classes.Launcher;

import NebenlaeufigeSysteme.Classes.Classes.Controller;
import NebenlaeufigeSysteme.Classes.Classes.SilTest;


public class SIL_SIL_Launcher {
    public static void main(String[] args){
        SilTest sil = new SilTest();
        Controller controller = new Controller(true, sil, sil, sil, sil, sil);
        controller.start();
    }


}
