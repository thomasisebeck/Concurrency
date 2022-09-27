package SpinLocks;

import java.util.ArrayList;

public class LockTests {
    public static void TAS(int loopIterations, int sleepTime) {

        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("             TAS           ");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~");

        TASLock l_tas = new TASLock();
        ArrayList<Long> timeArray = new ArrayList<>();
        ArrayList<Long> diffArray = new ArrayList<>();
        ArrayList<Integer> numThreadsArr = new ArrayList<>();

        ArrayList<TASLockThread> Threads = null;

        for (int i = 0; i < loopIterations; i++) {

            System.out.println("i = " + i + " ...");

            Threads = new ArrayList<>();

            for (int j = 0; j < (5 * i); j++)
                Threads.add(new TASLockThread(l_tas, sleepTime));

            for (int j = 0; j < (5 * i); j++)
                Threads.get(j).start();

            numThreadsArr.add(Threads.size());

            // System.out.println("Testing " + numThreads + " thread(s)");

            long startTime = System.currentTimeMillis();

            //wait while a thread is alive
            for (int j = 0; j < Threads.size(); j++) {
                while (Threads.get(j).isAlive()) {}
            }

            long elapsedTime = System.currentTimeMillis() - startTime;

            //1 thread sleeps for 500ms

            //add time to array
            timeArray.add(elapsedTime);
            diffArray.add(elapsedTime - (Threads.size() * sleepTime));

            while (Threads.size() > 0)
                Threads.remove(0);
            //Clear the array
            Threads = null;

        }

        double avgSlope = 0;
        ArrayList<Long> slopes = new ArrayList<>();

        for (int i = 1; i < diffArray.size(); i++)
            slopes.add(diffArray.get(i) - diffArray.get(i - 1));

        for (int i = 0; i < slopes.size(); i++)
            avgSlope += slopes.get(i);

        avgSlope /= slopes.size();

        System.out.println("Number of threads: " + numThreadsArr.toString());
        System.out.println("----------------------------------------------------------------------");
        System.out.println("TASLock time taken: " + timeArray.toString() + " time in ms");
        System.out.println("TASLock overhead:   " + diffArray.toString() + " time in ms");
        System.out.println("Overhead increase:  " + slopes.toString() + " time in ms");
        System.out.println("Average increase:   " + (int) avgSlope + " time in ms");
        System.out.println("----------------------------------------------------------------------");

    }

    public static void TTAS(int loopIterations, int sleepTime) {

        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("            TTAS           ");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~");

        TTASLock l_ttas = new TTASLock();
        ArrayList<Long> timeArray = new ArrayList<>();
        ArrayList<Long> diffArray = new ArrayList<>();
        ArrayList<Integer> numThreadsArr = new ArrayList<>();

        ArrayList<TTASLockThread> Threads = null;

        for (int i = 0; i < loopIterations; i++) {

            System.out.println("i = " + i + " ...");

            Threads = new ArrayList<>();

            for (int j = 0; j < (5 * i); j++)
                Threads.add(new TTASLockThread(l_ttas, sleepTime));

            for (int j = 0; j < (5 * i); j++)
                Threads.get(j).start();

            numThreadsArr.add(Threads.size());

            // System.out.println("Testing " + numThreads + " thread(s)");

            long startTime = System.currentTimeMillis();

            //wait while a thread is alive
            for (int j = 0; j < Threads.size(); j++) {
                while (Threads.get(j).isAlive()) {}
            }

            long elapsedTime = System.currentTimeMillis() - startTime;

            //1 thread sleeps for 500ms

            //add time to array
            timeArray.add(elapsedTime);
            diffArray.add(elapsedTime - (Threads.size() * sleepTime));

            while (Threads.size() > 0)
                Threads.remove(0);
            //Clear the array
            Threads = null;

        }

        double avgSlope = 0;
        ArrayList<Long> slopes = new ArrayList<>();

        for (int i = 1; i < diffArray.size(); i++)
            slopes.add(diffArray.get(i) - diffArray.get(i - 1));

        for (int i = 0; i < slopes.size(); i++)
            avgSlope += slopes.get(i);

        avgSlope /= slopes.size();

        System.out.println("Number of threads: " + numThreadsArr.toString());
        System.out.println("----------------------------------------------------------------------");
        System.out.println("TTASLock time taken: " + timeArray.toString() + " time in ms");
        System.out.println("TTASLock overhead:   " + diffArray.toString() + " time in ms");
        System.out.println("Overhead increase:   " + slopes.toString() + " time in ms");
        System.out.println("Average increase:    " + (int) avgSlope + " time in ms");
        System.out.println("----------------------------------------------------------------------");
    }

    public static void BACKOFF(int loopIterations, int sleepTime) {

        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("          BACKOFF          ");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~");

        BackoffLock l_backoff = new BackoffLock();
        ArrayList<Long> timeArray = new ArrayList<>();
        ArrayList<Long> diffArray = new ArrayList<>();
        ArrayList<Integer> numThreadsArr = new ArrayList<>();

        ArrayList<BackoffLockThread> Threads = null;

        for (int i = 0; i < loopIterations; i++) {

            System.out.println("i = " + i + " ...");

            Threads = new ArrayList<>();

            for (int j = 0; j < (5 * i); j++)
                Threads.add(new BackoffLockThread(l_backoff, sleepTime));

            for (int j = 0; j < (5 * i); j++)
                Threads.get(j).start();

            numThreadsArr.add(Threads.size());

            // System.out.println("Testing " + numThreads + " thread(s)");

            long startTime = System.currentTimeMillis();

            //wait while a thread is alive
            for (int j = 0; j < Threads.size(); j++) {
                while (Threads.get(j).isAlive()) {}
            }

            long elapsedTime = System.currentTimeMillis() - startTime;

            //1 thread sleeps for 500ms

            //add time to array
            timeArray.add(elapsedTime);
            diffArray.add(elapsedTime - (Threads.size() * sleepTime));

            while (Threads.size() > 0)
                Threads.remove(0);
            //Clear the array
            Threads = null;

        }

        double avgSlope = 0;
        ArrayList<Long> slopes = new ArrayList<>();

        for (int i = 1; i < diffArray.size(); i++)
            slopes.add(diffArray.get(i) - diffArray.get(i - 1));

        for (int i = 0; i < slopes.size(); i++)
            avgSlope += slopes.get(i);

        avgSlope /= slopes.size();

        System.out.println("Number of threads: " + numThreadsArr.toString());
        System.out.println("----------------------------------------------------------------------");
        System.out.println("BackoffLock time taken: " + timeArray.toString() + " time in ms");
        System.out.println("BackoffLock overhead:   " + diffArray.toString() + " time in ms");
        System.out.println("Overhead increase:      " + slopes.toString() + " time in ms");
        System.out.println("Average increase:       " + (int) avgSlope + " time in ms");
        System.out.println("----------------------------------------------------------------------");

    }
        
    public static void main (String[] args) {

        //sleep time
        final int OVERHEAD_OFFSET = 5; //thread.sleep time
        final int LOOP_ITERATIONS = 7;
        final int SLEEP_TIME = 20;

        TTAS(LOOP_ITERATIONS, SLEEP_TIME);

        try {
            System.out.println("Cooling off...");
            Thread.sleep(10000);
            System.out.println("Done cooling off...");
        }
        catch (InterruptedException e) {}

        TAS(LOOP_ITERATIONS, SLEEP_TIME);

        try {
            System.out.println("Cooling off...");
            Thread.sleep(10000);
            System.out.println("Done cooling off...");
        }
        catch (InterruptedException e) {}

        BACKOFF(LOOP_ITERATIONS, SLEEP_TIME);

    }
}
