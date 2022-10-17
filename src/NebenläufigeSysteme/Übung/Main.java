package NebenläufigeSysteme.Übung;


public class Main {
    public static void main(String[] args) {
        start(5);
    }
    public static void start(int x) {
        Text_thread[] thread_list = new Text_thread[x];
        for(int i = 0; i<5; i++){
            thread_list[i] = new Text_thread();
            thread_list[i].start();
        }

        for (Text_thread text_thread : thread_list) {
            try {
                text_thread.join();
            } catch (InterruptedException e){
                e.fillInStackTrace();
            }

        }
        System.out.println("Finished");
    }
}