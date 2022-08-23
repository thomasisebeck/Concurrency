package SoftwareEngineering;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class Bakery implements Lock {
    private volatile boolean[] flag;
    private volatile int[] label;
    private int numLevels;

    public Bakery(int n) {
        flag = new boolean[n]; //Level of each thread
        label = new int[n]; //Victim on each level
        numLevels = n;
    }
    public int getMax(int[] arr) {
        int max = Integer.MIN_VALUE;

        for (int el : arr){
            if (el > max)
                max = el;
        }

        return max;
    }

    @Override
    public void lock() {

        int me = (int) Thread.currentThread().getId() - 11 - numLevels;

        flag[me] = true;
        label[me] = getMax(label) + 1;

        for (int k = 0; k < numLevels; k++)
            while (  (k != me) && flag[k] &&
                     (label[k] < label[me] || (label[k] == label[me] && k < me))   ) {} //wait

        //System.out.println(Thread.currentThread().getName() + "IN CRIT!!");

    }

    @Override
    public void unlock() {
        int me = (int) Thread.currentThread().getId() - 11 - numLevels;
       // System.out.println(Thread.currentThread().getName() + "LEFT!!");
        flag[me] = false;
    }



    @Override
    public void lockInterruptibly() throws InterruptedException {}
    @Override
    public boolean tryLock() { return false;}
    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException { return false; }
    @Override
    public Condition newCondition() { return null; }

}
