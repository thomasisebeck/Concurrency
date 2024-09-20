package Task2;

import java.util.concurrent.atomic.AtomicInteger;

/*

    Without locks,

 */

public class BoundedQueue<T> {
    int capacity;
    AtomicInteger head;
    AtomicInteger tail;
    T[] queue;

    public BoundedQueue(int cap) {
        this.capacity = cap;
        queue = (T[]) new Object[capacity];
        head = new AtomicInteger(0);
        tail = new AtomicInteger(0);
    }
    public void enq(T x) throws Exception {

        if (tail.get() - head.get() == capacity)
            fullException();
        queue[tail.getAndIncrement() % capacity] = x;
        System.out.println(Thread.currentThread().getName() + " [e] " + x + "(" + (tail.get() - head.get()) + ")");

    }
    public T deq() throws Exception {
        T result = null;

        if (tail.get() - head.get() == 0)
            emptyException();

        result = queue[head.getAndIncrement() % capacity];
        System.out.println(Thread.currentThread().getName() + " [d] " + result + "(" + (tail.get() - head.get()) + ")");

        return result;
    }

    // Method which throws Exception
    public static void emptyException() throws Exception
    {
        throw new Exception("Empty Queue");
    }

    public static void fullException() throws Exception
    {
        throw new Exception("Full Queue");
    }
}
