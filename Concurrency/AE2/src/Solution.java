import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Solution implements CommandRunner{
    Map<String, Thread> threadMap = new ConcurrentHashMap<>(); // only contains calculating threads
    Map<String, Integer> resultMap = new ConcurrentHashMap<>(); // only contains results (finished threads)
    Map<String,List<String>> afterMap = new ConcurrentHashMap<>(); // contains waiting or calculating one if it has dependent
    // calculating: only exists in key, waiting: may exists in key or value
    public Solution(){}

    public String runCommand(String command){
        String[] split = command.split(" ");
        if(split.length == 0) return "Invalid command";

        String commandType = split[0];

        switch(commandType){
            case "start":
                if(split.length != 2){
                    return "Invalid command";
                }
                return executeStart(split[1]);

            case "cancel":
                if(split.length != 2){
                    return "Invalid command";
                }
                return executeCancel(split[1]);

            case "running":
                if(split.length != 1){
                    return "Invalid command";
                }
                return executeRunning();

            case "get":
                if(split.length != 2){
                    return "Invalid command";
                }
                return executeGet(split[1]);

            case "after":
                if(split.length != 3){
                    return "Invalid command";
                }
                return executeAfter(split[1], split[2]);

            case "finish":
                if(split.length != 1){
                    return "Invalid command";
                }
                return executeFinish();

            case "abort":
                if(split.length != 1){
                    return "Invalid command";
                }
                return executeAbort();

            default:
                return "Invalid command";
        }
    }

    private String executeStart(String N){ // start thread N
        long lN = Long.parseLong(N);
        SlowCalculator slowCalculator = new SlowCalculator(lN, threadMap, afterMap, resultMap, N);
        Thread t = new Thread(slowCalculator);
        t.setName(N);
        threadMap.put(N, t);
        t.start();
        return "started " + N;
    }

    private String executeCancel(String N){ // immediately cancel thread N
        if(threadMap.containsKey(N)){ // if N is calculating
            Thread t = threadMap.get(N);
            t.interrupt();
            try{
                t.join(100);
            }catch (InterruptedException e){
                e.printStackTrace();
            }

            // for checking whether thread is still alive
//            if(t.isAlive()){
//                return "failed cancel";
//            }

            threadMap.remove(N);

            if(afterMap.containsKey(N)){ // if N has dependents, start calculation
                for(String dependents: afterMap.get(N)){
                    executeStart(dependents);
                }
            }
            return "cancelled " + N;
        }

        for(String Nparent : afterMap.keySet()){ // if N is waiting, need to get N's parent first
            if(afterMap.get(Nparent).contains(N)){
                List<String> dependents = new ArrayList<>(afterMap.get(N)); // get all N's dependent
                afterMap.put(Nparent, dependents); // move N's children to after N's parent
                return "cancelled " + N;
            }
        }
        // finished or never requested
        // TODO: spec says to do nothing but we still need to return a string? maybe return null or ""
        return "";
    }

    private String executeRunning(){ // return the threads currently running
        if(!threadMap.isEmpty()){
            int num = threadMap.size();
            String res = num + " calculations running: ";

            for(Thread t: threadMap.values()){
                res += t.getName() + " ";
            }
            return res;
        }else
            return "no calculations running";
    }

    private String executeGet(String N){ // return the status of thread N
        if(resultMap.containsKey(N)){ // calculation has finished
            return "result is " + resultMap.get(N);
        }

        if(threadMap.containsKey(N)){ // calculating
            return "calculating";
        }

        for(List<String> dependents: afterMap.values()){
            if(dependents.contains(N)){ // N is waiting
                return "waiting";
            }
        }
        return "cancelled";
    }

// TODO: Delete threads in afterMap when they're finished
// TODO: How to activate dependent threads after parent thread finished
    private String executeAfter(String N, String M){ // schedule the calculation for M to start when that for N finishes
        if (threadMap.containsKey(N)) { // N is calculating
            if(afterMap.containsKey(N)){ // N already has dependent(s)
                List<String> copy = afterMap.get(N);
                copy.add(M);
                afterMap.put(N, copy);
            }else{
                List<String> child = new ArrayList<>();
                child.add(M);
                afterMap.put(N, child);
            }
//            System.out.println("Sit 1: ");
//            afterMap.forEach((k, v) -> {
//                System.out.println(k + " -> " + v);
//            });
            return M + " will start after " + N;
        }

        for(List<String> dependents: afterMap.values()){
            if(dependents.contains(N)){ // N is waiting
                if(afterMap.containsKey(M)) { // M is waiting or calculating, hence already before N (error)
                    return getCircularDependency(N, M);
                }else{
                    if(afterMap.containsKey(N)){ // N already has dependent(s)
                        List<String> copy = afterMap.get(N);
                        copy.add(M);
                        afterMap.put(N, copy);
                    }else{
                        List<String> child = new ArrayList<>();
                        child.add(M);
                        afterMap.put(N, child);
                    }
//                    System.out.println("Sit 2: ");
//                    afterMap.forEach((k, v) -> {
//                        System.out.println(k + " -> " + v);
//                    });
                    return M + " will start after " + N;
                }
            }
        }

        if(resultMap.containsKey(N)){ // N is finished
            return executeStart(M);
        }else{ // N is cancelled
            return executeStart(M);
        }
    }

    private String getCircularDependency(String N, String M){
        String s = "circular dependency " + N + " ";
        String child = N;
        boolean found = true;

        while (found) {
            found = false;
            for (String key : afterMap.keySet()) {
                if (afterMap.get(key).contains(child)) {
                    s += key + " ";
                    if(key.equals(M)){
                        return s;
                    }else{
                        child = key;
                        found = true;
                        break;
                    }
                }
            }
        }
        return s;
    }

    private String executeFinish(){ // wait for all calculations completed
        while(!threadMap.isEmpty() || !afterMap.isEmpty()){
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        return "finished";
    }

    private String executeAbort(){ // immediately stop all threads
        afterMap.clear();
        for (Thread t : threadMap.values()) {
            t.interrupt();
        }

        // check if all threads stopped
//        boolean allStopped = true;
//        for (Thread t : threadMap.values()) {
//            try {
//                t.join(100);
//                if (t.isAlive()) {
//                    allStopped = false;
//                }
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }

        threadMap.clear();
        return "aborted";
//        if(allStopped) return "aborted";
//        else return "failed aborted"; // shouldn't return this
    }
}
