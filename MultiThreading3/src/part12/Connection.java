package part12;

import java.util.concurrent.Semaphore;

public class Connection
{
    private static Connection instance    = new Connection();
    private Semaphore         sem         = new Semaphore(10);//10 connections at at a time.
    private int               connections = 0;

    private Connection ()
    {

    }

    public static Connection getInstance ()
    {
        return instance;
    }

    public void connect ()
    {
        try
        {
            sem.acquire();//Has to acquire a permit before it can run, permits number is
                          //decreased by 1. - Look below for Javadoc for this method.
        }
        catch (InterruptedException e1)
        {
            e1.printStackTrace();
        }
        try
        {
            doConnect();
        }
        finally
        {
            sem.release();//Has to release a permit before it ends, permits number is
            ////increased by 1.
        }
    }

    public void doConnect ()
    {
        //        try
        //        {
        //            sem.acquire();//Has to acquire a permit before it can run, permits number is
        //                          //decreased by 1.
        //        }
        //        catch (InterruptedException e1)
        //        {
        //            e1.printStackTrace();
        //        }
        synchronized (this)//synchronized block is needed because of the ++ operator
        {
            connections++;
            System.out.println("Current connections: " + connections);
        }
        try
        {
            Thread.sleep(2000);//For simulating some real work.
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        synchronized (this)//synchronized block is needed because of the -- operator
        {
            connections--;
            //System.out.println("Current connections after --: " + connections);
        }
        //        sem.release();//Has to release a permit before it ends, permits number is
        //                      ////increased by 1.
    }//End of connect() method
}//End of class Connection
