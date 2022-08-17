package com.company;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//The shared scrumbaord object
//Makes use of locks to synchronise
public class ScrumLock implements Lock {
    private ArrayList<String> todo;
    private ArrayList<String> completed;
    private Lock lock;

    int victim;
    int id;
    private boolean flag[] = new boolean[2];

    ScrumLock(int i) {
        lock = new ReentrantLock();

        todo = new ArrayList<>();
        completed = new ArrayList<>();

        flag[0] = false;
        flag[1] = false;
        victim = 0;

        id = i;

        todo.add("ONE");
        todo.add("TWO");
        todo.add("THREE");
        todo.add("FOUR");
        todo.add("FIVE");
        todo.add("SIX");
        todo.add("SEVEN");
        todo.add("EIGHT");
        todo.add("NINE");
        todo.add("TEN");

        System.out.println("Created board");
    }

    public String getNextItem() {
        try {
            this.lock();
            if (todo.size() > 0)
                return todo.remove(todo.size() - 1);
        } finally {
            this.unlock();
        }

        return "empty list";
    }

    public void completeItem(String i) {
        try {
            this.lock();
            if (i.equals("empty list"))
                return ; //lock unlocks automatically
            completed.add(i);
            //System.out.println("Completed " + i);
        } finally {
            this.unlock();
        }
    }

    //need to override the following methods to implement lock
    //The current implementation makes use of
    //a Reentrant lock to do the synchronisation

    @Override
    public void lock() {
        int me = id;
        int other = 1 - id;
        flag[me] = true;
        while (flag[other] && victim == me){}
    }
    @Override
    public void unlock() {
        flag[id] = false;
    }

    //Needed to implement lock

    @Override
    public void lockInterruptibly() throws InterruptedException {}
    @Override
    public boolean tryLock() { return false;}
    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException { return false; }

    @Override
    public Condition newCondition() { return null;}
}
