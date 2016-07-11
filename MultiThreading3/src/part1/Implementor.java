package part1;

public class Implementor implements Runnable {

	public void run() {
		for (int i = 1; i < 21; i++) {

			System.out.println("Iter " + i + " "
					+ Thread.currentThread().getName());
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		Thread thread1 = new Thread(new Implementor());
		Thread thread2 = new Thread(new Implementor());
		thread1.start();
		thread2.start();

	}

}
