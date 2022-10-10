package Practical5T1;

class PersonQueueFine extends Thread {
    FineList list;
    int nodesAdded;
    int timeSlept;
    PersonQueueFine() {
        list = new FineList();
        nodesAdded = 0;
        timeSlept = 0;
        //list.print();
    }

    public void run() {
        list.add((int)(Math.random() * 900) + 100); //add the first node
        nodesAdded++;

        while(!list.isEmpty() || nodesAdded < 5) {
            try {
                Thread.sleep(50);
                if (nodesAdded < 5) {
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
