package part1;

public class QuickWay {

	public static void main(String[] args) {
		Thread t1 = new Thread(new Runnable() {

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
		});
		t1.start();
	}
}
