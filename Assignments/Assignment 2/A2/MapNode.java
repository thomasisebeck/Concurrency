package A2;

import java.util.concurrent.locks.ReentrantLock;

public class MapNode <T> {
    private final Bakery verticalLock;
    private final Bakery horizontalLock;


    private final ReentrantLock transferLock;
    public boolean isDestination;
    String nodeName;
    UpDownEdge up;
    UpDownEdge down;
    LeftRightEdge left;
    LeftRightEdge right;

    MapNode(String nodeName, boolean isDestination) {
        verticalLock = new Bakery(2);
        verticalLock.lock();
        horizontalLock = new Bakery(2);
        transferLock = new ReentrantLock();
        this.nodeName = nodeName;
        this.isDestination = isDestination;
    }

    public void sleepThread(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean checkIfAtDest(Vehicle v, String direction, String destination) {
        if (v.crossedIntersections.getAndIncrement() == 2){
            System.out.println(v.getName() + " reached destination at the " + destination);
            return true;
        }

        System.out.println(v.getName() + " moved " + direction + " passed intersection " + v.crossedIntersections.get() + " of 2");
        return false;

    }

    public boolean transferVertical() {
        if (!horizontalLock.isLocked()) {
            try {
                transferLock.lock();

                if (up != null && !up.vehicles.isEmpty() && up.vehicles.get(0).getType() == VehicleType.DOWN) { //transfer down

                    sleepThread(250);

                    if (!horizontalLock.isLocked()) {
                        Vehicle v = up.vehicles.remove(0);
                        if (!checkIfAtDest(v, "down", "bottom"))
                            down.vehicles.add(v);
                    }
                }
                if (down != null && !down.vehicles.isEmpty() && down.vehicles.get(0).getType() == VehicleType.UP) {

                    sleepThread(250);

                    if (!horizontalLock.isLocked()) {
                        Vehicle v = down.vehicles.remove(0);
                        if (!checkIfAtDest(v, "up", "top"))
                            up.vehicles.add(v);
                    }
                }
            } finally {
                transferLock.unlock();
            }
            return true;
        }
        return false;
    }

    public boolean transferHorizontal() {
        if (!verticalLock.isLocked()) {
            try {
                transferLock.lock();

                if (left != null && !left.vehicles.isEmpty() && left.vehicles.get(0).getType() == VehicleType.RIGHT) { //transfer down

                    sleepThread(250);

                    if (!verticalLock.isLocked()) {
                        Vehicle v = left.vehicles.remove(0);
                        if (!checkIfAtDest(v, "right", "right"))
                            right.vehicles.add(v);
                    }
                }
                if (right != null && !right.vehicles.isEmpty() && right.vehicles.get(0).getType() == VehicleType.LEFT) {

                    sleepThread(250);

                    if (!verticalLock.isLocked()) {
                        Vehicle v = right.vehicles.remove(0);
                        if (!checkIfAtDest(v, "left", "left"))
                            left.vehicles.add(v);
                    }
                }
            } finally {
                transferLock.unlock();
            }

            return true;
        }

        return false;
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
