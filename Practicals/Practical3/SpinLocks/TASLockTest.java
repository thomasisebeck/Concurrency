package SpinLocks;

import java.util.ArrayList;

public class TASLockTest {
    public static void main (String[] args) {

        TASLock l = new TASLock();
        ArrayList<Long> timeArray = new ArrayList<>();
        ArrayList<Long> diffArray = new ArrayList<>();
        ArrayList<Integer> numThreadsArr = new ArrayList<>();
        int numThreads = 0;

        for (int i = 0; i < 5; i++) {
            ArrayList<TASLockThread> threadArr = new ArrayList<>();

            for (int j = 0; j <= i; j++) {
                threadArr.add(new TASLockThread(l));
                threadArr.add(new TASLockThread(l));
            }
            numThreads += 2;
            numThreadsArr.add(numThreads);

           // System.out.println("Testing " + numThreads + " thread(s)");

            long startTime = System.currentTimeMillis();

            //start the threads
            for (int j = 0; j < threadArr.size(); j++) {
                threadArr.get(j).start();
            }
            //wait while a thread is alive
            for (int j = 0; j < threadArr.size(); j++) {
                while (threadArr.get(j).isAlive()) {}
            }

            long elapsedTime = System.currentTimeMillis() - startTime;

            //1 thread sleeps for 500ms

            //add time to array
            timeArray.add(elapsedTime);
            diffArray.add(elapsedTime - (numThreads * 500));

            while (threadArr.size() > 0)
                threadArr.remove(0);
            //Clear the array
            threadArr = null;
        }

        System.out.println("Number of threads: " + numThreadsArr.toString());
        System.out.println("-----------------------------------------------------------");
        System.out.println("TASLock time taken: " + timeArray.toString() + " time in ms");
        System.out.println("TASLock overhead:   " + diffArray.toString() + " time in ms");
        System.out.println("-----------------------------------------------------------");


        /*TASLockThread Thread1 = new TASLockThread(l);
        long startTime = System.nanoTime();
        Thread1.start();
        while (Thread1.isAlive()) {}
        long elapsedTime = System.nanoTime() - startTime;
        System.out.println("Elasped time: " + (elapsedTime / 1000000) + "ms");*/

    }
}
