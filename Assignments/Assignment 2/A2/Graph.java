package A2;

import java.util.LinkedList;
import java.util.List;

class Graph<T> {

    List<UpDownEdge> upDownEdges;
    List<LeftRightEdge> leftRightEdges;
    int vehicleCount = 0;

    Graph() {
        upDownEdges = new LinkedList<>();
        leftRightEdges = new LinkedList<>();

        //create a map of 1 intersection
        MapNode<T> middle = new MapNode<>("middle", false);
        MapNode<T> left = new MapNode<>("left", false);
        MapNode<T> leftDest = new MapNode<>("leftDest", true);
        MapNode<T> right = new MapNode<>("right", false);
        MapNode<T> rightDest = new MapNode<>("rightDest", true);
        MapNode<T> up = new MapNode<>("up", false);
        MapNode<T> upDest = new MapNode<>("upDest", true);
        MapNode<T> down = new MapNode<>("down", false);
        MapNode<T> downDest = new MapNode<>("downDest", true);

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
            UpDownEdge down = new UpDownEdge(destination, source, threadName);
            source.down = down;

            //add the vehicles to the edge
            up.addVehicle(new Vehicle(VehicleType.UP, "V-" + vehicleCount++));
            down.addVehicle(new Vehicle(VehicleType.DOWN, "V-" + vehicleCount++));

            //add the edges to the list
            upDownEdges.add(up);
            upDownEdges.add(down);
        }
        else {
            LeftRightEdge left = new LeftRightEdge(source, destination, threadName);
            source.left = left;
            LeftRightEdge right = new LeftRightEdge(source, destination, threadName);
            source.right = right;

            //add the vehicles to the edge
            left.addVehicle(new Vehicle(VehicleType.LEFT, "V-" + vehicleCount++));
            right.addVehicle(new Vehicle(VehicleType.RIGHT, "V-" + vehicleCount++));

            //add edges to the list
            leftRightEdges.add(left);
            leftRightEdges.add(right);
        }



    }


}