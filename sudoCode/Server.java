
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;


public class Server extends Thread  {
	
	public ArrayList buffers = new ArrayList();

	public Server (int productsNumber, int bufferSize)
	{
		//create buffers for each product number
	}
	
    public Server() {
	}
    
	public void run() {
        ServerSocket serverTCPSocket = null;
        boolean listening = true;
        int port = 8989; 

        try {
            serverTCPSocket = new ServerSocket(port);;
        } catch (IOException e) {
            System.err.println("Could not listen on port: "+port);
            System.exit(-1);
        }

        while (listening){
	    		try {
					new ServerThread(serverTCPSocket.accept()).start();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
        try {
			serverTCPSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}