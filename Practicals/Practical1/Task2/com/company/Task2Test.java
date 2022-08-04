package com.company;

public class Task2Test {
    public static void main (String[] args) {
        ScrumLock s = new ScrumLock();
        Thread t1 = new MyThread2(s, "T1");
        Thread t2 = new MyThread2(s, "T2");
        t1.start();
        t2.start();
    }
}