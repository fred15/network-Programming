
import java.net.*;
import java.io.*;

public class ServerThread extends Thread {
    private Socket clientTCPSocket = null;

    public ServerThread(Socket socket) {
		super("ServerThread");
		clientTCPSocket = socket;
    }
    
    public Server server = new Server();	//give you access to buffers and to methods to fill buffers later
    
    public void run() {
    	
	  	try
	  	{
			
			PrintWriter writer = new PrintWriter(System.out);
	 	  	PrintWriter outputstream = new PrintWriter(clientTCPSocket.getOutputStream(), true);
           	BufferedReader inputstream = new BufferedReader( new InputStreamReader( clientTCPSocket.getInputStream()));

	      	String fromFactory;

       		while((fromFactory = inputstream.readLine()) != null); //wait for message
       		
       		//message will probably say which product they need and then you need to send that product to them
           	
       		//if product doesnt exists halt thread and then call method bellow to call production server to get more product
       		
		   	outputstream.close();
		   	inputstream.close();
		   	clientTCPSocket.close();

		} catch (IOException e) 
	  	{
		    e.printStackTrace();
		}
    }
    
    public void fillBuffers(int index) //index should be which buffer needs filling basically a copy of client
    {
    	 try {

             int port = 8990;  
             String dns = "localhost";

             Socket tcpSocket = new Socket(dns, port);
 					

             BufferedReader bufferedreader = new BufferedReader( new InputStreamReader(System.in));
             BufferedWriter bufferedwriter = new BufferedWriter( new OutputStreamWriter(tcpSocket.getOutputStream()));
             BufferedReader socketIn = 	new BufferedReader( new InputStreamReader(tcpSocket.getInputStream()));

             String fromProductionServer;
             String toServer = "product1  110";
             
             bufferedwriter.write(toServer); 		//send initial request to server
            
             
             while((fromProductionServer = bufferedreader.readLine())!= null);	//while loop to continue listening form server
              
             //idk what else you want

             socketIn.close();
             bufferedwriter.close();
             tcpSocket.close(); 

         } catch (Exception exception) {
             exception.printStackTrace();
         }
    }
    
    
}
