package lab7.max_finder;

public class MaxFinder2 {
    public static class MaxRow implements Runnable {
        Double[] row;
        SharedDouble d;  // use this to track the maximum across all threads (Q1.2)

        public MaxRow(Double[] row, SharedDouble d) {
            this.row = row;
            this.d = d;
        }

        public void run() {
            for (int i = 0; i < row.length; i++) {
                Double globalMax = d.getD();  // get the current shared maximum value (Q1.2)
                if (row[i] > globalMax) {  // check if this value is greater...
                    try {
                        Thread.sleep(1);  // sleeping (or doing other stuff) between getD and setD increases the chance of a race condition (Q1.3)
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    d.setD(row[i]);  // ...and if the value was greater, update the maximum (Q1.2)
                }
            }
        }
    }

    public static void main(String[] args) {
        int nRows = 100;
        int nCols = 50;
        Double[][] randArray = new Double[nRows][nCols];
        for (int r = 0; r < nRows; r++) {
            for (int c = 0; c < nCols; c++) {
                randArray[r][c] = Math.random();
            }
        }

        // Find the max using loops
        Double max = 0.0;
        for (int r = 0; r < nRows; r++) {
            for (int c = 0; c < nCols; c++) {
                if (randArray[r][c] > max) {
                    max = randArray[r][c];
                }
            }
        }
        System.out.println(max);

        // Threaded solution
        SharedDouble d = new SharedDouble();
        d.setD(0.0);
        Thread[] threads = new Thread[nRows];
        for (int i = 0; i < nRows; i++) {
            threads[i] = new Thread(new MaxRow(randArray[i], d));
            threads[i].start();
        }
        try {
            for (int i = 0; i < nRows; i++) {
                threads[i].join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(d.getD());
    }
}