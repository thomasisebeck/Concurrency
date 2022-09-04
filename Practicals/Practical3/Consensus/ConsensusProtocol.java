package Consensus;

public abstract class ConsensusProtocol<T> implements Consensus<T>
{
	public static volatile Object[] proposed;

	public ConsensusProtocol(int threadCount)	{
		proposed = new Object[threadCount];
	}

	public void propose(T value){
		//set the value of the current thread
		proposed[(int) Thread.currentThread().getId() - 16] = value;
	}

	abstract public void decide(); //concrete class decides

}
