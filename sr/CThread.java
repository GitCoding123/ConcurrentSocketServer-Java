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
public class CThread implements Runnable{
	
	private Socket client;
	private BufferedReader in;
	private BufferedReader command;
	private PrintWriter out;
	private String data;
	private C c = new C();
	
	/**
	 * 
	 * @param client
	 * @throws IOException
	 */
	public CThread(Socket client) throws IOException {
		this.client = client;
		in = new BufferedReader(new InputStreamReader(client.getInputStream()));
		command = new BufferedReader(new InputStreamReader(System.in));
		out = new PrintWriter(client.getOutputStream(), true);
	}
	
	/**
	 * 
	 */
	@Override
	public void run() {
		String string = "";
		String menuOption = c.getMenuOption();
		
		out.println(menuOption);  // write out menuOption to server
		out.flush();
		try {
		while((data = in.readLine()) != null) {
			if(data.endsWith("[END]")) {
				break;
			} // end if
					
			string = string + data + "\n";   //add info to string line by line
		}// end while
		} catch (IOException e) {
			System.out.println("This is IOException in CThread.run()");
		} // end catch
		System.out.println(string); //print info to user
	} // end run
} // end class
