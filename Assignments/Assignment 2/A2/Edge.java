package A2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class Edge extends Thread {
    public String threadName;
    public BoundedQueue<Vehicle> vehicleBoundedQueue;
    public MapNode first;
    public MapNode second;
    private ReentrantLock printLock;
    Edge(String threadName, MapNode first, MapNode second) {
        vehicleBoundedQueue = new BoundedQueue<>(5);
        this.threadName = threadName;
        this.first = first;
        this.second = second;
        printLock = new ReentrantLock();
    }
    public void addVehicle(Vehicle toAdd) {
        try {
            vehicleBoundedQueue.enq(toAdd);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void run() {
        while (true) {
            for (int i = 0; i < this.vehicleBoundedQueue.getSize(); i++) {
                if (this.first != null)
                    this.first.transfer();
                if (this.second != null)
                    this.second.transfer();
            }
        }
    }
}
