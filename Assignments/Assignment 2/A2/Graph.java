package A2;

import java.util.LinkedList;
import java.util.List;

class Graph<T> {

    List<UpDownEdge> upDownEdges;
    List<LeftRightEdge> leftRightEdges;
    List<MapNode<T>> nodes;
    int vehicleCount = 0;

    Graph() {
        upDownEdges = new LinkedList<>();
        leftRightEdges = new LinkedList<>();
        nodes = new LinkedList<>();

        //create a map of 1 intersection
        MapNode<T> middle = new MapNode<>("middle", false);
        nodes.add(middle);
        MapNode<T> left = new MapNode<>("left", false);
        nodes.add(left);
        MapNode<T> leftDest = new MapNode<>("leftDest", true);
        nodes.add(leftDest);
        MapNode<T> right = new MapNode<>("right", false);
        nodes.add(right);
        MapNode<T> rightDest = new MapNode<>("rightDest", true);
        nodes.add(rightDest);
        MapNode<T> up = new MapNode<>("up", false);
        nodes.add(up);
        MapNode<T> upDest = new MapNode<>("upDest", true);
        nodes.add(upDest);
        MapNode<T> down = new MapNode<>("down", false);
        nodes.add(down);
        MapNode<T> downDest = new MapNode<>("downDest", true);
        nodes.add(downDest);

        //connect the middle 5 nodes
        addNewEdge(middle, left, false, "T-Middle-Left");
        addNewEdge(middle, right, false, "T-Middle-Right");
        addNewEdge(middle, up, true, "T-Middle-Up");
        addNewEdge(middle, down, true, "T-Middle-Down");

        //connect the outer 4 nodes
        addNewEdge(left, leftDest, false, "T-Left-Dest");
        addNewEdge(right, rightDest, false, "T-Right-Dest");
        addNewEdge(up, upDest, true, "T-Up-Dest");
        addNewEdge(down, downDest, true, "T-Down-Dest");
    }

    public void addNewEdge(MapNode source, MapNode destination, boolean upDown, String threadName) {

        if (upDown) {
            UpDownEdge up = new UpDownEdge(source, destination, threadName);
            source.up = up;
            destination.down = up;
            UpDownEdge down = new UpDownEdge(destination, source, threadName);
            source.down = down;
            destination.up = down;

            //add the vehicles to the edge
            up.addVehicle(new Vehicle(VehicleType.DOWN, "DV-" + vehicleCount++));
            down.addVehicle(new Vehicle(VehicleType.UP, "UV-" + vehicleCount++));

            //add the edges to the list
            upDownEdges.add(up);
            upDownEdges.add(down);
        }
        else {
            LeftRightEdge left = new LeftRightEdge(source, destination, threadName);
            source.left = left;
            destination.right = left;
            LeftRightEdge right = new LeftRightEdge(source, destination, threadName);
            source.right = right;
            destination.left = right;

            //add the vehicles to the edge
            left.addVehicle(new Vehicle(VehicleType.LEFT, "LV-" + vehicleCount++));
            right.addVehicle(new Vehicle(VehicleType.RIGHT, "RV-" + vehicleCount++));

            //add edges to the list
            leftRightEdges.add(left);
            leftRightEdges.add(right);
        }

    }

    public void switchStates() {
        synchronized (this) {
            //go through all nodes and switch their states...
            for (MapNode n : nodes) {
                n.switchStates();
            }
            System.out.println("Switched states...");
        }
    }

    public void startDriving() {
        for (UpDownEdge e : upDownEdges) {
            e.start();
        }
        for (LeftRightEdge e: leftRightEdges) {
            e.start();
        }
    }

}