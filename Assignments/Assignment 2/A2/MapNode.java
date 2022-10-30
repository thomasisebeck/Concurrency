package A2;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class MapNode {
    private final Bakery verticalLock;
    private final Bakery horizontalLock;

    private final ReentrantLock transferLock;
    String nodeName;
    Edge up;
    Edge down;
    Edge left;
    Edge right;
    AtomicInteger vehiclesAtDestination;
    AtomicBoolean printed;
    int TOTAL_VEHICLES;
    int VEHICLE_SLEEP_TIME;

    MapNode(String nodeName, AtomicInteger vehiclesAtDestination, AtomicBoolean printed, int TOTAL_VEHICLES) {
        verticalLock = new Bakery(2);
        verticalLock.lock();
        horizontalLock = new Bakery(2);
        transferLock = new ReentrantLock();
        this.nodeName = nodeName;
        this.TOTAL_VEHICLES = TOTAL_VEHICLES;
        this.VEHICLE_SLEEP_TIME = 50;
        this.printed = printed;
        this.vehiclesAtDestination = vehiclesAtDestination;
    }

    public void sleepThread(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void directVehicle(Vehicle v) {

        if (!v.route.isEmpty()) {

            Directions nexTurn = v.route.remove(0);

            boolean turned = false;

            //direct the vehicle to the next node in its route
            switch (nexTurn) {
                case UP:
                    if (this.up != null) {
                        System.out.println(v.getName() + " went up.");
                        up.vehicles.add(v);
                        turned = true;
                    }
                    break;
                case DOWN:
                    if (this.down != null) {
                        System.out.println(v.getName() + " went down.");
                        down.vehicles.add(v);
                        turned = true;
                    }
                    break;
                case LEFT:
                    if (this.left != null) {
                        System.out.println(v.getName() + " went left.");
                        left.vehicles.add(v);
                        turned = true;
                    }
                    break;
                case RIGHT:
                    if (this.right != null) {
                        System.out.println(v.getName() + " went right.");
                        right.vehicles.add(v);
                        turned = true;
                    }

            } // switch

            if (!turned) { //change the route if car could not take path!
                //get a random number
                int direction = new Random().nextInt(4);
                switch (direction) {
                    case 0:
                        v.route.add(0, Directions.UP);
                        break;
                    case 1:
                        v.route.add(0, Directions.DOWN);
                        break;
                    case 2:
                        v.route.add(0, Directions.LEFT);
                        break;
                    case 3:
                        v.route.add(0, Directions.RIGHT);
                }
            }
        }

        if (v.route.isEmpty()) {
            vehiclesAtDestination.getAndIncrement();
            if (vehiclesAtDestination.get() == TOTAL_VEHICLES)
                System.out.println("ALL VEHICLES REACHED THIER DESTINATION");
            else
                System.out.println(v.getName() + " reached its destination (" + vehiclesAtDestination.get() + " of " + TOTAL_VEHICLES + ")");
        }
    }

    public void transfer() {

        if (up != null && !up.vehicles.isEmpty()) { //transfer from above
            //pop the next turn-off of the list
            if (!horizontalLock.isLocked()) {
                Vehicle v;
                transferLock.lock();
                if (!up.vehicles.isEmpty()) {
                    v = up.vehicles.remove(0);
                    sleepThread(VEHICLE_SLEEP_TIME);
                    directVehicle(v);
                }
                transferLock.unlock();
            }
        }

        if (down != null && !down.vehicles.isEmpty()) { //transfer from below

            //pop the next turn-off of the list
            if (!horizontalLock.isLocked()) {
                Vehicle v;
                transferLock.lock();
                if (!down.vehicles.isEmpty()) {
                    v = down.vehicles.remove(0);
                    sleepThread(VEHICLE_SLEEP_TIME);
                    directVehicle(v);
                }
                transferLock.unlock();
            }
        }

        if (left != null && !left.vehicles.isEmpty()) { //transfer from above

            //pop the next turn-off of the list
            if (!verticalLock.isLocked()) {
                Vehicle v;
                transferLock.lock();
                if (!left.vehicles.isEmpty()) {
                    v = left.vehicles.remove(0);
                    sleepThread(VEHICLE_SLEEP_TIME);
                    directVehicle(v);
                }
                transferLock.unlock();
            }
        }

        if (right != null && !right.vehicles.isEmpty()) { //transfer from below

            //pop the next turn-off of the list
            if (!verticalLock.isLocked()) {
                Vehicle v;
                transferLock.lock();
                if (!right.vehicles.isEmpty()) {
                    v = right.vehicles.remove(0);
                    sleepThread(VEHICLE_SLEEP_TIME);
                    directVehicle(v);
                }
                transferLock.unlock();
            }
        }

    }

    void switchStates() {
        synchronized (this) {
            if (horizontalLock.isLocked()) { //lock the vertical lock
                verticalLock.lock();
                horizontalLock.unlock();
            }
            else { //lock the horizontal lock
                horizontalLock.lock();
                verticalLock.unlock();
            }
        }
    }

}
