package SpinLocks;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class BackoffLock implements Lock {
    private AtomicBoolean state = new AtomicBoolean(false);
    private static final int MIN_DELAY = 10;
    private static final int MAX_DELAY = 30;

    @Override
    public void lock() {
        Backoff backoff = new Backoff(MIN_DELAY, MAX_DELAY);
        while (true) {
            while (state.get()) {} //spin while state is true (occupied)

            if (!state.getAndSet(true)) //try to acquire
                return ;
            else {
                try {
                    backoff.backoff(); //wait longer if you can't acquire
                } catch (InterruptedException e) {}
            }
        }
    }

    @Override
    public void unlock() {
        state.set(false);
    }

    //needed to implement lock

    @Override
    public void lockInterruptibly() throws InterruptedException {}
    @Override
    public boolean tryLock() { return false; }
    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException { return false; }
    @Override
    public Condition newCondition() {  return null; }
}
