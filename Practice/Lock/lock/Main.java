package lock;

public class Main {
    public static void lock (int turn, int me, boolean busy) {
        do {
            do {
                turn = me;
            } while (busy);
            busy = true;
        } while (turn != me);

        System.out.println("In crit");
    }
    public static void main (String [] args) {
        int turn = 10;
        int me = 2;
        boolean busy = false;

        lock(turn, 1, busy);
        lock(turn, 2, busy);
        lock(turn, 3, busy);
    }
}
