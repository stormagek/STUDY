package part6;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CountDownLatcheser implements Runnable {
	private CountDownLatch latch;

	public CountDownLatcheser(CountDownLatch latch) {
		this.latch = latch;
	}

	@Override
	public void run() {
		System.out.println("#############################");
		System.out.println("Started: " + latch.getCount());
		System.out.println("#############################");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		latch.countDown();
	}

	public static void main(String[] args) {
		CountDownLatch countDownLatch = new CountDownLatch(10);
		ExecutorService executorService = Executors.newFixedThreadPool(3);
		for (int i = 0; i < 100; i++) {
			executorService.submit(new CountDownLatcheser(countDownLatch));
		}
		try {
			countDownLatch.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Finished");
	}
}
