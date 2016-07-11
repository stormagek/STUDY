package part11;

public class App {

	
	public static void main(String[] args){
		
		final Runner runner=new Runner();
		
		Thread t1=new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					runner.firstThread();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		
		Thread t2=new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					runner.secondThread();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
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
		
		runner.finished();

	}
}
