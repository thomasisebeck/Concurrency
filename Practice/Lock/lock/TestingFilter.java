package lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class TestingFilter implements Lock {

    private volatile int[] level;
    private volatile int[] victim;
    private int numLevels;


    TestingFilter(int n) {
        level = new int[n]; //Level of each thread
        victim = new int[n]; //Victim on each level
        numLevels = n;
    }


    @Override
    public void lock() {
        int me = (int) Thread.currentThread().getId() - 11 - numLevels;
        for (int i = 1; i < numLevels; i++) {
            level[me] = i;
            victim[i] = me;

            //check each level
            for (int k = 0; k < numLevels; k++) {
                while (k != me && level[k] >= i && victim[i] == me) {} //wait
            }

            //enter level
        }
    }

    @Override
    public void unlock() {
        int me = (int) Thread.currentThread().getId() - 11 - numLevels;
        //System.out.println("Thread " + me + " leaving...");
        level[me] = 0; //back at start
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {}
    @Override
    public boolean tryLock() { return false;  }
    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException { return false;   }
    @Override
    public Condition newCondition() {  return null;  }
}
