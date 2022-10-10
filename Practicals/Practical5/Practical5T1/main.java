package Practical5T1;

public class main {

    static void Task1() {
        PersonQueueCoarse c1 = new PersonQueueCoarse();
        PersonQueueCoarse c2 = new PersonQueueCoarse();
        PersonQueueCoarse c3 = new PersonQueueCoarse();
        PersonQueueCoarse c4 = new PersonQueueCoarse();
        PersonQueueCoarse c5 = new PersonQueueCoarse();

        c1.start();
        c2.start();
        c3.start();
        c4.start();
        c5.start();
    }

    static void Task2() {
        PersonQueueFine f1 = new PersonQueueFine();
        PersonQueueFine f2 = new PersonQueueFine();
        PersonQueueFine f3 = new PersonQueueFine();
        PersonQueueFine f4 = new PersonQueueFine();
        PersonQueueFine f5 = new PersonQueueFine();

        f1.start();
        f2.start();
        f3.start();
        f4.start();
        f5.start();
    }

    public static void main(String args[]){

        //Task1();
        Task2();

    }

}
