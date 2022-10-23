package A2;

public class Vehicle {
    private final VehicleType type;
    private final String name;

    Vehicle(VehicleType type, String name) {
        this.type = type;
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public VehicleType getType() {
        return this.type;
    }
}
