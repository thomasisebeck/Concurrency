package Filter;

public class ThreadCounter  extends Thread{
    private Thread t;
    final private String threadName;
    final private Counter c;

    ThreadCounter(Counter count, String tn) {
        this.threadName = tn;
        System.out.println("Creating thread: " + threadName);
        c = count;
    }

    public void run() {
        for (int i = 0; i < 10; i++) {
            c.getAndInc();
            System.out.println(threadName + " incremented to " + c.getValue());
        }
    }

    public void start() {
        System.out.println("Starting thread " + threadName);
        if (t == null) {
            t = new Thread(this, threadName);
            t.start();
        }
    }
}
