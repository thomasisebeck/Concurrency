package SpinLocks;

public class TTASLockThread extends Thread {
    private TTASLock lock;
    private final int sleepTime;

    TTASLockThread(TTASLock l, int sleepTime) {
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