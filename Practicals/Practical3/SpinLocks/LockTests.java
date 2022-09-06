package SpinLocks;

import java.util.ArrayList;

public class LockTests {
    public static void main (String[] args) {

        //sleep time
        final int OVERHEAD_OFFSET = 25;
        final int LOOP_ITERATIONS = 7;

        //************************* TAS LOCK *****************************//

        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("             TAS           ");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~");

        TASLock l_tas = new TASLock();
        ArrayList<Long> timeArray = new ArrayList<>();
        ArrayList<Long> diffArray = new ArrayList<>();
        ArrayList<Integer> numThreadsArr = new ArrayList<>();

        ArrayList<TASLockThread> TASThreads = null;

        for (int i = 1; i < LOOP_ITERATIONS; i++) {

            System.out.println("i = " + i + " ...");

            TASThreads = new ArrayList<>();

            for (int j = 0; j < (5 * i); j++)
                TASThreads.add(new TASLockThread(l_tas));

            for (int j = 0; j < (5 * i); j++)
                TASThreads.get(j).start();


            numThreadsArr.add(TASThreads.size());

            // System.out.println("Testing " + numThreads + " thread(s)");

            long startTime = System.currentTimeMillis();

            //wait while a thread is alive
            for (int j = 0; j < TASThreads.size(); j++) {
                while (TASThreads.get(j).isAlive()) {}
            }

            long elapsedTime = System.currentTimeMillis() - startTime;

            //1 thread sleeps for 500ms

            //add time to array
            timeArray.add(elapsedTime);
            diffArray.add(elapsedTime - (TASThreads.size() * OVERHEAD_OFFSET));

            while (TASThreads.size() > 0)
                TASThreads.remove(0);
            //Clear the array
            TASThreads = null;

        }

        ArrayList<Long> slopes = new ArrayList<>();
        for (int i = 1; i < diffArray.size(); i++)
            slopes.add(diffArray.get(i) - diffArray.get(i - 1));

        System.out.println("Number of threads: " + numThreadsArr.toString());
        System.out.println("----------------------------------------------------------------------");
        System.out.println("TASLock time taken: " + timeArray.toString() + " time in ms");
        System.out.println("TASLock overhead:   " + diffArray.toString() + " time in ms");
        System.out.println("Overhead increase:  " + slopes.toString() + " time in ms");
        System.out.println("----------------------------------------------------------------------");

        l_tas = null;
        timeArray = null;
        diffArray = null;
        numThreadsArr = null;

        //****************************************************************//

        //---------------------------- TTAS LOCK -------------------------//

        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("           TTAS            ");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~");

        TTASLock l_ttas = new TTASLock();
        timeArray = new ArrayList<>();
        diffArray = new ArrayList<>();
        numThreadsArr = new ArrayList<>();

        ArrayList<TTASLockThread> TTASThreads = null;

        for (int i = 1; i < LOOP_ITERATIONS; i++) {

            System.out.println("i = " + i + " ...");

            TTASThreads = new ArrayList<>();

            for (int j = 0; j < (5 * i); j++)
                TTASThreads.add(new TTASLockThread(l_ttas));

            for (int j = 0; j < (5 * i); j++)
                TTASThreads.get(j).start();

            numThreadsArr.add(TTASThreads.size());

            // System.out.println("Testing " + numThreads + " thread(s)");

            long startTime = System.currentTimeMillis();

            //wait while a thread is alive
            for (int j = 0; j < TTASThreads.size(); j++) {
                while (TTASThreads.get(j).isAlive()) {}
            }

            long elapsedTime = System.currentTimeMillis() - startTime;

            //1 thread sleeps for 500ms

            //add time to array
            timeArray.add(elapsedTime);
            diffArray.add(elapsedTime - (TTASThreads.size() * OVERHEAD_OFFSET));

            while (TTASThreads.size() > 0)
                TTASThreads.remove(0);
            //Clear the array
            TTASThreads = null;

        }

        slopes = new ArrayList<>();
        for (int i = 1; i < diffArray.size(); i++)
            slopes.add(diffArray.get(i) - diffArray.get(i - 1));

        System.out.println("Number of threads: " + numThreadsArr.toString());
        System.out.println("----------------------------------------------------------------------");
        System.out.println("TTASLock time taken: " + timeArray.toString() + " time in ms");
        System.out.println("TTASLock overhead:   " + diffArray.toString() + " time in ms");
        System.out.println("Overhead increase:   " + slopes.toString() + " time in ms");
        System.out.println("----------------------------------------------------------------------");


        //----------------------------------------------------------------//



    }
}
