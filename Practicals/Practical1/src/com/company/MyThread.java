package com.company;

public class MyThread extends Thread{
    private Thread t;
    private String threadName;
    private Scrumboard s;

    MyThread(Scrumboard scrum, String tn) {
        this.threadName = tn;
        System.out.println("Creating thread: " + threadName);
        s = scrum;
    }

    public void run() {
        for (int i = 0; i < 5; i++) {
            synchronized (this) {
                String item = s.getNextItem();
                System.out.println(threadName + " Task: " + item);
                s.completeItem(item);
            }
        }
    }

    public void start() {
        System.out.println("Starting thread " + threadName);
        if (t == null) {
            t = new Thread(this, threadName);
            t.start();
        }
    }
}
