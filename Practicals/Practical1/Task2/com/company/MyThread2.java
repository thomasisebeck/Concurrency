package com.company;

//creates a thread that runs to consume items
//same for both the thread and lock implementation
public class MyThread2 extends Thread{
    private Thread t;
    final private String threadName;
    final private ScrumLock s;

    MyThread2(ScrumLock scrum) {
        this.threadName = this.getName();
        s = scrum;
    }

    public void run() {
        for (int i = 0; i < 5; i++) {
            String item = s.getNextItem();
            System.out.println(threadName + " Task: " + item);
            s.completeItem(item);
        }
    }

    public void start() {
        System.out.println("Starting thread " + threadName);
        if (t == null) {
            t = new Thread(this);
            t.start();
        }
    }
}
