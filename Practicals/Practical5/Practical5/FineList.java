package Practical5;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class FineList {
    private Node head;
    private Node tail;
    private AtomicInteger currentPerson;

    public FineList() {
        head = new Node(0, -1);
        tail = new Node(0, Integer.MAX_VALUE);
        head.next = tail;
        currentPerson = new AtomicInteger(0);
    }

    public boolean isEmpty() {
        return head == null || head.next == tail;
    }

    public void print() {
        if (head == null) return ;
        head.lock.lock(); //lock the head node
        Node prev = null;
        String toPrint = "";
        boolean mustPrint = false;

        try {

            Node curr = head.next; //first node to be printed
            if (curr != null) curr.lock.lock();

            try {
                if (curr != null)
                    toPrint += Thread.currentThread().getName() + ": "; //add the thread name

                while (curr != null && curr != tail) {
                    long time = curr.remainingTime();

                    if (time <= 0) { //print when someone leaves
                        toPrint += "(P-" + curr.number + ", 0ms)";
                        mustPrint = true;

                        if (prev != null) { //removes if time is up
                            prev.lock.lock();
                            prev.next = curr.next;
                            curr.next = null;
                            curr = prev;
                        }
                        else if (head.next != null)
                            head = head.next; //remove the head node
                    } else
                        toPrint += "(P-" + curr.number + ", " + time + "ms)"; //print the time remaining

                    if (curr.next != tail && curr.next != null) //print a comma if not last item
                        toPrint += ", ";

                    if (prev != null)
                        prev.lock.unlock();
                    prev = curr;
                    curr = curr.next;
                    if (curr != null) curr.lock.lock();
                }
            } finally {
                if (curr != null) curr.lock.unlock();
            }
        }
        finally {
            if (prev != null)
                prev.lock.unlock();

                if (mustPrint)
                    System.out.println(toPrint);
        }

    }

    public void add(int time) {
        head.lock.lock();
        Node pred = head;

        try {
            Node curr = pred.next; //node after head
            if (curr != null) curr.lock.lock();

            try {

                while (curr != tail && curr != null) {
                    pred.lock.unlock(); //unlock previous node
                    pred = curr; //set current node to previous
                    curr = curr.next; //walk
                    curr.lock.lock(); //lock the current node
                }

                Node newNode = new Node(time, currentPerson.getAndIncrement());
                pred.next = newNode; //add to the list
                newNode.next = tail; //set tail to next

            } finally { if (curr != null) curr.lock.unlock(); }
        } finally {
            System.out.println(Thread.currentThread().getName() + " ADDED (P-" + (currentPerson.get() - 1) + ", " + time + "ms)");
            if (pred != null && pred.lock.isLocked()) pred.lock.unlock();
        }

    }

    private class Node {
        Node(int time, int number) {
            this.time = time;
            this.number = number;
            startTime = System.currentTimeMillis();
            lock = new ReentrantLock();
        }
        public long startTime;
        public long remainingTime() {
            long rem =  this.time - (System.currentTimeMillis() - this.startTime);
            if (rem > this.time) return 0;
            return rem;
        }
        public int time;
        public int number;
        public Node next;
        public ReentrantLock lock;
    }
}
