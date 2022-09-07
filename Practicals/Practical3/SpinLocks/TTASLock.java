package SpinLocks;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class TTASLock implements Lock {
    private AtomicBoolean state = new AtomicBoolean(false);

    @Override
    public void lock() {
        while (true) {
            while (state.get()) {}; //spin while lock taken
            if (!state.getAndSet(true)) //returns prev (false) if free
                return ; //acquire lock if free
            //go back to spining if failed to aquire
        }
    }

    @Override
    public void unlock() {
        state.set(false); //lock free
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
