package SoftwareEngineering;

public class PersonThread extends Thread{

    Project proj;
    PersonThread(Project p) {
        proj = p;
    }
    @Override
    public void run() {

        boolean devHas = true;
        boolean testHas = true;
        int misses = 0;

        //items in the develop or test queue
        while (proj.getNumTested() < proj.getNumComponents() || proj.getNumDeveloped() < proj.getNumComponents()) {

            //System.out.println("Exec: " + proj.devEmpty() + ", " + proj.testEmpty());

            if (!proj.devEmpty()) {
                //synchronised dequeue with lock
                Component toDev = proj.getComponentFromDevelop();

                if (toDev == null)
                    continue; //queue was empty

                //working time between 100 and 500 ms
                int randomSleep = (int) (Math.random() * 400) + 100;

                try {
                    Thread.sleep(randomSleep);
                } catch (InterruptedException e) {}

                //atomic subtract with lock
                proj.subtractDevopTime(toDev, randomSleep);

                if (toDev.developTime > 0) {
                    System.out.println(this.getName() + " Developed " + toDev.name + " for " + randomSleep
                            + ". Time remaining: " + toDev.developTime);

                    //synchronised enqueue with lock
                    proj.addComponentToDevelop(toDev);
                } else {
                    System.out.println(this.getName() + " finished developing " + toDev.name);
                    //synchronised enqueue with lock
                    proj.addComponentToTest(toDev);
                }

                //////////  TAKE A BREAK //////////
                // take a break between 50 and 100 ms
                randomSleep = (int) (Math.random() * 50) + 50;
                System.out.println(this.getName() + " is taking a break.");

                try {
                    Thread.sleep(randomSleep);
                } catch (InterruptedException ignored) {}

                //after developing and sleeping, the thread is ready
                System.out.println(this.getName() + " is ready to develop/test a component.");
                //////////////////////////////////

            } //if not dev empty


            if (!proj.testEmpty()) {
                //synchronised dequeue with lock
                Component toTest = proj.getComponentFromTest();

                if (toTest == null) {
                    misses++;
                    continue; //queue was empty
                }


                //working time between 100 and 500 ms
                int randomSleep = (int) (Math.random() * 400) + 100;

                try {
                    Thread.sleep(randomSleep);
                } catch (InterruptedException e) {}

                //atomic subtract with lock
                proj.subtractTestTime(toTest, randomSleep);

                if (toTest.testTime > 0) {
                    System.out.println(this.getName() + " Tested " + toTest.name + " for " + randomSleep
                            + ". Time remaining: " + toTest.testTime);

                    //synchronised enqueue with lock
                    proj.addComponentToTest(toTest);
                } else {
                    System.out.println(this.getName() + " finished testing " + toTest.name);
                }

                //////////  TAKE A BREAK //////////
                // take a break between 50 and 100 ms
                randomSleep = (int) (Math.random() * 50) + 50;
                System.out.println(this.getName() + " is taking a break.");

                try {
                    Thread.sleep(randomSleep);
                } catch (InterruptedException ignored) {}

                //after developing and sleeping, the thread is ready
                System.out.println(this.getName() + " is ready to develop/test a component.");
                //////////////////////////////////

                // catch the last few projects that were out of the
                // queue while being tested
                /*if (proj.testEmpty()) {
                    for (int i = 0; i < 5; i++) {
                        try {
                            Thread.sleep(200 + randomSleep);
                        } catch (InterruptedException ignored) {}
                        if (!proj.testEmpty())
                            break;
                    }
                }*/


            } //if not test empty
        }

        try {
            Thread.sleep(100);
        } catch (InterruptedException ignored) {}

        proj.printResults();



    }
}
