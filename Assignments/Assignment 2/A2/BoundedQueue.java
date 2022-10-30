package A2;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class BoundedQueue<T> {
    ReentrantLock enqLock, deqLock, printLock;
    Condition notEmptyCondition, notFullCondition;
    int capacity;
    AtomicInteger head;
    AtomicInteger tail;
    Vehicle[] queue;
    String name;

    public BoundedQueue(int cap, String name) {
        this.capacity = cap;
        queue = new Vehicle[capacity];
        head = new AtomicInteger(0);
        tail = new AtomicInteger(0);
        enqLock = new ReentrantLock();
        printLock = new ReentrantLock();
        notFullCondition = enqLock.newCondition();
        deqLock = new ReentrantLock();
        notEmptyCondition = deqLock.newCondition();
        this.name = name;
    }
    public void enq(Vehicle x) throws InterruptedException {
        boolean mustWakeDequeuers = false;
        enqLock.lock();

        try { //wait until not full
            while ((tail.get() - head.get()) == capacity)
                notFullCondition.await();

            queue[tail.getAndIncrement() % capacity] = x; //add node

            if ((tail.get() - head.get()) == 1) //not Empty, and first to be not empty
                mustWakeDequeuers = true;
            }
        finally {
            enqLock.unlock();
        }

        //check if you added the first node and wake the dequeurs
        if (mustWakeDequeuers) {
            deqLock.lock();
            try {
                notEmptyCondition.signalAll();
            } finally {
                deqLock.unlock();
            }
        }

    }
    public Vehicle get(int index) {
        return queue[index];
    }
    public int getSize() {
        return tail.get() - head.get();
    }

    public void print(boolean isEntering) {
        printLock.lock();
        String res;

        if (isEntering)
            res = "entering: ";
        else
            res = "leaving: ";

        boolean musPrint = false;
        for (int i = head.get(); i < tail.get(); i++) {
            if (queue[i % capacity] != null) {
                musPrint = true;
                res += queue[i % capacity].getName();
                if (i != tail.get() - 1)
                    res += ", ";
            }
        }

        if (musPrint)
            System.out.println(name + ": " + res);

        printLock.unlock();
    }

    public Vehicle deq() throws InterruptedException {
        Vehicle result;
        boolean mustWakeEnqueuers = false;
        deqLock.lock();
        try {

            while ((tail.get() - head.get()) == 0)
                notEmptyCondition.await();

            result = queue[head.getAndIncrement() % capacity];

            if ((tail.get() - head.get()) == capacity - 2) //not full, and first to be not full
                mustWakeEnqueuers = true;

        } catch (InterruptedException e) { throw new RuntimeException(e); }
        finally {
            deqLock.unlock();
        }

        if (mustWakeEnqueuers) {
            enqLock.lock();
            try {
                notFullCondition.signalAll();
            } finally {
                enqLock.unlock();
            }
        }

        return result;
    }

    public boolean isEmpty() {
        return tail.get() - head.get() == 0;
    }
}
