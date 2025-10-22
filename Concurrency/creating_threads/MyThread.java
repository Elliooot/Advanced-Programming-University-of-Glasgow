package lab6.creating_threads;

import java.util.Random;

// Q1.2
class MyThread extends Thread {
    public void run() {
        int numSeconds = new Random().nextInt(10) + 1;  // random number from 1-10
        try {
            Thread.sleep(numSeconds * 1000);  // convert seconds to milliseconds
        } catch (InterruptedException e) {
            return;  // if interrupted, we should return as quickly as possible
        }
        System.out.println("Slept for " + numSeconds + " seconds");
    }
}
