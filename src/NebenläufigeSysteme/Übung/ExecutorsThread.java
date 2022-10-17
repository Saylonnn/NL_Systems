package NebenläufigeSysteme.Übung;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

public class ExecutorsThread {
    public static void main(String[] args) {
        launche_nThreads(10);
    }
    public static void launche_nThreads(int n){
        ExecutorService executor = Executors.newFixedThreadPool(n);
        Callable<String> callableTask = () -> {
            double startTime = System.nanoTime();
            try {
                Random rg = new Random();
                while (System.nanoTime() - startTime < 5000000000L) {
                    int sleep_time = rg.nextInt(5000);
                    TimeUnit.MILLISECONDS.sleep(sleep_time);
                    String str_out = ("Thread: " + Thread.currentThread() + " sleeped for: " + sleep_time);
                    System.out.println(str_out);
                };
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return (Thread.currentThread() + " finished.");
        };
        List<Callable<String>> callableTasks = new ArrayList<>();
        try{
            for(int i= 0; i < n; i++){
                Future<String> future = executor.submit(callableTask);
            }
            List<Future<String>> result = executor.invokeAll(callableTasks);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        try {
            if (!executor.awaitTermination(10000, TimeUnit.MILLISECONDS)) {
                executor.shutdownNow();
                System.out.println("executor shuteddown");
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            System.out.println("Executor Failure");
        }
    }
}

