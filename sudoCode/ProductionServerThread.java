
import java.net.*;
import java.io.*;

public class ProductionServerThread extends Thread {
    private Socket clientTCPSocket = null;

    public ProductionServerThread(Socket socket) {
		super("ServerThread");
		clientTCPSocket = socket;
    }

    public void run() {

	  	try{
			
			PrintWriter writer = new PrintWriter(System.out);
	 	  	PrintWriter outputstream = new PrintWriter(clientTCPSocket.getOutputStream(), true);
           	BufferedReader inputstream = new BufferedReader( new InputStreamReader( clientTCPSocket.getInputStream()));

        	String fromFactory;

       		while((fromFactory = inputstream.readLine()) != null); //wait for message
       		
       		//message will probably say which product they need and then you need to send that product to them
           	

		   	outputstream.close();
		   	inputstream.close();
		   	clientTCPSocket.close();

		} catch (IOException e) {
		    e.printStackTrace();
		}
    }
}
