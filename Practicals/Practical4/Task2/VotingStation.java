package Task2;

import java.util.concurrent.locks.Lock;

public class VotingStation {
    Lock l;
	public VotingStation(Timeout lock) {
		l = lock;
	}

	public void castBallot() {

		boolean casted = false;

		while (!casted){
			if (l.tryLock()) {
				casted = true;
				try {
					Thread.sleep(Math.round(Math.random() * 800) + 200);
				} catch (InterruptedException e) {
				} finally {
					l.unlock();
				}
			}
		}

	}
}
