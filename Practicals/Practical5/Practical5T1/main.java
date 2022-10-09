package Practical5T1;

public class main {

    public static void main(String args[]){
        CourseList mylist = new CourseList();
        mylist.add(2500);
        mylist.add(1100);
        mylist.add(560);
        mylist.add(240);
        mylist.print();

        while(!mylist.isEmpty()) {
            try {
                Thread.sleep(50);
            }catch (InterruptedException e) {}

            mylist.print();
        }

    }

}
