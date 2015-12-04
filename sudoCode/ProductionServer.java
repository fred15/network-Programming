
import java.io.IOException;
import java.net.*;


public class ProductionServer extends Thread  {

	public ProductionServer(int productsNumber) {
		//create an array of the number of products to send 
	}
    public void run() {
        ServerSocket serverTCPSocket = null;
        boolean listening = true;
        int port = 8990; 

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