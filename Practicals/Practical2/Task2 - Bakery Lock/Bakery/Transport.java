package Bakery;

import com.company.ScrumThread;

public class Transport extends Thread {
    Venue destination;

	public Transport(Venue dest){
		destination = dest;
	}

	@Override
	public void run()
	{
		//drop off 5 busses
		//cals destination.dropoff() 5 times

		for (int i = 0; i < 5; i++)
			destination.dropOff(i);

	}
}
