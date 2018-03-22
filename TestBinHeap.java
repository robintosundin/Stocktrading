import java.util.HashSet;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Random;


public class TestBinHeap {
    static Random randgen = new Random(686585037);

    static int nopdone = 0;
    static int lastnopdone = -1;
    static int noperation = 0;
    static StringBuilder log = new StringBuilder();

    static void showException(Exception e, StringBuilder log) {
        System.out.println("The operation on the last line of the following code causes an exception:\n");
        System.out.print(log.toString());
        System.out.println("\nThe exception is: " + e.toString());
        System.exit(1);
    }
    
    
    static void runTest(int noperation, int interval) {
        log = new StringBuilder();
        
        log.append("PrioQueue<Integer> pq = new BinHeap<>(new NaturalOrderComparator<Integer>());\n");
        PrioQueue<Integer> pq = new BinHeap<>(new NaturalOrderComparator<Integer>());

        PriorityQueue<Integer> refpq = new PriorityQueue<>(10, new NaturalOrderComparator<Integer>());
        for (int j = 0; j < noperation; j++) {
            int whichop = randgen.nextInt(5);
            switch (whichop) {
            case 0: {  // add
                int element = randgen.nextInt(interval);
                log.append("pq.add(" + element + ");\n");
                try {
                    pq.add(element);
                } catch (Exception e) {
                    showException(e, log);
                }
                refpq.add(element);
            } break;
            case 1: {  // peek
                log.append("pq.peek();");
                Integer res = null;
                try {
                    res = pq.peek();
                } catch (Exception e) {
                    showException(e, log);
                }
                log.append("  // result: " + res + "\n");
                Integer refres = refpq.peek();
                if (res != refres) {
                    System.out.println("The result on the last line of the following code is incorrect:\n");
                    System.out.print(log.toString());
                    System.out.println("The result should be: " + refres);
                    System.exit(1);
                }
            } break;
            case 2: {  // poll
                log.append("pq.poll();");
                Integer res = null;
                try {
                    res = pq.poll();
                } catch (Exception e) {
                    showException(e, log);
                }
                log.append("  // result: " + res + "\n");
                Integer refres = refpq.poll();
                if (res != refres) {
                    System.out.println("The result on the last line of the following code is incorrect:\n");
                    System.out.print(log.toString());
                    System.out.println("The result should be: " + refres);
                    System.exit(1);
                }
            } break;
            case 3: {  // iterator
                log.append("pq.iterator();\n");
                Iterator<Integer> iter = null;
                try {
                    iter = pq.iterator();
                } catch (Exception e) {
                    showException(e, log);
                }
                Iterator<Integer> refiter = refpq.iterator();
                HashSet<Integer> set = new HashSet<>();
                while (iter.hasNext()) set.add(iter.next());
                HashSet<Integer> refset = new HashSet<>();
                while (refiter.hasNext()) refset.add(refiter.next());
                if (!set.equals(refset)) {
                    System.out.println("The result on the last line of the following code is incorrect:\n");
                    System.out.print(log.toString());
                    System.out.println("The returned iterator iterates over the following set of elements:");
                    System.out.println(set.toString());
                    System.out.println("The iterator should iterate over the following set of elements:");
                    System.out.println(refset.toString());
                    System.exit(1);
                }
            } break;
            case 4: {  // remove
                int element = randgen.nextInt(interval);
                log.append("pq.remove(" + element + ");\n");
                try {
                    pq.remove(element);
                } catch (Exception e) {
                    showException(e, log);
                }
                refpq.remove(element);
            } break;
            }
            nopdone++;
        }
        
    }
    
    private static class Monitor implements Runnable {
        final int nsec;
        
        Monitor(int nsec) {
            this.nsec = nsec;
        }
        
        @Override
        public void run() {
            for (int secs = 0; secs < nsec; secs++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    return;
                }
                if (nopdone == lastnopdone) {
                    System.out.println("No new operation completed during the last second.");
                    System.out.println("Your program seems to loop (or be very slow) at the operation on the last line of the following code:\n");
                    System.out.print(log.toString());
                    System.exit(1);
                }
                lastnopdone = nopdone;
                System.out.println(nopdone + " operations executed. (No bugs found so far.)");
            }
            System.out.println("No bugs found in " + nsec + " seconds.");
            System.exit(0);
        }
        
    }
    
    
    public static void main(String[] args) {
        if (args.length > 1) {
            System.out.println("The program accepts zero or one arguments.");
            System.out.println("If there is an argument this should be a positive number specifying the number of seconds to run the test.");
            System.exit(1);
        }
        
        int nrepetition = 5;
        
        int nsec = 5;
        if (args.length == 1) {
            try {
                nsec = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.out.println("The given argument is not a valid number.");
                System.exit(1);
            }
        }

        if (args.length == 0) {
            System.out.println("The test will run for " + nsec + " seconds or until a bug is found. (The number of seconds can be specified as an argument to the program.)");
        }
        
        new Thread(new Monitor(nsec)).start();
        
        for (int iter = 1;; iter++) {
            nrepetition *= 2;
            noperation = iter;
            int interval = iter;
            for (int i = 0; i < nrepetition; i++) {
                runTest(noperation, interval);
            }
        }        
    
    }
}
