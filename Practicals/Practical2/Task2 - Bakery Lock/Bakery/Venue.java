package Bakery;

public class Venue {
	Bakery lock;
	Venue(Bakery l) {
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
			System.out.println("BUS " + Thread.currentThread().getName()+ " has left: LOAD " + i);
			lock.unlock();
		}

	}
}
