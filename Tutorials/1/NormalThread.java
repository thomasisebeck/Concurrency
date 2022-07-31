

//extend from the thread class
public class NormalThread extends Thread {
    private Thread t;
    private String threadName;

    NormalThread(String threadName) {
        this.threadName = threadName;
        System.out.println("Creating thread: " + threadName);
    }

    public void run() {
        System.out.println("Running thread " + threadName);
        try {
            for (int i = 4; i > 0; i--) {
                System.out.println("Thread: " + threadName + ", " + i);

                //NB: don't use t.sleep, use thread.sleep
                Thread.sleep(50);
            }
        }
        catch (InterruptedException e) { //Need braces
            System.out.println("Thread " + threadName + " interrupted...");
        }
        System.out.println("Thread " + threadName + " exiting...");
    }

    public void start() {
        System.out.println("Starting thread " + threadName);
        if (t == null) {
            t = new Thread(this, threadName);
            t.start();
        }
    }


}
