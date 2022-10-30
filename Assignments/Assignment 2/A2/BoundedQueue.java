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

    public BoundedQueue(int cap) {
        this.capacity = cap;
        queue = new Vehicle[capacity];
        head = new AtomicInteger(0);
        tail = new AtomicInteger(0);
        enqLock = new ReentrantLock();
        printLock = new ReentrantLock();
        notFullCondition = enqLock.newCondition();
        deqLock = new ReentrantLock();
        notEmptyCondition = deqLock.newCondition();
    }
    public void enq(Vehicle x) throws InterruptedException {
        boolean mustWakeDequeuers = false;
        enqLock.lock();

        try { //wait until not full
            while ((tail.get() - head.get()) == capacity)
                notFullCondition.await();

            printLock.lock();
            queue[tail.getAndIncrement() % capacity] = x; //add node
            printLock.unlock();

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
    public Vehicle deq() throws InterruptedException {
        Vehicle result;
        boolean mustWakeEnqueuers = false;
        deqLock.lock();
        try {

            while ((tail.get() - head.get()) == 0)
                notEmptyCondition.await();

            printLock.lock();
            result = queue[head.getAndIncrement() % capacity];

            String res = "";

            for (int i = head.get(); i < tail.get(); i++) {
                if (queue[i % capacity] != null) {
                    res += queue[i % capacity].getName();
                    if (i != tail.get() - 1)
                        res += ", ";
                }
            }

            if (res != "")
                System.out.println(res);

            printLock.unlock();

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
