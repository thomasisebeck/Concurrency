package Practical5;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class OptimisticList {
    private Node head;
    private Node tail;
    private AtomicInteger currentPerson;
    private Lock lock = new ReentrantLock();

    public OptimisticList() {
        head = new Node(0, -1);
        tail = new Node(0, Integer.MAX_VALUE);
        head.next = tail;
        currentPerson = new AtomicInteger(0);
    }

    public boolean isEmpty() {
        return head.next == null || head.next == tail;
    }

    public void print() {
        lock.lock();

        try {
            Node curr = head.next;
            Node prev = null;
            String toPrint = "";
            boolean mustPrint = false;

            if (curr != null) {
                toPrint += Thread.currentThread().getName() + ": ";
                while (curr != tail && curr != null) {
                    long time = curr.remainingTime();
                    if (time <= 0) {
                        toPrint += "(P-" + curr.number + ", 0ms)";
                        mustPrint = true;
                        //time limit reached, remove the node
                        if (prev != null) { //removes if time is up
                            prev.next = curr.next;
                            curr.next = null;
                            curr = prev;
                        }
                        else {
                            head = head.next;
                        }
                    } else
                        toPrint += "(P-" + curr.number + ", " + time + "ms)";

                    if (curr.next != tail && curr.next != null)
                        toPrint += ", ";
                    prev = curr;
                    curr = curr.next;
                }
                if (mustPrint) {
                    System.out.println(toPrint);
                }

            } //curr not null, could be becuase set to heads next!
        }
        finally {
            lock.unlock();
        }

    }

    private boolean validate(Node pred, Node curr) {
        Node node = head;

        while (node != null){
            if (node.number == pred.number)
                return pred.next == curr; //pred and curr are still in correct order
            node = node.next;
        }
        return false;
    }

    public boolean contains(Node toFind) {
        while (true) {
            Node pred = head;
            Node curr = pred.next;
            while (curr != tail && curr.number != toFind.number) {
                pred = curr;
                curr = curr.next;
            }

            pred.lock.lock();
            try {
                curr.lock.lock();
                try {

                    if (validate(pred, curr)) //still linked in correct order
                        return (curr.number == toFind.number); //node found

                } finally { curr.lock.unlock(); }
            }  finally { pred.lock.unlock(); }

        }
    }

    public boolean add(int time) {
        while (true) { //try repeatedly until added
            Node pred = head;
            Node curr = head.next;
            while (curr != tail) { //go to the end of the list
                pred = curr;
                curr = pred.next;
            }

            pred.lock.lock(); //lock the predecessor
            try {
                curr.lock.lock(); //lock the current node
                try {
                    if (validate(pred, curr)) { //still in correct order, add the node
                        Node newNode = new Node(time, currentPerson.getAndIncrement());
                        newNode.next = tail;
                        pred.next = newNode;
                        return true;
                    } //if validate()
                } finally { curr.lock.unlock(); }
            } finally {
                System.out.println(Thread.currentThread().getName() + " ADDED (P-" + (currentPerson.get() - 1) + ", " + time + "ms)");
                pred.lock.unlock();
            }
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
