package practical4;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class MCSQueue implements Lock {

    AtomicReference<QNode> tail;            //tail = where nodes try to add to the queue, getAndSet() to aquire
    ThreadLocal<QNode> myNode;              //every thread has its own node that it adds to queue

    public MCSQueue() {
        tail = new AtomicReference<>(null);     //set tail to null initially
        myNode = new ThreadLocal<QNode>() {
            protected QNode initialValue() {
                return new QNode();
            }
        };
    }

    @Override
    public void lock() {
        QNode node = myNode.get();
        QNode pred = tail.getAndSet(node);                  //set my node to the tail & store previous tail

        if (pred != null) {                                 //was there someone waiting?
            node.locked = true;                             //mark my node as locked
            pred.next = node;                               //add myself to the list

            while (node.locked){}                           //wait for pred to release my node
        }
    }

    @Override
    public void unlock() {
        QNode node = myNode.get();
        if (node.next == null) {                            //empty or slow node
            if (tail.compareAndSet(node, null))     //try to set tail to null (empty)
                return ;

            while (node.next == null) {}                    //wait for successor to fill next
        }
        node.next.locked = false;                           //next can go
        node.next = null;                                   //free node
    }

    class QNode {
        //to lock and unlock nodes
        volatile boolean locked = false;
        volatile QNode next = null;
    }

    //needed to implement lock
    @Override public void lockInterruptibly() throws InterruptedException {}
    @Override public boolean tryLock() { return false;}
    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException { return false; }
    @Override
    public Condition newCondition() { return null; }
}
