package SpinLocks;

import java.util.concurrent.ThreadLocalRandom;

public class Backoff {
    final int minDelay, maxDelay;
    int limit;

    public Backoff(int minDelay, int maxDelay) {
        this.minDelay = minDelay;
        this.maxDelay = maxDelay;
        limit = minDelay;
    }

    public void backoff() throws InterruptedException {
        int delay = ThreadLocalRandom.current().nextInt(limit);
        limit = Math.min(maxDelay, 2 * limit);
        Thread.sleep(delay);
    }

}