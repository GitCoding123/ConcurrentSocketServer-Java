import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * 
 * @author bgerk
 *
 */
public class C {
	//private static Thread threads;
	private static Thread thread;
	private Socket socket;
//	private static int SERVER_PORT;
//	private static String SERVER_IP;
	private static String menuOption;
	private static int numR;
	private static int SERVER_PORT;
	private static String SERVER_IP;

	/**
	 * 
	 * @param args
	 * @throws IOException
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws IOException {
		
		String numRequests;
		long startTime = 0;
		long endTime = 0;
		long totalTime;
		long avgTime;
		long total = 0;
	
		
		SERVER_IP = args[0];
		SERVER_PORT = Integer.parseInt(args[1]);
        
		
		Socket socket = new Socket(SERVER_IP, SERVER_PORT);
		BufferedReader userIn = new BufferedReader(new InputStreamReader(System.in));
		while(true) {
		
		
		
			printMenu();	//Print menu to user
			System.out.println("> ");
		
			menuOption = userIn.readLine();	//Get menu option from user
		
			// If user enters 7, exit
			if(menuOption.equals("7")) {
				System.out.println("\nProgram terminated.\nHave a nice day!");
				socket.close();
				System.exit(0);
			}//end if
		
		//ask user how many requests to generate
		System.out.println("\nHow many requests would you like to generate? 1, 5, 10, 15, 20, 25?");   // ask for number of user requests
		numRequests = userIn.readLine(); //retrieve number of requests form user
		numR = Integer.parseInt(numRequests);	//Parse string to int for number of requests
		
		Thread[] thread = new Thread[numR];	//Create an array of threads with size of number of requests
		
		// Add new threads in array
		for(int i=0; i<numR; i++) {
			thread[i] = new Thread(new CThread(socket));
		}
		
		startTime = System.currentTimeMillis();	//Start timer
			
		//Start threads
		for(int i=0; i<numR; i++) {	
			thread[i].start();
			try {
				thread[i].join();
			} catch (InterruptedException e) {
				System.out.println("This is .join() exception for C.java");
			}
		}
		
		endTime = System.currentTimeMillis();
		total = endTime - startTime;
		avgTime = total / numR;	//Get average time
		
		System.out.println("\nThe total turn-around time is " + total + " ms");
		System.out.println("\nThe average turn-around time is " + avgTime + " ms");
		} // end while 
	} // end main
	
	/**
	 * 
	 */
	 public static void printMenu() {
	    	System.out.println();
	    	System.out.println("Please enter one of the following number commands: ");
	    	System.out.println("1.) Host Current Date/Time");
	    	System.out.println("2.) Host Uptime");
	    	System.out.println("3.) Host Memory Use");
	    	System.out.println("4.) Host Netstat");
	    	System.out.println("5.) Host Current Users");
	    	System.out.println("6.) Host Running Processes");
	    	System.out.println("7.) Exit");
	    } // end printMenu()

	 /**
	  * 
	  * @return
	  */
	 public String getMenuOption() {
		 return menuOption;
	 }// end getMenuOption()

} // end class
