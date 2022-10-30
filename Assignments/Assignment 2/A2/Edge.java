package A2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class Edge extends Thread {
    public String threadName;
    public List<Vehicle> vehicles;
    public MapNode first;
    public MapNode second;
    private ReentrantLock printLock;
    Edge(String threadName, MapNode first, MapNode second) {
        vehicles = new ArrayList<>();
        this.threadName = threadName;
        this.first = first;
        this.second = second;
        printLock = new ReentrantLock();
    }
    public void addVehicle(Vehicle toAdd) {
        vehicles.add(toAdd);
    }

    private void printList() {

        String toPrint = "";
        boolean mustPrint = false;
        toPrint = "ROAD " + threadName + ": ";

        for (int i = 0; i < vehicles.size(); i++) {
            mustPrint = true;
            toPrint = toPrint + vehicles.get(i).getName();
            if (i != vehicles.size() - 1 && vehicles.size() != 1)
                toPrint += ", ";
        }

        if (mustPrint)
            System.out.println(toPrint);
    }

    public void run() {
        while (true) {
            for (int i = 0; i < this.vehicles.size(); i++) {
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }

                if (this.first != null) {
                    this.first.transfer();
                    printLock.lock();
                    printList();
                    printLock.unlock();
                }
                if (this.second != null) {
                    this.second.transfer();
                    printLock.lock();
                    printList();
                    printLock.unlock();
                }
            }
        }
    }
}
