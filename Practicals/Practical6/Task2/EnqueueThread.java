package Task2;

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
                e.printStackTrace();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
