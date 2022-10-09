package Practical5T1;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CourseList {
    private Node head;
    private Node tail;
    private Lock lock = new ReentrantLock();
    private AtomicInteger currentPerson;

    public CourseList() {
        head = new Node(0, Integer.MIN_VALUE);
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
            Node prev = head;

            if (curr != null) {
                System.out.print(Thread.currentThread().getName() + ": ");
                while (curr != tail && curr != null) {
                    long time = curr.remainingTime();
                    if (time <= 0) {
                        System.out.print("(P-" + curr.number + ", 0ms)");
                        //time limit reached, remove the node
                        prev.next = curr.next;
                        curr.next = null;
                        curr = prev.next;
                    } else
                        System.out.print("(P-" + curr.number + ", " + time + "ms)");

                    if (curr.next != tail && curr.next != null)
                        System.out.print(", ");
                    prev = curr;
                    curr = curr.next;
                }
                System.out.println("");
            } //curr not null, could be becuase set to heads next!
        }
        finally {
            lock.unlock();
        }

    }

    public void add(int time) {
        lock.lock();

        try {
            Node pred = head;
            Node curr = pred.next;

            while (curr != tail) { //get the node before the tail
                pred = curr;
                curr = curr.next;
            }

            Node newNode = new Node(time, currentPerson.getAndIncrement());
            newNode.next = tail;
            pred.next = newNode;
        }
        finally {
            lock.unlock();
            print();
        }
    }

    private class Node {
        Node(int time, int number) {
            this.time = time;
            this.number = number;
            startTime = System.currentTimeMillis();
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
    }
}
