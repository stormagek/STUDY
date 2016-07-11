package part14;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class App2 {
	public static void main(String[] args) throws InterruptedException {
		System.out.println("Starting...");

		ExecutorService exec = Executors.newCachedThreadPool();
		// We don't need to return anything, so the parameterized type is Void
		Future<?> future = exec.submit(new Callable<Void>() {
			@Override
			public Void call() throws Exception {
				Random random = new Random();
				for (int i = 0; i < 1E8; i++) // 1E6=1*10^6
				{
					// If the interrupted flag is set, then we terminate the
					// thread
					// gracefully, and the main thread gets CPU time.
					if (Thread.currentThread().isInterrupted()) {
						System.out.println("Interrupted!");
						break; // Or we could just use return;
					}

					// System.out.println(i);

					Math.sin(random.nextDouble());
				} // End of for-loop
				return null;
			}
		});

		// Shuts down the pool's managerial thread, after previous tasks are
		// finished
		exec.shutdown();

		Thread.sleep(500);

		exec.shutdownNow();

		// future.cancel(true);

		exec.awaitTermination(1, TimeUnit.DAYS);

		System.out.println("Finished...");
	}
}
