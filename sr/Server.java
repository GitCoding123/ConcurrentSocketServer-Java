import java.io.*;
import java.lang.management.RuntimeMXBean;
import java.lang.management.ManagementFactory;
import java.net.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
 
/**
 * 
 * @author bgerk
 *
 */
public class Server {
	public static ArrayList<SThread> clients = new ArrayList<>();
	private static ExecutorService pool = Executors.newFixedThreadPool(100);
	public static int i = 1;

	/**
	 * 
	 * @param args
	 * @throws IOException
	 * @throws InterruptedException 
	 */
    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
        	return;
        }
        
        int port = Integer.parseInt(args[0]);			//Get port number
        ServerSocket serverSocket = new ServerSocket(port);       //Create socket for server
        System.out.println("\nServer is listening on port " + port);       
        
        while(true) {
        	System.out.println("\n[SERVER] Waiting for client connection...");
           	Socket socket = serverSocket.accept();   
           	
           	System.out.println("\n[SERVER] Connected to client " + i + socket.getRemoteSocketAddress().toString());
           	
           	SThread sThread = new SThread(socket, i);
           	clients.add(sThread);
           	pool.execute(sThread);
           	i++;
        }  //end while
    }  // end main
    
}  //end program
