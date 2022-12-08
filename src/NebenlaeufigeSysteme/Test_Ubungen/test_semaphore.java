package NebenlaeufigeSysteme.Test_Ubungen;

import java.util.concurrent.*;



public class test_semaphore {
    ExecutorService exS = Executors.newFixedThreadPool(4);
    int c = 0;
    int c2 = 0;


    Semaphore s1 = new Semaphore(1);
    Semaphore s2 = new Semaphore(1);
    public static void main(String[] args) {
        test_semaphore x = new test_semaphore();
    }

    public test_semaphore(){
        long start = System.currentTimeMillis();

        Callable<String> inc = new Callable<String>() {
            @Override
            public String call() throws Exception {
                inc();
                return null;
            }
        };
        Future<String> inc_ret = exS.submit(inc);


        Callable<String> dec = new Callable<String>() {
            @Override
            public String call() throws Exception {
                dec();
                return null;
            }
        };
        Future<String> dec_ret = exS.submit(dec);

        Callable<String> dec2 = new Callable<String>() {
            @Override
            public String call() throws Exception {
                dec2();
                return null;
            }
        };
        Future<String> dec2_ret = exS.submit(dec2);

        Callable<String> inc2 = new Callable<String>() {
            @Override
            public String call() throws Exception {
                inc2();
                return null;
            }
        };
        Future<String> inc2_ret = exS.submit(inc2);


        while(!dec_ret.isDone() | !inc_ret.isDone() | !dec2_ret.isDone() | !inc2_ret.isDone()){
            try{
                Thread.sleep(10);
            }catch(InterruptedException e){
                e.printStackTrace();
            }

        }
        long end = System.currentTimeMillis();
        long time = end - start;
        System.out.println("counter: " +c + " Counter 2: " + c2 + " Uptime: " + time);
        endComponents();
    }

    void inc(){
        for (int i = 0; i < 100; i++) {

            try {
                s1.acquire();
                int current = c;
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                c = current + 1;
                s1.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("inc finished");
    }

    void dec(){
        for(int i = 0; i < 100; i++){
            try {
                s1.acquire();
                int current = c;
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                c = current - 1;
                s1.release();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        System.out.println("dec finished");
    }
    void inc2(){
        for(int i = 0; i < 100; i++){
            try {
                s2.acquire();
                int current = c2;
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                c2 = current + 1;
                s2.release();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        System.out.println("inc2 finished");
    }
    void dec2(){
        for(int i = 0; i < 100; i++){
            try {
                s2.acquire();
                int current = c2;
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                c2 = current - 1;
                s2.release();
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
        System.out.println("dec2 finished");
    }

    void endComponents(){
        try {
            if (!exS.awaitTermination(1000, TimeUnit.MILLISECONDS)) {
                exS.shutdownNow();
                System.out.println("executor shuteddown");
            }
        } catch (InterruptedException e) {
            exS.shutdownNow();
            System.out.println("Executor Failure");
        }
        System.out.println("Exit");
        System.exit(0);
    }

}


