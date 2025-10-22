import java.util.concurrent.*;

public class ParallelMath {
    static class RunOnSubarray implements Runnable{
        double[] inputArray, outputArray;
        int firstIndex, count;

        public RunOnSubarray(double[] inputArray, double[] outputArray, int firstIndex, int count) {
            this.inputArray = inputArray;
            this.outputArray = outputArray;
            this.firstIndex = firstIndex;
            this.count = count;
        }

        public void run() {
            for (int index = firstIndex; index < firstIndex + count; ++index) {
                final double input = inputArray[index];
                outputArray[index] = calculateExpSlowly(input);
            }
        }

        static double calculateExpSlowly(final double x) {
            // This calculates exp(x) using a Taylor series. The maths is not important. What matters is that
            // it's a slooow calculation. In real life you would use Math.exp(x)
            double result = 1.;
            double factorial = 1.;
            for (int n = 1; n < 1000; ++n) {
                factorial *= n;
                result += Math.pow(x, n) / factorial;
            }
            return result;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        final int numInputs = 1000003;
        double[] inputs = new double[numInputs];
        double[] outputs = new double[numInputs];
        for (int index = 0; index < numInputs; ++index) {
            inputs[index] = Math.random();
        }
        final int numThreads = 4;
        final int countPerThread = numInputs / numThreads + 1; // Allow for one extra calculation per thread to handle leftovers
        ExecutorService pool = Executors.newFixedThreadPool(numThreads); // Create a thread-pool
        for (int threadIndex = 0; threadIndex < numThreads; ++threadIndex) {
            final int firstIndex = threadIndex * countPerThread;
            final int countForThread = Math.min(countPerThread, numInputs - firstIndex); // Make sure the thread doesn't go beyond the last value
            pool.execute(new ParallelMath.RunOnSubarray(inputs, outputs, firstIndex, countForThread));
        }
        pool.shutdown();
        pool.awaitTermination(60, TimeUnit.SECONDS); // Wait for all threads to complete
    }
}
