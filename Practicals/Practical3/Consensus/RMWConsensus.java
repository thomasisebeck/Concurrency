package Consensus;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public class RMWConsensus<T> extends ConsensusProtocol<T>{
    //public volatile T[] proposed;
    static AtomicInteger atomicInteger = null;

    public RMWConsensus(int threadCount) {
        super(threadCount);
    }

    @Override
    public void decide() {

        //get my ID and the other threads ID
        int i = (int) Thread.currentThread().getId() - 16;
        int j = 1 - i;

        synchronized (this) {
            if (atomicInteger == null)
                atomicInteger = new AtomicInteger((int) Thread.currentThread().getId() - 16);
        }

        System.out.println(Thread.currentThread().getName() + " register values: [" + proposed[i] + ", " + proposed[j] + "]");

        if (atomicInteger.compareAndSet(i, i)) // i first
            System.out.println(Thread.currentThread().getName() + " decided " + proposed[i]);
        else // j first
            System.out.println(Thread.currentThread().getName() + " decided " + proposed[j]);

    }
}
