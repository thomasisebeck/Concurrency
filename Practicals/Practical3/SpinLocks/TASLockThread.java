package SpinLocks;

public class TASLockThread extends Thread {
    private TASLock lock;
    private final int sleepTime;

    TASLockThread(TASLock l, int sleepTime) {
        this.lock = l;
        this.sleepTime = sleepTime;
    }

    public void run() {
        for (int i = 0; i < 5; i++) {
            lock.lock();
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {}
            lock.unlock();
        }
    }

}
