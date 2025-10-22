package lab7;

public class CounterExampleVolatile {
    public static class MyCounter {
        private volatile int count = 0;  // field is volatile, hence will not be cached (Q2.3)
        public void increment() {
            count++;
        }
        public int getCount() {
            return count;
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
            //Do nothing
        }
        System.out.println(count.getCount());
        // The output here should still be less than 100K. volatile doesn't make the entire read-increment-write process
        // atomic -- we might still switch thread between read and write. It would be sufficient if only one thread were
        // writing (but many reading)
    }
}