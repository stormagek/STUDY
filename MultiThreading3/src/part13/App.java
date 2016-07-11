package part13;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

//Callable and Future (Both are interfaces)
//These 2 types enable us to get returned-results from our threads, and also allow
//thread code to throw exception.

//As in lecture 12, we use ExecutorService executor = Executors.newCachedThreadPool();
//Check lecture 12 for elaboration on the cached thread pool.
//We're submitting one task (Runnable instance) to the pool, how can we get a returned value
//from that thread code?

//One way to do it would be to create a separate class that implements Runnable,
//and the run method code could save the result in an instance variable.
//So if that new class name is:A, and the field storing the value is called b, then
//we could do the following in class App:
//A a=new A(); executor.submit(a); 
//After the thread is done and b had been assigned a new value, we'll do a.b;
//-->An illustrative code for this, at bottom of page.

//There is another way which is using Callable and Future which is more elegant.
//One more thing we'd like to explore is how can we throw exceptions from thread code.

//The original main method has been commented out and placed below the code at the bottom
//of this file.
//Rather than submitting a Runnable to the pool, he submits a Callable.

//Callable is a parameterized interface, we'll give it Integer as a generic type, and that 
//Integer type, is the type we want returned from the thread code.
//The method to implement in this interface is call(), and it returns the type we've specified,
//which is Integer in this case - Elaboration on this method - down below. 
//Now we'll add the implementation of the Runnable interface from the original method to the
//Callable interface.
//Now as opposed to before, I can get the value returned from the running thread, we do it
//using Future.
//The code of call() method executes in its own thread.
//The submit method returns a Future object. 
//The method submit, submits a value-returning task for execution and returns a Future 
//representing the pending results of the task. The Future's get method will return the task's
//result upon successful completion.(See at bottom of file Javadoc for this method).
//Future is also parameterized, and the parameter has to be the same as the one we've 
//specified for the Callable (Integer in our case).
//We'll use a variable of type Future to obtain the returned value from the thread, using
//future.get() - This method throws 2 checked exceptions.
//In the main thread, we invoke future.get(), without waiting for the other running thread
//to finish, and as we saw in previous lectures, we can wait by invoking
//executor.awaitTermination(timeout, unit) below the thread code.
//If we don't invoke this method, have no fear, because invoking future.get() blocks, it'll
//block until the thread associated with future is finished, when it finishes and terminates,
//get() stops blocking and returns.
//The invocation of future.get() happens in the main thread and it blocks until
//the thread associated with future is finished, when it finishes and terminates,
//get() stops blocking and returns.
//To sum up: we saw how to return value from thread.

//Now we'll see how to throw exceptions from thread code (from the call method).
//The statement  if(duration>2000) pertains that.

//The method call() has the "throws Exception" clause in ite signature, so we can throw
//virtually every exception that extends the Exception class, and he chose IOException 
//even though it's not relevant, just for illustration purposes.
//An important thing to note here is that the exception might be thrown from the thread
//in a time that it and the main thread run concurrently, this means that the exception
//does not propagate all the way up the stack to the main method:
//Every thread has its own stack, the main thread has the main method at the top of the stack,
//and any other thread has their own stack with stack frame for every invoked method, hence,
//the exception is not thrown and propagate to the main method stack-frame in the main thread's
//stack, rather it propagates in the stack of the other thread.

//Just for the sake of testing, I've commented out the future.get() invocation, and at
//the beginning of the call() method I did the following:
//int k=0; k=k/0; This code leads to an arithmeticException being thrown, however, I didn't
//see anything at the console, namely, some code caught this exception and handled it.
//The call() method's signature has the "throws Exception" clause, but that has nothing to do
//with this arithmeticException, hence, it's thrown by the call() method to the method that 
//invoked it.
//I've marked the code line k=k/0; with a break point, and started the debugger. 
//Here is the status of the stack when the debugger stopped at the break point:

//App (14) [Java Application] 
//          lecture13_1.App at localhost:57906  
//              Thread [pool-1-thread-1] (Suspended (breakpoint at line 85 in App$1))   
//                  App$1.call() line: 85   
//                  App$1.call() line: 1    
//                  FutureTask<V>.run() line: not available 
//                  ThreadPoolExecutor.runWorker(ThreadPoolExecutor$Worker) line: not available 
//                  ThreadPoolExecutor$Worker.run() line: not available 
//                  Thread.run() line: not available [local variables unavailable]  
//              Thread [DestroyJavaVM] (Running)    
//          C:\Program Files\Java\jre1.8.0_73\bin\javaw.exe (Feb 22, 2016, 11:09:36 PM)

//So we can see the method invocation chain that eventually invoked the call() method of the
//thread that runs as part of the pool thread, ( it started with Thread.run() ).
//The exception thrown because of the division by zero, is caught and handled by one of 
//those methods shown in the method invocation chain.

//Now I've uncommented future.get() invocation, and when duration<2000 is true, an
//IOException is thrown (The call method's signature throws Exception), and it's shown in
//the console like this:
//java.util.concurrent.ExecutionException: java.io.IOException: Sleeping for too long.

//The thrown exception will propagate to the future.get() method (If it's invoked).
//One of the exceptions future.get() throws is of type ExecutionException which is
//created if the computation threw an exception.
//Indeed the computation of the task threw an exception which is IOException.
//Some mechanism is in place, that handles that thrown exception in a way that alerts the
//future.get() method that the computation threw an exception, and as a result it needs
//to throw the relevant exception (future.get() throws 2 checked exceptions) - One of which is 
//ExecutionException, that according to Javadoc, the method throws this exception if the 
//computation threw an exception, and indeed the computation threw an exception, hence the
//future.get() method throws an exception of type ExecutionException.
//In other words, the cause of the ExecutionException exception that is thrown in the
//future.get() method, is the IOException exception that is thrown in the call() method
//of the thread we've created.

//Look at the invocation of future.get() in the code, its catch block has different ways
//of obtaining data from the original thrown exception (Which is IOException).

//Future class has some useful methods like cancel, that allows us to cancel the thread - 
//We'll look at threads interruption in next lecture.
//The method isDone() that lets us know if the thread is finished or not.

//What if we want to use a method of Future, but we don't want to get a returned result?
//We can do that by specifying a question mark (AKA wild card) within the angle brackets,
//at the creation of the Future variable (In our case it comes instead of Integer), 
//and the parameterized type for Callable will be Void.
//The method will return null.
//In this situation, we can use all of the Future class methods, without getting a returned
//value from the thread.
/* ( Void is a AutoBoxing feature (since JDK 1.5) of void).
 //It's self explanatory that Void is reference whereas void is a primitive type.
 //Where the requirement comes to have to use Void ?
 //One common usage with Generic types where we can't use primitive. */

public class App {
	public static void main(String[] args) {
		ExecutorService executor = Executors.newCachedThreadPool();
		Future<Integer> future = executor.submit(new Callable<Integer>() // Submitting
																			// a
																			// task
																			// to
																			// the
																			// pool
				{
					@Override
					public Integer call() throws Exception {
						Random random = new Random();
						int duration = random.nextInt(4000);

						if (duration > 2000) {
							System.out
									.println("duration is greater than 2000: "
											+ duration);
							throw new IOException("Sleeping for too long");
						}
						// Between the "Starting" print and the "Finished" print
						// we'll simulate
						// real work by sleeping.
						System.out.println("Starting...");

						try {
							Thread.sleep(duration);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}

						System.out.println("Finished...");

						return duration;
					}
				});

		executor.shutdown();// Managerial thread of the pool shutdown when all
							// its threads
							// are done.

		// If we invoke future.get(), it blocks, so no need for this method
		// invocation.
		// executor.awaitTermination(timeout, unit)

		try {
			System.out.println("Result is: " + future.get());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			// Prints:java.util.concurrent.ExecutionException:
			// java.io.IOException: Sleeping for too long
			// The ExecutionException carries the message we've specified to the
			// IOException.
			System.out.println(e);

			// Prints java.io.IOException: Sleeping for too long
			// The ExecutionException carries the message we've specified to the
			// IOException.
			System.out.println(e.getMessage());

			// This is done to retrieve the original exception which is
			// IOException.
			// Prints Sleeping for too long
			IOException ex = (IOException) e.getCause();
			System.out.println(ex.getMessage());
		}
	}// End of main method
}// End of App class

