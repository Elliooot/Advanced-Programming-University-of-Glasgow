import java.util.*;

public class SlowCalculator implements Runnable {

    private final long N;
    private boolean finished = false;
    private boolean cancelled = false;
    private Map<String, Thread> threadMap;
    private Map<String, List<String>> afterMap1;
    private Map<String, Integer> resultMap;
    private String calculationID;

    public SlowCalculator(final long N, Map<String, Thread> threadMap, Map<String, List<String>> afterMap1, Map<String, Integer> resultMap, String calculationID) {
        this.N = N;
        this.threadMap = threadMap;
        this.afterMap1 = afterMap1;
        this.resultMap = resultMap;
        this.calculationID = calculationID;
    }

    public void run() {
        final int result = calculateNumFactors(N);

        if(Thread.currentThread().isInterrupted()){
            cancelled = true;
        }else{
            finished = true;
            resultMap.put(calculationID, result);
        }

        activateDependents();
        threadMap.remove(calculationID);
    }

    private void activateDependents(){
        if(afterMap1.containsKey(calculationID)){ // if the current calculation has dependent(s)
            List<String> dependents = afterMap1.get(calculationID);

            for(String dependentId: dependents){ // start the calculation(s) respectively
                long dependentN = Long.parseLong(dependentId);
                SlowCalculator dependentCalculator = new SlowCalculator(dependentN, threadMap, afterMap1, resultMap, dependentId);
                Thread dependentThread = new Thread(dependentCalculator);
                dependentThread.setName(dependentId);
                threadMap.put(dependentId, dependentThread);
                System.out.println(dependentId + " started");
                dependentThread.start();
            }

            afterMap1.remove(calculationID);
        }
    }

    public boolean isFinished(){
        return finished;
    }

    public boolean isCancelled(){
        return cancelled;
    }

    private static int calculateNumFactors(final long N) {
        // This (very inefficiently) finds and returns the number of unique prime factors of |N|
        // You don't need to think about the mathematical details; what's important is that it does some slow calculation taking N as input
        // You should NOT modify the calculation performed by this class, but you may want to add support for interruption
        int count = 0;
        for (long candidate = 2; candidate < Math.abs(N); ++candidate) {
            if(Thread.currentThread().isInterrupted()){
                return -1;
            }

            if (isPrime(candidate)) {
                if (Math.abs(N) % candidate == 0) {
                    count++;
                }
            }
        }
        return count;
    }

    private static boolean isPrime(final long n) {
        // This (very inefficiently) checks whether n is prime
        // You should NOT modify this method
        for (long candidate = 2; candidate < Math.sqrt(n) + 1; ++candidate) {
            if (n % candidate == 0) {
                return false;
            }
        }
        return true;
    }
}