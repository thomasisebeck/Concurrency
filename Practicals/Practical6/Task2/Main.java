package Task2;

import java.util.ArrayList;

public class Main {
    public static void main (String[] args) {
        final int CAPACITY = 5;
        final int NUM_DEQUEUERS = 5;
        final int NUM_ENQUEERS = 6;

        BoundedQueue<Integer> Q = new BoundedQueue<Integer>(CAPACITY);

        ArrayList<EnqueueThread> enqueueThreads = new ArrayList<>();
        ArrayList<DequeueThread> dequeueThreads = new ArrayList<>();

        for (int i = 0; i < NUM_ENQUEERS; i++)
            enqueueThreads.add(new EnqueueThread(Q));

        for (int i = 0; i < NUM_DEQUEUERS; i++)
            dequeueThreads.add(new DequeueThread(Q));

        //start all the threads
        int enqStarted = 0;
        int deqStarted = 0;

        while (enqStarted < NUM_ENQUEERS || deqStarted < NUM_DEQUEUERS) {
            if (enqStarted < NUM_ENQUEERS)
                enqueueThreads.get(enqStarted++).start();
            if (deqStarted < NUM_DEQUEUERS)
                dequeueThreads.get(deqStarted++).start();
        }


    }
}
