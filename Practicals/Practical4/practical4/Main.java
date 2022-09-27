package practical4;

public class Main {
    public static void main(String args[]){
        MCSQueue queueLock = new MCSQueue();
        TestThread t1 = new TestThread(queueLock);
        TestThread t2 = new TestThread(queueLock);

        t1.start();
        t2.start();
    }
}
