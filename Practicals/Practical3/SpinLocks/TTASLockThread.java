package SpinLocks;

public class TTASLockThread extends Thread {
    private TTASLock lock;

    TTASLockThread(TTASLock l) {
        this.lock = l;
    }

    public void run() {
        for (int i = 0; i < 5; i++) {
            lock.lock();
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {}
            lock.unlock();
        }
    }

}