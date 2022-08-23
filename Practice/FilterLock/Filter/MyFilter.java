package Filter;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class MyFilter implements Lock {

    private int[] level;
    private int[] victim;
    private int numLevels;

    public MyFilter(int n) {
        level = new int[n]; //Level of each thread
        victim = new int[n]; //Victim on each level
        numLevels = n;

        for (int i = 0; i < n; i++)
            level[i] = 0;
    }

    @Override
    public void lock() {
        int me = (int) Thread.currentThread().getId() - 11 - numLevels;

        for (int i = 1; i < numLevels; i++) { //attempt to enter level
            level[me] = i;
            victim[i] = me;

            //while ((SUM_OF(k) != me) && (level[k] >= i && victim[i] == me)) {}

            boolean hasConflict = true;

            while (hasConflict) {
                hasConflict = false; // set flag to false

                //check each level
                for (int k = 1; k < numLevels; k++)
                    //I am the victim, and there is a thread on a higher level
                    if (k != me && level[k] >= i && victim[i] == me) {
                        hasConflict = true;
                        break;
                    }
            } //hasConflict

        } //for

    }

    @Override
    public void unlock() {
        int me = (int) Thread.currentThread().getId() - 11 - numLevels;
        level[me] = 0; //back at start
    }

    //Needed to implement lock

    public void lockInterruptibly() throws InterruptedException { throw new UnsupportedOperationException(); }
    public boolean tryLock()
    {
        throw new UnsupportedOperationException();
    }
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {  throw new UnsupportedOperationException(); }
    public Condition newCondition()
    {
        throw new UnsupportedOperationException();
    }
}
