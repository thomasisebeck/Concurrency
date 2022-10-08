package Practical5T1;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CourseList {
    private Node head;
    private Node tail;
    private Lock lock = new ReentrantLock();
    AtomicInteger currentPerson;

    public CourseList() {
        head = new Node(0, Integer.MIN_VALUE);
        tail = new Node(0, Integer.MAX_VALUE);
        head.next = tail;
        currentPerson = new AtomicInteger(0);
    }

    public void add(double time) {
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
        }
    }


    private class Node {
        Node(double time, int number) {
            this.time = time;
            this.number = number;
        }
        public double time;
        public double number;
        public Node next;
    }
}
