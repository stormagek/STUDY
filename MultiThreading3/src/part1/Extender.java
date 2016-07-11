package part1;
//https://www.youtube.com/playlist?list=PLBB24CFB073F1048E

public class Extender extends Thread {

	public void run() {
		for (int i = 1; i < 21; i++) {

			System.out.println("Iter "+i+" "+Thread.currentThread().getName());
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {

		Extender extender1=new Extender();
		extender1.start();
		
		Extender extender2=new Extender();
		extender2.start();
		
	}

}
