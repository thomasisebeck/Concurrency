
//implement runnable class
public class RunnableThread implements Runnable {

    private Thread t;
    private String id;

    RunnableThread(String id) {
        this.id = id;
        System.out.println("Creating thread: " + id);
    }

    public void start() {
        System.out.println("Starting thread: " + id);
        if (t == null) {
            t = new Thread (this, id);
            t.start();
        }
    }

    @Override
    public void run() {

        System.out.println("Running thread: " + id);
        try {
            for (int i = 4; i > 0; i--) {
                System.out.println("T[" + id + "]");
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
            System.out.println("Thread " + id + " interrupted");
        }

        System.out.println("Thread " + id + " exiting...");

    }

    public int inc(int num) { return ++num;}

    public int sub(int num) { return --num; }

    public int multfour(int num) { return num * 4; }

    public int multtwo(int num) { return num * 2; }


}
