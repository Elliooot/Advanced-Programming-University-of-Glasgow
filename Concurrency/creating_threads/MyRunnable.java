wpackage lab6.creating_threads;

import java.util.Random;

// Q1.1
class MyRunnable implements Runnable {
    public void run() {
        int numSeconds = new Random().nextInt(10) + 1;  // random number from 1-10
        try {
            Thread.sleep(numSeconds * 1000);  // convert seconds to milliseconds
        } catch (InterruptedException e) {
            return;  // if interrupted, we should return as quickly as possible
        }
        System.out.println("Slept for " + numSeconds + " seconds");  // this line for Q1.1
//        System.out.println(Thread.currentThread().getName() + " slept for " + numSeconds + " seconds");  // this line for Q1.4
    }
}
