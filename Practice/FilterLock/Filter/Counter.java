package Filter;

public class Counter {
    private long value;
    private MyFilter lock;

    public Counter(int start, int filterSize) {
        value = start;
        lock = new MyFilter(filterSize);
    }

    public long getAndInc() {
        lock.lock();
        try {
            long temp = value;
            value = temp + 1;
            return temp;
        } finally {
            lock.unlock();
        }
    }

    public long getValue() {
        return value;
    }
}
