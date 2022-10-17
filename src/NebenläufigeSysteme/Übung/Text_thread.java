package NebenläufigeSysteme.Übung;

import java.lang.Thread;
import java.util.Random;
import java.util.concurrent.TimeUnit;



public class Text_thread extends Thread{
    public void run(){
        double startTime = System.nanoTime();
        try{
            Thread.sleep(1000);
        } catch(InterruptedException e){
            e.printStackTrace();
        }

        Random rg = new Random();
        while(System.nanoTime() - startTime < 5000000000L){
            int sleep_time = rg.nextInt(5000);

            try {Thread.sleep(sleep_time);
            }catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            String str_out = ("Thread: "+ Thread.currentThread() + " sleeped for: " + sleep_time);
            System.out.println(str_out);

        }
        }

}
