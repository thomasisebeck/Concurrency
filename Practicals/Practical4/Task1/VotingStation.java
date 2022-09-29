package Task1;

import java.util.concurrent.locks.Lock;

public class VotingStation {
    Lock l;
	public VotingStation(MCSQueue lock) {
		l = lock;
	}

	public void castBallot(){
		l.lock();

		try {
			Thread.sleep(Math.round(Math.random() * 800) + 200);
		} catch (InterruptedException e) {}

		l.unlock();
	}
}
