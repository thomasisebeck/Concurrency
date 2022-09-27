package practical4;

import java.util.Random;

public class TestThread extends Thread {
    private Thread t;
    final private MCSQueue lock;

    TestThread(MCSQueue l) {
        lock = l;
    }

    public void run() {
        for (int i = 0; i < 5; i++) {
            lock.lock();
            System.out.println("Thread " + this.getName() + " is now in the crit section");

            try {
                Thread.sleep(Math.round(Math.random() * 1000));
            } catch (InterruptedException e) {}

            System.out.println("Thread " + Thread.currentThread().getName() + " has left");
            lock.unlock();

            try {
                Thread.sleep(Math.round(Math.random() * 10));
            } catch (InterruptedException e) {}
        }
    }
}

/*



    public void run() {
        for (int i = 0; i < 5; i++) {
            String item = s.getNextItem();
            System.out.println(threadName + " Task: " + item);
            s.completeItem(item);
        }
    }

    public void start() {
        System.out.println("Starting thread " + threadName);
        if (t == null) {
            t = new Thread(this);
            t.start();
        }
    }
}


 */
