package A2;

import java.util.LinkedList;
import java.util.List;

public class UpDownEdge <T> extends Thread  {
    MapNode up;
    MapNode down;
    String threadName;
    List<Vehicle> vehicles;

    UpDownEdge(MapNode u, MapNode d, String threadName) {
        this.up = u;
        this.down = d;
        this.threadName = threadName;
        vehicles = new LinkedList<>();
    }

    void addVehicle(Vehicle toAdd) {
        if (toAdd.getType() != VehicleType.DOWN && toAdd.getType() != VehicleType.UP)
            System.out.println(this.threadName + ": Trying to add incompatible vehicle type on UP_DOWN edge");
        else
            vehicles.add(toAdd);
    }

    public void run() {
        while (true) {
            for (int i = 0; i < this.vehicles.size(); i++)
                this.up.transferVertical();
        }
    }

}
