package NebenlaeufigeSysteme.Test_Ubungen;

import java.util.concurrent.Semaphore;

public class Staebchen {

    Semaphore s = new Semaphore(1, false);


    public void nehmen(){
        try {
            s.acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public void  freigeben(){
        s.release();
    }
}
