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

        if (atomicInteger.compareAndSet(i, i))
            System.out.println(Thread.currentThread().getName() + " decided " + proposed[i] + " index " + i);
        else
            System.out.println(Thread.currentThread().getName() + " decided " + proposed[j] + " index " + j);

    }
}
