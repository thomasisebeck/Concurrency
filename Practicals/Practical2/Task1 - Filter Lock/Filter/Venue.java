package Filter;

public class Venue {
	Filter lock;
	Venue(Filter l) {
		lock = l;
	}
	public void dropOff(int i){
		//lock, start thread
		// try thread.sleep

		System.out.println("BUS " + Thread.currentThread().getName()+ " is waiting to drop off: LOAD " + i);

		try {
			lock.lock();

			System.out.println("BUS " + Thread.currentThread().getName()+ " is dopping off: LOAD " + i);

			Thread.sleep((long) (Math.random() * 800 + 200));

		} catch (InterruptedException e) {

		} finally {
			System.out.println("Thread " + Thread.currentThread().getName()+ " has left: LOAD " + i);
			lock.unlock();
		}

	}
}
