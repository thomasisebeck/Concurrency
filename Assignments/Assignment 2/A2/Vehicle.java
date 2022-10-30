package A2;

import java.util.concurrent.atomic.AtomicInteger;

public class Vehicle {
    private final VehicleType type;
    private final String name;
    public AtomicInteger crossedIntersections;

    Vehicle(VehicleType type, String name) {
        this.type = type;
        this.name = name;
        crossedIntersections = new AtomicInteger(0);
    }

    public String getName() {
        return this.name;
    }

    public VehicleType getType() {
        return this.type;
    }
}
