package Filter;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

// Name: Thomas Isebeck
// Student Number: 20446332

public class Filter implements Lock {

	private AtomicInteger[] level;
	private AtomicInteger[] victim;
	private int numLevels;

	public Filter(int n) {
		level = new AtomicInteger[n]; //Level of each thread
		victim = new AtomicInteger[n]; //Victim on each level
		numLevels = n;

		//create atomic integers for level and victim
		for (int i = 0; i < n; i++) {
			level[i] = new AtomicInteger();
			victim[i] = new AtomicInteger();
		}
	}

	// Inital order must repeat!!!!
	// Queue up in order and go over and over!!!

	@Override
	public void lock() {

		int me = (int) Thread.currentThread().getId() - 11 - numLevels;

		System.out.println("Thread " + Thread.currentThread().getName()+ " waiting...");

		//starts at 1: only check after base level!!!!
		for (int i = 1; i < numLevels; i++) { //attempt to enter level
			level[me].set(i);
			victim[i].set(me);

			//check each level
			for (int k = 0; k < numLevels; k++) {
				//I am the victim, and there is a thread on a higher level
				while (k != me && level[k].get() >= i &&
						victim[i].get() == me) {} //wait
			} //check each level
		} //attempt to enter level

		System.out.println("Thread " + me + " in crit section!!!");

	}

	public void unlock() {
		int me = (int) Thread.currentThread().getId() - 11 - numLevels;
		System.out.println("Thread " + me + " leaving...");
		level[me].set(0); //back at start
	}

	//Needed to implement lock
	public void lockInterruptibly() throws InterruptedException { throw new UnsupportedOperationException();}
	public boolean tryLock() {throw new UnsupportedOperationException();}
	public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {throw new UnsupportedOperationException();}
	public Condition newCondition() { throw new UnsupportedOperationException();}

}
