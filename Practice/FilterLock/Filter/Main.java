package Filter;

public class Main {
    public static void main(String[] args) {

        Counter c = new Counter(0, 6);
        Thread t1 = new ThreadCounter(c, "T1");
        Thread t2 = new ThreadCounter(c, "T2");
        Thread t3 = new ThreadCounter(c, "T3");
        Thread t4 = new ThreadCounter(c, "T4");
        Thread t5 = new ThreadCounter(c, "T5");
        Thread t6 = new ThreadCounter(c, "T6");

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
        t6.start();

    }
}
