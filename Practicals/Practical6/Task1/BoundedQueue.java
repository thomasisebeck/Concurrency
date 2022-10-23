package Task1;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class BoundedQueue<T> {
    ReentrantLock enqLock, deqLock, printLock;
    Condition notEmptyCondition, notFullCondition;
    int capacity;
    AtomicInteger head;
    AtomicInteger tail;
    T[] queue;

    public BoundedQueue(int cap) {
        this.capacity = cap;
        queue = (T[]) new Object[capacity];
        head = new AtomicInteger(0);
        tail = new AtomicInteger(0);
        enqLock = new ReentrantLock();
        printLock = new ReentrantLock();
        notFullCondition = enqLock.newCondition();
        deqLock = new ReentrantLock();
        notEmptyCondition = deqLock.newCondition();
    }
    public void enq(T x) throws InterruptedException {
        boolean mustWakeDequeuers = false;
        enqLock.lock();

        try { //wait until not full
            while ((tail.get() - head.get()) == capacity)
                notFullCondition.await();

            printLock.lock();
            queue[tail.getAndIncrement() % capacity] = x; //add node
            System.out.println(Thread.currentThread().getName() + " [e] " + x + "(" + (tail.get() - head.get()) + ")");
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
    public T deq() throws InterruptedException {
        T result;
        boolean mustWakeEnqueuers = false;
        deqLock.lock();
        try {

            while ((tail.get() - head.get()) == 0)
                notEmptyCondition.await();

            printLock.lock();
            result = queue[head.getAndIncrement() % capacity];
            System.out.println(Thread.currentThread().getName() + " [d] " + result + "(" + (tail.get() - head.get()) + ")");
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
}
