package SpinLocks;

import java.util.ArrayList;

public class LockTests {
    public static void TAS(int overheadOffset, int loopIterations, int sleepTime) {

        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("             TAS           ");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~");

        TASLock l_tas = new TASLock();
        ArrayList<Long> timeArray = new ArrayList<>();
        ArrayList<Long> diffArray = new ArrayList<>();
        ArrayList<Integer> numThreadsArr = new ArrayList<>();

        ArrayList<TASLockThread> TASThreads = null;

        for (int i = 0; i < loopIterations; i++) {

            System.out.println("i = " + i + " ...");

            TASThreads = new ArrayList<>();

            for (int j = 0; j < (5 * i); j++)
                TASThreads.add(new TASLockThread(l_tas, sleepTime));

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
            diffArray.add(elapsedTime - (TASThreads.size() * overheadOffset));

            while (TASThreads.size() > 0)
                TASThreads.remove(0);
            //Clear the array
            TASThreads = null;

        }

        double avgSlope = 0;
        ArrayList<Long> slopes = new ArrayList<>();
        for (int i = 1; i < diffArray.size(); i++) {
            slopes.add(diffArray.get(i) - diffArray.get(i - 1));
            avgSlope += diffArray.get(i) - diffArray.get(i - 1);
        }
        avgSlope /= slopes.size();

        System.out.println("Number of threads: " + numThreadsArr.toString());
        System.out.println("----------------------------------------------------------------------");
        System.out.println("TASLock time taken: " + timeArray.toString() + " time in ms");
        System.out.println("TASLock overhead:   " + diffArray.toString() + " time in ms");
        System.out.println("Overhead increase:  " + slopes.toString() + " time in ms");
        System.out.println("Average increase:   " + (int) avgSlope + " time in ms");
        System.out.println("----------------------------------------------------------------------");

    }

    public static void TTAS(int overheadOffset, int loopIterations, int sleepTime) {
        //---------------------------- TTAS LOCK -------------------------//

        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("           TTAS            ");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~");

        TTASLock l_ttas = new TTASLock();
        ArrayList<Long> timeArray = new ArrayList<>();
        ArrayList<Long> diffArray = new ArrayList<>();
        ArrayList<Integer> numThreadsArr = new ArrayList<>();

        ArrayList<TTASLockThread> TTASThreads = null;

        for (int i = 0; i < loopIterations; i++) {

            System.out.println("i = " + i + " ...");

            TTASThreads = new ArrayList<>();

            for (int j = 0; j < (5 * i); j++)
                TTASThreads.add(new TTASLockThread(l_ttas, sleepTime));

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
            diffArray.add(elapsedTime - (TTASThreads.size() * overheadOffset));

            while (TTASThreads.size() > 0)
                TTASThreads.remove(0);
            //Clear the array
            TTASThreads = null;

        }

        double avgSlope = 0;
        ArrayList<Long> slopes = new ArrayList<>();
        for (int i = 1; i < diffArray.size(); i++) {
            slopes.add(diffArray.get(i) - diffArray.get(i - 1));
            avgSlope += diffArray.get(i) - diffArray.get(i - 1);
        }
        avgSlope /= slopes.size();

        System.out.println("Number of threads: " + numThreadsArr.toString());
        System.out.println("----------------------------------------------------------------------");
        System.out.println("TTASLock time taken: " + timeArray.toString() + " time in ms");
        System.out.println("TTASLock overhead:   " + diffArray.toString() + " time in ms");
        System.out.println("Overhead increase:   " + slopes.toString() + " time in ms");
        System.out.println("Average increase:    " + (int) avgSlope + " time in ms");
        System.out.println("----------------------------------------------------------------------");
    }

    public static void main (String[] args) {

        //sleep time
        final int OVERHEAD_OFFSET = 5; //thread.sleep time
        final int LOOP_ITERATIONS = 7;
        final int SLEEP_TIME = 3;


        TTAS(OVERHEAD_OFFSET, LOOP_ITERATIONS, SLEEP_TIME);

        /*

            ~~~~~~~~~~~~~~~~~~~~~~~~~~~
                 TAS
            ~~~~~~~~~~~~~~~~~~~~~~~~~~~
            i = 1 ...
            i = 2 ...
            i = 3 ...
            i = 4 ...
            i = 5 ...
            i = 6 ...
            Number of threads: [5, 10, 15, 20, 25, 30]
            ----------------------------------------------------------------------
            TASLock time taken: [1542, 3554, 6142, 10170, 14456, 19152] time in ms
            TASLock overhead:   [1417, 3304, 5767, 9670, 13831, 18402] time in ms
            Overhead increase:  [1887, 2463, 3903, 4161, 4571] time in ms
            ----------------------------------------------------------------------

         */

        TAS(OVERHEAD_OFFSET, LOOP_ITERATIONS, SLEEP_TIME);

        /*

            ~~~~~~~~~~~~~~~~~~~~~~~~~~~
                       TTAS
            ~~~~~~~~~~~~~~~~~~~~~~~~~~~
            i = 1 ...
            i = 2 ...
            i = 3 ...
            i = 4 ...
            i = 5 ...
            i = 6 ...
            Number of threads: [5, 10, 15, 20, 25, 30]
            ----------------------------------------------------------------------
            TTASLock time taken: [1500, 3490, 6196, 9775, 13012, 17382] time in ms
            TTASLock overhead:   [1375, 3240, 5821, 9275, 12387, 16632] time in ms
            Overhead increase:   [1865, 2581, 3454, 3112, 4245] time in ms
            ----------------------------------------------------------------------

         */


        //----------------------------------------------------------------//



    }
}
