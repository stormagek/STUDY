package part2;

import java.util.Scanner;

public class Proccessor extends Thread {

	private boolean running = true;

	public void run() {
		while (running) {
			System.out.println("Hello");
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void shutdown() {
		running = false;
	}

	public static void main(String[] args) {
		Proccessor proccessor1 = new Proccessor();
		proccessor1.start();
		System.out.println("Return to stop");
		Scanner scanner = new Scanner(System.in);
		scanner.nextLine();
		proccessor1.shutdown();
	}

}
