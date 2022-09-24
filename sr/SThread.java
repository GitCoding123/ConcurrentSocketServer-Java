import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * 
 * @author bgerk
 *
 */
public class SThread implements Runnable {
		private Socket client;
		public int clientNum;
		static SThread s;
		
	/**
	 * 	
	 * @param client
	 * @param clientNum
	 */
	public SThread(Socket client, int clientNum) {
		this.client = client;
		this.clientNum = clientNum;
	} // end constructor
	
	/**
	 * 
	 */
	@Override
	public void run() {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(this.client.getInputStream()));
			PrintWriter out = new PrintWriter(this.client.getOutputStream(), true);
			while(true) {				
				String menuOption = in.readLine(); // read in menuOption
				String data = getProcess(menuOption); //retrieve data
				
				out.println(data + "[END]");  //write data to client
				out.flush();
				System.out.println("\n[SERVER] Waiting for client connection...");
			}//end while
			} catch(IOException e) {
					System.out.println("This is IOException for SThread.run()");
				} // end catch
			catch(NullPointerException e) {
				System.out.println("\n[SERVER] Client has left!");
				System.out.println("\n[SERVER] Waiting for client connection...");
				return;
			} //end catch	
	} // end run
	
	/**
	 * 
	 * @param menuOption
	 * @return
	 * @throws IOException
	 */
	public static String getProcess(String menuOption) throws IOException {
		switch(menuOption) {
			case "1":
				System.out.println("\n[SERVER] Sending date to client...");
				return getData("date");
				//return "This is date test";
			case "2":
				System.out.println("\n[SERVER] Sending uptime to client...");
				return getData("uptime");
				//return "This is uptime test";
			case "3":
				System.out.println("\n[SERVER] Sending memory use to client...");
				return getData("cat /proc/meminfo");
				//return "This is memory test";
			case "4":
				System.out.println("\n[SERVER] Sending netstat to client...");
				return getData("netstat");
				//return "This is netstat test";
			case "5":
				System.out.println("\n[SERVER] Sending current users to client...");
				return getData("w");
				//return "This is users test";
			case "6":
				System.out.println("\n[SERVER] Sending running processes to client...");
				return getData("ps -A");
				//return "This is processes test";
			
			default: 
				return "Incorrect Entry. Please choose ";
		}  //end switch case
	}  //end method
	
	/**
	 * 
	 * @param command
	 * @param numCommands
	 * @return
	 * @throws IOException 
	 */
	private static String getData(String command) {
		//int numR = Integer.parseInt(numRequests);
		String data = "";
		
		String nextLine;
		Process process;
		BufferedReader reader = null;
		
		try { 

			process = Runtime.getRuntime().exec(command);

			reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

			while ((nextLine = reader.readLine()) != null) {
				data = data + nextLine + "\n";
				
			}

		} catch (IOException e) {
			System.out.println("Oops, We've encountered an error :(");
			e.printStackTrace();
		} // end try/catch
		try {
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}// end getInput
	
}


