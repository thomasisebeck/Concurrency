package A2;

import java.util.ArrayList;

public class Vehicle {
    private final String name;
    public ArrayList<Directions> route;

    Vehicle(String name, ArrayList<Directions> route) {
        this.name = name;
        this.route = route;
    }

    public String getName() {
        return this.name;
    }

}
