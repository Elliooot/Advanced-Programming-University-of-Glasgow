package lab6.creating_threads;

public class CreatingThreads1 {

    public static void main(String[] args) {
        // Q1.1
        MyRunnable myRunnable = new MyRunnable();
        Thread myThread = new Thread(myRunnable);
        myThread.start();

        // Q1.2
        MyThread myOtherThread = new MyThread();
        myOtherThread.start();
    }
}
