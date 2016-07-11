package part12;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class App
{
    public static void main (String[] args) throws Exception
    {
        ExecutorService executor = Executors.newCachedThreadPool();
        for (int i = 0; i < 200; i++)
        {
            executor.submit(new Runnable()
            {
                public void run ()
                {
                    //This print is just for debugging
                    //System.out.println(System.identityHashCode(Thread.currentThread()));
                    Connection.getInstance().connect();
                }
            });
        }
        executor.shutdown();//Shutting down the managerial thread of the pool
        executor.awaitTermination(1, TimeUnit.DAYS);

        //This commented out code just shows basic methods of semaphore and print the result.
        //        Semaphore sem = new Semaphore(1);
        //        System.out.println("Available permits: " + sem.availablePermits());
        //        sem.release();
        //        System.out.println("Available permits: " + sem.availablePermits());
        //        sem.acquire();
        //        sem.release();
        //        System.out.println("Available permits: " + sem.availablePermits());

    }
}//End of class App
