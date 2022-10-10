package Practical5;

public class PersonQueueOptimistic extends Thread{
    OptimisticList list;
    int nodesAdded;
    int timeSlept;
    PersonQueueOptimistic() {
        list = new OptimisticList();
        nodesAdded = 0;
        timeSlept = 0;
    }

    public void run() {
        list.add((int)(Math.random() * 900) + 100); //add the first node
        nodesAdded++;

        while(!list.isEmpty() || nodesAdded < 10) {
            try {
                Thread.sleep(50);
                if (nodesAdded < 10) {
                    timeSlept += 50;
                    if (timeSlept == 200) { //add a node every 200 ms
                        timeSlept = 0;
                        list.add((int)(Math.random() * 900) + 100);
                        nodesAdded++;
                    }
                }
            }catch (InterruptedException e) {}
            list.print();
        }

    }
}
