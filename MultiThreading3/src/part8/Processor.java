package part8;

import java.util.Scanner;

public class Processor {
	public void consume() throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        Thread.sleep(2000);
        synchronized (this)//we'll synchronize on the Processor object
        {
            System.out.println("Waiting for return key...");
            scanner.nextLine();   //Program halts until we press Enter
            notify();
            System.out.println("consume() has finished running");
        }
	}

	public void produce() throws InterruptedException {
		synchronized (this)// we'll synchronize on the Processor object
		{
			System.out.println("Producer thread running...");
			wait();
			System.out.println("Resumed...");
		}
	}

}
