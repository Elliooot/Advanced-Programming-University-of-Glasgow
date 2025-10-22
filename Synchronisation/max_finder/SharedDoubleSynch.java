package lab7.max_finder;

public class SharedDoubleSynch {
    private Double d;

    public Double getD() {
        return d;
    }

    public void setD(Double d) {
        this.d = d;
    }

    public synchronized void compare(Double a) {  // synchronized keyword ensures only one thread is doing compare-and-update at any moment (Q1.5)
        if (a > d) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            d = a;
        }
    }
}