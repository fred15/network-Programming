
import java.io.IOException;
import java.net.*;


public class server {
    public static void main(String[] args) throws IOException {
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
	    		new serverThread(serverTCPSocket.accept()).start();
		}
        serverTCPSocket.close();
    }
}