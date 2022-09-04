package Consensus;

import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static void main (String[] args) {
        Thread t1 = new ConsensusThread(new RMWConsensus(2));
        Thread t2 = new ConsensusThread(new RMWConsensus(2));

        t1.start();
        t2.start();

    }
}
