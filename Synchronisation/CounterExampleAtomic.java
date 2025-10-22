package lab7;

import java.util.concurrent.atomic.AtomicInteger;

public class CounterExampleAtomic {
    public static class MyCounter {
        private AtomicInteger count = new AtomicInteger(0);
        public void increment() {
            // this method isn't synchronised, but the increment method on AtomicInteger means it's safe
            count.incrementAndGet();
        }
        public int getCount() {
            return count.get();
        }
    }
    public static class Counter extends Thread {
        private MyCounter count;
        private int n;
        public Counter(MyCounter count,int n) {
            this.count = count;
            this.n = n;
        }
        public void run() {
            for(int i=0;i<n;i++) {
                count.increment();
            }
        }
    }
    public static void main(String[] args) {
        MyCounter count = new MyCounter();
        int nCounters = 100;
        Counter[] c = new Counter[nCounters];
        for(int i=0;i<nCounters;i++) {
            c[i] = new Counter(count,1000);
            c[i].start();
        }
        try {
            for(int i=0;i<nCounters;i++) {
                c[i].join();
            }
        }catch(InterruptedException e) {
        }
        System.out.println(count.getCount());
    }
}