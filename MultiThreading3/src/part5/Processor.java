package part5;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Processor implements Runnable {

	private int id;

	public Processor(int id) {
		this.id = id;
	}

	@Override
	public void run() {
		System.out.println("Starting: " + id);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Completed: " + id);
	}

	public static void main(String[] args) {

		long start = System.currentTimeMillis();
		ExecutorService executorService = Executors.newFixedThreadPool(2);
		for (int i = 1; i < 10; i++) {
			executorService.submit(new Processor(i));
		}
		executorService.shutdown();
		System.out.println("All tasks submitted");
		try {
			executorService.awaitTermination(1,TimeUnit.DAYS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long end = System.currentTimeMillis();
		System.out.println("All tasks Completed: " + (end - start) / 1000
				+ "seconds");
	}
}
