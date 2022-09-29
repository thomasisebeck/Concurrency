package Task1;

public class Marshal extends Thread {

    private VotingStation vs;

	Marshal(VotingStation _vs)
	{
		vs = _vs ;
	}

	@Override
	public void run()
	{
		for (int i = 0; i < 5; i++)
			vs.castBallot();
	}
}
