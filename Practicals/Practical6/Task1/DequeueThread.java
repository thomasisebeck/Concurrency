package Task1;

public class DequeueThread extends Thread{
    BoundedQueue myQueue;
    public DequeueThread(BoundedQueue Q) {
        myQueue = Q;
    }
    public void run() {
        while(true) {
            try {
                Thread.sleep((int) (Math.random() * 2000) + 2000);
                myQueue.deq();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
