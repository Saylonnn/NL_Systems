package Testumgebung.Test_Ubungen;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Philosoph {
    ExecutorService exS = Executors.newFixedThreadPool(5);
    Staebchen sl;
    Staebchen sr;
    String name;
    String priority;
    int essenZahl = 0;
    boolean gegessen = false;
    Philosoph(Staebchen sl_p, Staebchen sr_p, String n){
        sl = sl_p;
        sr = sr_p;
        name = n;
        priority = "MIN";
    }
    public static void main(String[] args) {

        Staebchen s1 = new Staebchen();
        Staebchen s2 = new Staebchen();
        Staebchen s3 = new Staebchen();
        Staebchen s4 = new Staebchen();
        Staebchen s5 = new Staebchen();

        Philosoph p1 = new Philosoph(s5, s1, "p1");

        Philosoph p2 = new Philosoph(s1, s2, "p2");
        Philosoph p3 = new Philosoph(s2, s3, "p3");
        Philosoph p4 = new Philosoph(s3, s4, "p4");
        Philosoph p5 = new Philosoph(s4, s5, "p5");



        p1.essen("r");
        p2.essen("r");
        p2.setPriority("MAX");
        p3.essen("r");
        p4.essen("l");
        p5.essen("r");

        try {
            Thread.sleep(10000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        p1.gegessen = true;
        p2.gegessen = true;
        p3.gegessen = true;
        p4.gegessen = true;
        p5.gegessen = true;
    }

    public void essen(String x){
        Callable<String> pC = new Callable<String>() {
            @Override
            public String call() throws Exception {
                Thread t = Thread.currentThread();
                if (Philosoph.this.priority.equals("MIN")) {
                    t.setPriority(Thread.MIN_PRIORITY);
                }else{
                    t.setPriority(Thread.MAX_PRIORITY);
                }
                //Philosoph.this.eat(x);
                return null;
            }
        };
        Future<String> pC_ret = exS.submit(pC);

    }
    public void eat(String x) {
        Staebchen s1;
        Staebchen s2;
        if (x.equals("r")){
            s1 = sr;
            s2 = sl;
        }else{
            s1 = sl;
            s2 = sr;
        }
        while (!gegessen) {
            s1.nehmen();
            //print(name + " hat 1 genommen");
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            s2.nehmen();
            //print(name + " hat 2 genommen");

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //print(name + " hat gegessen");
            s1.freigeben();
            //print(name + " hat 1 freigegeben");
            s2.freigeben();
            //print(name + " hat 2 freigegeben");
            System.out.println("gegessen");
             essenZahl++;


        }
        print(name + " eaten: "+ essenZahl);
    }

    void print(String x){
        System.out.println(x);
    }

    void setPriority(String x){
        if (x.equals("MIN")){
            priority = x;
        }else if (x.equals("MAX")){
            priority = x;
        }
    }
}
