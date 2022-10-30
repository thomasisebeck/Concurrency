package A2;

public class Main {
    public static void main (String [] args) {

        Graph<String> g = new Graph<>();
        g.startDriving();

        while (true) {
            try {
                Thread.sleep(300);
                g.switchStates();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }
}