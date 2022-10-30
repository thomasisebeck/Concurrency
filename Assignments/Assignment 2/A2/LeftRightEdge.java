package A2;

import java.util.LinkedList;
import java.util.List;

public class LeftRightEdge extends Thread {
    MapNode left;
    MapNode right;
    String threadName;
    List<Vehicle> vehicles;

    LeftRightEdge(MapNode l, MapNode r, String threadName) {
        this.left = l;
        this.right = r;
        this.threadName = threadName;
        vehicles = new LinkedList<>();
    }

    void addVehicle(Vehicle toAdd) {
        if (toAdd.getType() != VehicleType.LEFT && toAdd.getType() != VehicleType.RIGHT)
            System.out.println(this.threadName + ": Trying to add incompatible vehicle type on LEFT_RIGHT edge");
        else
            vehicles.add(toAdd);
    }

    public void run() {
        while (true) {
            for (int i = 0; i < this.vehicles.size(); i++) {

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                if (this.left != null)
                    this.left.transferHorizontal();
                if (this.right != null)
                    this.right.transferHorizontal();
            }
        }
    }
}
