package Consensus;

//pure virtual interface
public interface Consensus<T>
{
	void decide();
	void propose(T value);
}
