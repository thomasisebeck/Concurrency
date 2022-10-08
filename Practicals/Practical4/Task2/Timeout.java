package Task2;

import Task1.MCSQueue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class Timeout implements Lock {

    static QNode AVAILABLE = new QNode("AVAILABLE");
    AtomicReference<QNode> tail;                                    //tail = where nodes try to add to the queue, getAndSet() to aquire
    List<String> printList;
    ThreadLocal<QNode> myNode;                                      //every thread has its own node that it adds to queue
    static AtomicInteger personNumber;
    final int TIME = 100;

    public Timeout() {
        tail = new AtomicReference<>(null);
        personNumber = new AtomicInteger(0);
        myNode = new ThreadLocal<QNode>() {
            protected QNode initialValue() {
                return new QNode(Thread.currentThread().getName());
            }
        };
        printList = new ArrayList<>();
    }

    @Override
    public boolean tryLock() {

       long startTime = System.currentTimeMillis();
       long patience = 50;

       QNode qnode = new QNode(Thread.currentThread().getName());
       myNode.set(qnode);
       qnode.pred = null;


       QNode myPred = tail.getAndSet(qnode);

       if (myPred == null || myPred.pred == AVAILABLE) {
           personNumber.getAndIncrement();
           qnode.number = personNumber.get();
           printList.add(myNode.get().nodeName + " " + personNumber.get());
           System.out.println(Thread.currentThread().getName() + " Person " + qnode.number + " has entered the voting station");
           return true;
       }

       while (System.currentTimeMillis() - startTime < patience) {
           QNode predPred = myPred.pred;
           if (predPred == AVAILABLE) {
               personNumber.getAndIncrement();
               qnode.number = personNumber.get();
               printList.add(myNode.get().nodeName + " " + personNumber.get());
               System.out.println(Thread.currentThread().getName() + " Person " + qnode.number + " has entered the voting station");
               return true;
           }
           else if (predPred != null) {
               myPred = predPred;
           }
       }

       if (!tail.compareAndSet(qnode, myPred))
           qnode.pred = myPred;

       return false;
    }

    @Override
    public void lock() {}

    private void printQueue() {
        for (String i : printList)
            System.out.print("{" + i + "}");
        System.out.println("");
    }

    @Override
    public void unlock() {
        QNode node = myNode.get();

        System.out.println(Thread.currentThread().getName() + " Person " + node.number + " has cast a vote");

        //print the queue on exit
        printQueue();
        System.out.println("");

        printList.remove(myNode.get().nodeName + " " + myNode.get().number);

        if (!tail.compareAndSet(node, null)) // If I'm not the tail, let the next node go
            node.pred = AVAILABLE;

    }

    static class QNode {
        private String nodeName;

        QNode(String n) {
            nodeName = n;
            next = null;
            pred = null;
            number = -1;
        }
        String getName() { return this.nodeName; }

        //to lock and unlock nodes
        public volatile QNode next;
        public volatile QNode pred;
        public int number;
    }

    //needed to implement lock
    @Override public void lockInterruptibly() throws InterruptedException {}
    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException { return false; }
    @Override
    public Condition newCondition() { return null; }
}
