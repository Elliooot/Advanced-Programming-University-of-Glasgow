package lab6.creating_threads;

public class CreatingThreads2 {

    public static void main(String[] args) {
        // Q1.3+1.4
        int numThreads = 5;
        Thread[] threads = new Thread[numThreads];
        for (int i = 0; i < numThreads; i++) {
            MyRunnable myRunnable = new MyRunnable();
            threads[i] = new Thread(myRunnable, "Thread #" + i);
            threads[i].start();
        }

        // Q1.5
        System.out.println("All threads started!");

        // Q1.6
        for (int i = 0; i < numThreads; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
            }
        }
        System.out.println("All threads finished!");
    }
}
