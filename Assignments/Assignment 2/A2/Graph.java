package A2;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

class Graph<T> {

    List<Edge> edges;
    List<MapNode> nodes;
    int vehicleCount;
    AtomicInteger numVehiclesAtDest;
    AtomicBoolean printed;

    Graph() {
        edges = new LinkedList<>();
        nodes = new LinkedList<>();
        vehicleCount = 0;
        numVehiclesAtDest = new AtomicInteger(0);
        printed = new AtomicBoolean(false);
        final int TOTAL_VEHICLES = 32;

        //total vehicles = num_edges (8) * vehicle per edge (2)

        MapNode TL = new MapNode("TL", numVehiclesAtDest, TOTAL_VEHICLES);
        nodes.add(TL);
        MapNode TR = new MapNode("TR", numVehiclesAtDest,  TOTAL_VEHICLES);
        nodes.add(TR);
        MapNode BL = new MapNode("BL", numVehiclesAtDest, TOTAL_VEHICLES);
        nodes.add(BL);
        MapNode BR = new MapNode("BR", numVehiclesAtDest, TOTAL_VEHICLES);
        nodes.add(BR);

        //---------- MIDDLE SQUARE ----------//

        Edge MUU = new Edge("MUU", TR, TL);
        Edge MUD = new Edge("MUD", TL, TR);
        TL.right = MUD;
        TR.left = MUU;
        edges.add(MUU);
        edges.add(MUD);

        Edge MLL = new Edge("MLL", TL, BL);
        Edge MLR = new Edge("MLR", BL, TL);
        TL.down = MLL;
        BL.up = MLR;
        edges.add(MLL);
        edges.add(MLR);

        Edge MDD = new Edge("MDD", BL, BR);
        Edge MDU = new Edge("MDU", BR, BL);
        BL.right = MDD;
        BR.left = MDU;
        edges.add(MDD);
        edges.add(MDU);

        Edge MRR = new Edge("MRR", BR, TR);
        Edge MRL = new Edge("MRL", TR, BR);
        BR.up = MRR;
        TR.down = MRL;
        edges.add(MRR);
        edges.add(MRL);

        //-----------------------------------//

        int vehiclesAdded = 0;

        //add vehicles to each edge
        for (Edge e: edges) {
            Vehicle vehicle = new Vehicle("v-" + vehiclesAdded++, generateRoute());
            e.addVehicle(vehicle);
            Vehicle vehicle2 = new Vehicle("v-" + vehiclesAdded++, generateRoute());
            e.addVehicle(vehicle2);
        }



    }

    public ArrayList<Directions> generateRoute() {
        ArrayList<Directions> route = new ArrayList<>();

        //take 3 turns each
        int random = 3;

        for (int i = 0; i < random; i++) {
            //add a random direction
            int direction = new Random().nextInt(4);
            switch(direction) {
                case 0:
                    route.add(Directions.UP);
                    break;
                case 1:
                    route.add(Directions.DOWN);
                    break;
                case 2:
                    route.add(Directions.LEFT);
                    break;
                case 3:
                    route.add(Directions.RIGHT);
            }
        }

        return route;
    }


    public void switchStates() {
        //go through all nodes and switch their states...
        for (MapNode n : nodes) {
            n.switchStates();
        }
    }

    public void startDriving() {
        for (Edge e : edges)
            e.start();
    }

}