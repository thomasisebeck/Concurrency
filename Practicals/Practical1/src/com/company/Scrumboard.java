package com.company;

import java.util.ArrayList;

public class Scrumboard {
    ArrayList<String> todo;
    ArrayList<String> completed;

    public Scrumboard() {

        todo = new ArrayList<>();
        completed = new ArrayList<>();


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
        synchronized (this) {
            if (todo.size() > 0) {
                return todo.remove(todo.size() - 1);
            }
            return "empty list";
        }
    }

    public void completeItem(String i) {

        if (i.equals("empty list")) return ;

        synchronized (this) {
            completed.add(i);
            System.out.println("Completed " + i);
        }
    }

}

