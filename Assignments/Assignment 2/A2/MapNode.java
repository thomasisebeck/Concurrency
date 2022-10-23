package A2;

import java.util.concurrent.locks.ReentrantLock;

public class MapNode <T> {
    private final ReentrantLock verticalLock;
    private final ReentrantLock horizontalLock;
    public boolean isDestination;
    String nodeName;
    UpDownEdge up;
    UpDownEdge down;
    LeftRightEdge left;
    LeftRightEdge right;

    MapNode(String nodeName, boolean isDestination) {
        verticalLock = new ReentrantLock();
        horizontalLock = new ReentrantLock();
        this.nodeName = nodeName;
        this.isDestination = isDestination;
    }

    public boolean transferVertical() {
        if (!horizontalLock.isLocked()) {
            if (!left.vehicles.isEmpty() && up.vehicles.get(0).getType() == VehicleType.DOWN) //transfer down
                down.vehicles.add(up.vehicles.remove(0));
            else if (!down.vehicles.isEmpty() && down.vehicles.get(0).getType() == VehicleType.UP)
                up.vehicles.add(down.vehicles.remove(0));
            return true;
        }
        return false;
    }

    public boolean transferHorizontal() {
        if (!verticalLock.isLocked()) {
            if (!up.vehicles.isEmpty() && up.vehicles.get(0).getType() == VehicleType.DOWN) //transfer down
                down.vehicles.add(up.vehicles.remove(0));
            else if (!down.vehicles.isEmpty() && down.vehicles.get(0).getType() == VehicleType.UP)
                up.vehicles.add(down.vehicles.remove(0));
            return true;
        }
        return false;
    }

    public void lockHorizontalLock() {
        synchronized (this) {
            horizontalLock.lock();
            verticalLock.unlock();
        }
    }
    public void lockVerticalLock() {
        synchronized (this) {
            verticalLock.lock();
            horizontalLock.unlock();
        }
    }

}
