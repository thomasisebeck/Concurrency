package Filter;

import javax.sound.sampled.FloatControl;

public class Venue {
	Filter lock;
	Venue(Filter l) {
		lock = l;
	}
	public void dropOff(int i){
		//lock, start thread
		// try thread.sleep

		try {
			lock.lock();

			Thread.sleep(100);

		} catch (InterruptedException e) {

		} finally {
			lock.unlock();
		}

	}
}
