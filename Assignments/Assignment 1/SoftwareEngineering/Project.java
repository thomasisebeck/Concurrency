package SoftwareEngineering;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

public class Project
{
	public volatile Queue<Component> develop = new LinkedList<>(),
			                          testing = new LinkedList<>();
	private volatile int numTested;
	private volatile int numDeveloped;

	private volatile boolean printed;

	private int numComponents;

	Bakery testLock;
	Bakery devLock;
	
	public Project(){
		//each component has a developTime and a testTime
		//After the developTime depletes, it is added to the test queue

		develop.add(new Component('s', "Calculator")); //yes
		develop.add(new Component('m', "Calendar")); //yes
		develop.add(new Component('s', "Billing")); //yes
		develop.add(new Component('l', "Timetable")); //yes
		develop.add(new Component('m', "Phonebook")); //yes
		develop.add(new Component('l', "User Manager")); //yes
		develop.add(new Component('s', "Api")); //yes

		testLock = new Bakery(5); //5 testers
		devLock = new Bakery(5); //5 developers

		numTested = 0;
		numDeveloped = 0;
		printed = false;
		numComponents = develop.size();
	}

	public int getNumComponents() {
		return numComponents;
	}

	public Component getComponentFromDevelop() {
		devLock.lock();
		Component comp = null;
		try {
			comp = develop.remove();
		} catch (NoSuchElementException e) {
			return null;
		}finally {
			devLock.unlock();
		}
		return comp;
	}

	public void subtractDevopTime(Component component, int time) {
		devLock.lock();
		component.developTime -= time;
		if (component.developTime < 0)
			numDeveloped++;
		devLock.unlock();
	}

	public void subtractTestTime(Component component, int time) {
		testLock.lock();
		component.testTime -= time;
		if (component.testTime < 0)
			numTested++;
		testLock.unlock();
	}

	public int getNumTested() {
		return numTested;
	}

	public int getNumDeveloped() {
		return numDeveloped;
	}

	public boolean testEmpty() {
		return testing.isEmpty();
	}

	public boolean devEmpty() { return develop.isEmpty(); }

	public Component getComponentFromTest() {
		testLock.lock();
		Component comp = null;
		try {
			comp = testing.remove();
		}
		catch (NoSuchElementException e) {
			//returns null
		} finally {
			testLock.unlock();
		}
		return comp;
	}

	public void addComponentToTest(Component c) {
		try {
			testLock.lock();
			testing.add(c);
		} finally {
			testLock.unlock();
		}
	}

	public void addComponentToDevelop(Component c) {
		try {
			devLock.lock();
			develop.add(c);
		} finally {
			devLock.unlock();
		}
	}

	public void printResults() {
		if (!printed) { //remove overhead of aquiring the lock
			try {
				devLock.lock();
				testLock.lock();
				if (!printed) {
					printed = true;
					System.out.println("============================");
					System.out.println("Components tested:       " + numTested);
					System.out.println("Components developed:    " + numDeveloped);
					System.out.println("============================");
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			} finally {
				devLock.unlock();
				testLock.unlock();
			}
		}
	}

}
