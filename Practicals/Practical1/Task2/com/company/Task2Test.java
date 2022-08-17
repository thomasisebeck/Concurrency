package com.company;

public class Task2Test {
    public static void main (String[] args) {
        ScrumLock s1 = new ScrumLock(0);
        Thread t1 = new MyThread2(s1);
        Thread t2 = new MyThread2(s1);
        t1.start();
        t2.start();

        //Didn't get thread id
        //Volatile variables
        //Alternating threads
    }
}