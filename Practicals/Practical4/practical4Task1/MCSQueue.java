package practical4Task1;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class MCSQueue implements Lock {

    AtomicReference<QNode> tail;            //tail = where nodes try to add to the queue, getAndSet() to aquire
    ThreadLocal<QNode> myNode;              //every thread has its own node that it adds to queue
    static AtomicInteger personNumber;

    public MCSQueue() {
        tail = new AtomicReference<>(null);     //set tail to null initially\
        personNumber = new AtomicInteger(0);
        myNode = new ThreadLocal<QNode>() {
            protected QNode initialValue() {
                return new QNode(Thread.currentThread().getName());
            }
        };
    }

    @Override
    public void lock() {
        myNode.get().number = personNumber.getAndIncrement();
        QNode node = myNode.get();
        QNode pred = tail.getAndSet(node);                  //set my node to the tail & store previous tail

        if (pred != null) {                                 //was there someone waiting?
            node.locked = true;                             //mark my node as locked
            pred.next = node;  //add myself to the list
            node.pred = pred; //to print

            while (node.locked){}                           //wait for pred to release my node
        }
        System.out.println(Thread.currentThread().getName() + " Person " + node.number + " has entered the voting station");
    }

    private void printQueue(QNode curr) {

        if (curr != null) {
            printQueue(curr.pred);
            System.out.print("{" + curr.getName() + ":Person " + curr.number + "}");
            if (curr.next != null) System.out.print(" -> ");
        }

    }

    @Override
    public void unlock() {
        QNode node = myNode.get();

        if (node.next == null) {                            //empty or slow node
            if (tail.compareAndSet(node, null)){     //try to set tail to null (empty)
                System.out.println(Thread.currentThread().getName() + " Person " + node.number + " has cast a vote");
                printQueue(tail.get());
                return;
            }

            while (node.next == null) {}                    //wait for successor to fill next
        }

        System.out.println(Thread.currentThread().getName() + " Person " + node.number + " has cast a vote");



        node.next.pred = node.pred;
        //print the queue on exit
        printQueue(tail.get());
        System.out.println("");

        node.next.locked = false; //next can go
        node.next = null;//free node
        node.pred = null;
    }

    class QNode {
        private String nodeName;

        QNode(String n) {
            nodeName = n;
            locked = false;
            next = null;
            pred = null;
        }
        String getName() { return this.nodeName; }

        //to lock and unlock nodes
        public volatile boolean locked;
        public volatile QNode next;
        public volatile QNode pred;
        public int number;
    }

    //needed to implement lock
    @Override public void lockInterruptibly() throws InterruptedException {}
    @Override public boolean tryLock() { return false;}
    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException { return false; }
    @Override
    public Condition newCondition() { return null; }
}
