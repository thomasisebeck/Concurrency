package SoftwareEngineering;

public class Main {
    public static void main(String[] args) {

        //all components have been added to the develop queue
        Project project = new Project();

        Thread t1 = new PersonThread(project);
        Thread t2 = new PersonThread(project);
        Thread t3 = new PersonThread(project);
        Thread t4 = new PersonThread(project);
        Thread t5 = new PersonThread(project);

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();

        //Feedback: Threads should be made in the constructor!
        //Should be able to pass in a variable number of threads!
    }
}
