package SoftwareEngineering;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

public class Project
{
	private volatile Queue<Component> develop = new LinkedList<>(),
			                          testing = new LinkedList<>();
	Bakery testLock;
	Bakery devLock;
	
	public Project(){
		//each component has a developTime and a testTime
		//After the developTime depletes, it is added to the test queue

		develop.add(new Component('s', "Calculator")); //done, done
		develop.add(new Component('m', "Calendar")); //done, done
		develop.add(new Component('s', "Billing")); //done, done
		develop.add(new Component('l', "Timetable")); //done, done
		develop.add(new Component('m', "Phonebook")); //done, done
		develop.add(new Component('l', "User Manager")); //done, done
		develop.add(new Component('s', "Api")); //done, done

		testLock = new Bakery(5); //5 testers
		devLock = new Bakery(5); //5 developers
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
		devLock.unlock();
	}

	public void subtractTestTime(Component component, int time) {
		testLock.lock();
		component.testTime -= time;
		testLock.unlock();
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

}
