package Task1;

import javax.swing.plaf.TableHeaderUI;

public class EnqueueThread extends Thread {
    BoundedQueue myQueue;
    public EnqueueThread(BoundedQueue Q) {
        myQueue = Q;
    }
    public void run() {
        while (true) {
            try {
                Thread.sleep((int) (Math.random() * 2000) + 2000);
                myQueue.enq((int) (Math.random() * 90) + 10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
