import java.util.Random;

public class Main {

    public static void main(String[] args) throws InterruptedException {

       /* NormalThread t1 = new NormalThread("Thread 1");
        NormalThread t2 = new NormalThread("Thread 2");

        t1.start();
        t1.run();
        t2.start();
        t2.run();*/

        int number = 0;

        RunnableThread run1 = new RunnableThread("Run 1");
        Thread t1 = new Thread(run1);
        t1.start();
        RunnableThread run2 = new RunnableThread("Run 2");
        Thread t2 = new Thread(run2);
        t2.start();

        System.out.println("Num: " + number);

    }
}
