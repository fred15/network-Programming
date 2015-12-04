
import java.io.*;
import java.net.*;
import java.util.*;

public class Client extends Thread{
	
	public int numberOfProducts = 0;
	public int productNumber;
	
	public Client(int productsNumber){

		Random rand = new Random(productsNumber);
		Random randProducts = new Random(1000); //how many to buy
		
		
		productNumber = rand.nextInt();
		numberOfProducts = randProducts.nextInt();
		
	}
	
    public void run() {

          try {

            int port = 8989;  
            String dns = "localhost";

            Socket tcpSocket = new Socket(dns, port);
					

            BufferedReader bufferedreader = new BufferedReader( new InputStreamReader(System.in));
            BufferedWriter bufferedwriter = new BufferedWriter( new OutputStreamWriter(tcpSocket.getOutputStream()));
            BufferedReader socketIn = 	new BufferedReader( new InputStreamReader(tcpSocket.getInputStream()));

            String fromServer;
            String toServer = "product1  110";
            
            bufferedwriter.write(toServer); 		//send initial request to server
           
            
            while((toServer = bufferedreader.readLine())!= null);	//while loop to continue listening form server
             
            //idk what else you want

            socketIn.close();
            bufferedwriter.close();
            tcpSocket.close(); 

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}