package com.company;

public class Task1Test {
    public static void main (String[] args) {
        ScrumThread s = new ScrumThread();
        Thread t1 = new MyThread1(s, "T1");
        Thread t2 = new MyThread1(s, "T2");
        t1.start();
        t2.start();
    }
}