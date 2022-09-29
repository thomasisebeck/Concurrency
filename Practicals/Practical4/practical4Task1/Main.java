package practical4Task1;

public class Main {
    public static void main(String args[]){
        VotingStation station = new VotingStation(new MCSQueue());
        Marshal t1 = new Marshal(station);
        Marshal t2 = new Marshal(station);
        Marshal t3 = new Marshal(station);
        Marshal t4 = new Marshal(station);
        Marshal t5 = new Marshal(station);

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
    }
}
