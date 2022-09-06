package SpinLocks;

public class TASLockThread extends Thread {
    private TASLock lock;

    TASLockThread(TASLock l) {
        this.lock = l;
    }

    public void run() {
        for (int i = 0; i < 5; i++) {
            lock.lock();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {}
            lock.unlock();
        }
    }

}
