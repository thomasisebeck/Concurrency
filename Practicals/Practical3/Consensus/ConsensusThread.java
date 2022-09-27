package Consensus;

import java.util.Random;

public class ConsensusThread extends Thread  {

	private Consensus<Integer> consensus;

	ConsensusThread(Consensus<Integer> consensusObject)
	{
		consensus = consensusObject;
	}

	public void run() {

		for (int i = 0; i < 5; i++) {
			Random rand = new Random();
			int value = rand.nextInt(100) + 100;

			try {
				Thread.sleep(rand.nextInt(50) + 50);
			} catch (InterruptedException e) {
			}

			System.out.println(Thread.currentThread().getName() + " proposing " + value);
			consensus.propose(value); //random from 100 to 200
			try {
				Thread.sleep(rand.nextInt(50) + 50); //sleep from 50 to 100
			} catch (InterruptedException e) {
			}
			consensus.decide();
		}
	}
}
