package part4;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Worker {

	private Random random = new Random();
	private Object lock1 = new Object();
	private Object lock2 = new Object();

	private List<Integer> list1 = new ArrayList<Integer>();
	private List<Integer> list2 = new ArrayList<Integer>();

	public void bothProcces() {
		this.procces1();
		this.procces2();
	}

	public void procces1() {
		synchronized (lock1) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for (int i = 0; i < 1000; i++) {
				list1.add(random.nextInt(100));
			}
		}
	}

	public void procces2() {
		synchronized (lock2) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for (int i = 0; i < 1000; i++) {
				list2.add(random.nextInt(100));
			}
		}
	}

	public void mainMethod() {
		long start = System.currentTimeMillis();
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				bothProcces();
			}
		});
		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				bothProcces();
			}
		});

		t1.start();
		t2.start();
		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long end = System.currentTimeMillis();
		System.out.println("Time: " + (end - start));
		System.out.println("Size1: " + list1.size() + "; Size1: "
				+ list2.size());

	}

}
