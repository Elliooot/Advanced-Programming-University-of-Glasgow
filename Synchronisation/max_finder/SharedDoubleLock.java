package lab7.max_finder;

import java.util.concurrent.locks.ReentrantLock;

public class SharedDoubleLock {
    private Double d;
    private ReentrantLock l = new ReentrantLock();  // this lock will be used to control access to the counter (Q1.5)

    public void lock() {
        l.lock();  // acquire the lock, preventing other threads from doing so
    }

    public void unlock() {
        l.unlock();  // release the lock, so other threads can acquire it
    }

    public Double getD() {
        return d;
    }

    public void setD(Double d) { // no need for synchronized here, since we now lock before calling it
        this.d = d;
    }
}