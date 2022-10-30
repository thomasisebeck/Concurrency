package A2;

public class Main {
    public static void main (String [] args) {

        Graph<String> g = new Graph<>();
        g.startDriving();

        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(2000);
                g.switchStates();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }
}