package SpinLocks;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class TASLock implements Lock {
    private AtomicBoolean state = new AtomicBoolean(false);

    @Override
    public void lock() {
        while (state.getAndSet(true)) {}
    }

    @Override
    public void unlock() {
        state.set(false);
    }

    //Needed to implement lock interface
    @Override
    public void lockInterruptibly() throws InterruptedException {}
    @Override
    public boolean tryLock() { return false; }
    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException { return false; }
    @Override
    public Condition newCondition() { return null; }
}
