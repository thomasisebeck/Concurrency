package A2;

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
    int TOTAL_VEHICLES;
    int VEHICLE_SLEEP_TIME;

    MapNode(String nodeName, AtomicInteger vehiclesAtDestination, int TOTAL_VEHICLES) {
        verticalLock = new Bakery(2);
        verticalLock.lock();
        horizontalLock = new Bakery(2);
        transferLock = new ReentrantLock();
        this.nodeName = nodeName;
        this.TOTAL_VEHICLES = TOTAL_VEHICLES;
        this.VEHICLE_SLEEP_TIME = 50;
        this.vehiclesAtDestination = vehiclesAtDestination;
    }

    public void sleepThread(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void sleepAndPrint(BoundedQueue<Vehicle> toPrint, boolean isEntering) {
        try {
            if (isEntering)
                Thread.sleep(200);
            toPrint.print(isEntering);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void directVehicle(Vehicle v) {

        if (!v.route.isEmpty()) {

            boolean turned = false;

            while (!turned) {

                Directions nextTurn = v.route.remove(0);

                //direct the vehicle to the next node in its route
                switch (nextTurn) {
                    case UP:
                        if (this.up != null) {
                            try {
                                synchronized (this) {
                                    sleepAndPrint(up.vehicleBoundedQueue, true);
                                    up.vehicleBoundedQueue.enq(v);
                                    sleepAndPrint(up.vehicleBoundedQueue, false);
                                }
                                turned = true;
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        } else
                            v.route.add(Directions.DOWN);

                        break;
                    case DOWN:
                        if (this.down != null) {
                            sleepThread(200);
                            try {
                                synchronized (this) {
                                    sleepAndPrint(down.vehicleBoundedQueue, true);
                                    down.vehicleBoundedQueue.enq(v);
                                    sleepAndPrint(down.vehicleBoundedQueue, false);
                                }
                                turned = true;
                            } catch (InterruptedException e) {
                                throw  new RuntimeException(e);
                            }
                            turned = true;
                        } else
                            v.route.add(Directions.UP);
                        break;
                    case LEFT:
                        if (this.left != null) {
                            sleepThread(200);
                            try {
                                synchronized (this) {
                                    sleepAndPrint(left.vehicleBoundedQueue, true);
                                    left.vehicleBoundedQueue.enq(v);
                                    sleepAndPrint(left.vehicleBoundedQueue, false);
                                }
                                turned = true;
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        } else
                            v.route.add(Directions.RIGHT);
                        break;
                    case RIGHT:
                        if (this.right != null) {
                            sleepThread(200);
                            try {
                                synchronized (this) {
                                    sleepAndPrint(right.vehicleBoundedQueue, true);
                                    right.vehicleBoundedQueue.enq(v);
                                    sleepAndPrint(right.vehicleBoundedQueue, false);
                                }
                                turned = true;
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        } else
                            v.route.add(Directions.LEFT);

                } // switch

            }

        }

        if (v.route.isEmpty()) {
            vehiclesAtDestination.getAndIncrement();
            System.out.println(v.getName() + " reached its destination (" + vehiclesAtDestination.get() + " of " + TOTAL_VEHICLES + ")");
            if (vehiclesAtDestination.get() == TOTAL_VEHICLES) {
                sleepThread(500);
                System.out.println("ALL VEHICLES REACHED THEIR DESTINATION");
            }
        }
    }

    public boolean transfer() {

        if (up != null && !(up.vehicleBoundedQueue.isEmpty())) { //transfer from above
            //pop the next turn-off of the list
            if (!horizontalLock.isLocked()) {
                Vehicle v;
                try {
                    transferLock.lock();
                    if (!up.vehicleBoundedQueue.isEmpty()) {
                        try {
                            v = up.vehicleBoundedQueue.deq();
                            sleepThread(VEHICLE_SLEEP_TIME);
                            directVehicle(v);
                            return true;
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                } finally {
                    transferLock.unlock();
                }
            }
        }

        if (down != null && !down.vehicleBoundedQueue.isEmpty()) { //transfer from below

            //pop the next turn-off of the list
            if (!horizontalLock.isLocked()) {
                Vehicle v;
                try {
                    transferLock.lock();
                    if (!down.vehicleBoundedQueue.isEmpty()) {
                        try {
                            v = down.vehicleBoundedQueue.deq();
                            sleepThread(VEHICLE_SLEEP_TIME);
                            directVehicle(v);
                            return true;
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                } finally {
                    transferLock.unlock();
                }

            }
        }

        if (left != null && !left.vehicleBoundedQueue.isEmpty()) { //transfer from above

            //pop the next turn-off of the list
            if (!verticalLock.isLocked()) {
                Vehicle v;
                try {
                    transferLock.lock();
                    if (!left.vehicleBoundedQueue.isEmpty()) {
                        try {
                            v = left.vehicleBoundedQueue.deq();
                            sleepThread(VEHICLE_SLEEP_TIME);
                            directVehicle(v);
                            return true;
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                } finally {
                    transferLock.unlock();
                }
            }
        }

        if (right != null && !right.vehicleBoundedQueue.isEmpty()) { //transfer from below

            //pop the next turn-off of the list
            if (!verticalLock.isLocked()) {
                Vehicle v;
                try {
                    transferLock.lock();
                    if (!right.vehicleBoundedQueue.isEmpty()) {
                        try {
                            v = right.vehicleBoundedQueue.deq();
                            sleepThread(VEHICLE_SLEEP_TIME);
                            directVehicle(v);
                            return true;
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                } finally {
                    transferLock.unlock();
                }
            }
        }

        return false;

    }

    void switchStates() {
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
