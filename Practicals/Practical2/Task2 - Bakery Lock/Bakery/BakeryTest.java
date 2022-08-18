package Bakery;

public class BakeryTest {
    public static void main(String[] args) {

        Transport[] buses = new Transport[5];

        Bakery bakery = new Bakery(5);

        Venue destination = new Venue(bakery);

        for(int i = 0; i < 5; i++)
            buses[i] = new Transport(destination);

        for(Transport bus : buses)
            bus.start();

        //******** LAST PRAC FEEDBACK *********//
        // - Didn't get thread id
        // - Volatile variables
        // - Alternating threads (1,2,1,2)
        //*************************************//

    }
}
