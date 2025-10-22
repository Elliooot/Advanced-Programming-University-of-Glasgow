package lab7.max_finder;

public class SharedDoubleWithCompare {
    // Q1.4
    private Double d;

    public Double getD() {
        return d;
    }

    public void setD(Double d) {
        this.d = d;
    }

    public void compare(Double a) {
        if (a > d) {
            try {
                Thread.sleep(1);  // still to increase the chance we see a race condition
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            d = a;
        }
    }
}