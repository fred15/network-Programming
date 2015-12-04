
import java.net.*;
import java.io.*;

public class serverThread extends Thread {
    private Socket clientTCPSocket = null;

    public serverThread(Socket socket) {
		super("ServerThread");
		clientTCPSocket = socket;
    }

    public void run() {


        String[] splitter;
        String fromClient, toClient;
        
    	
		try {
	 	   	PrintWriter cSocketOut = new PrintWriter(clientTCPSocket.getOutputStream(), true);
	  		BufferedReader cSocketIn = new BufferedReader(
				    new InputStreamReader(
				    clientTCPSocket.getInputStream()));
	  		 
	  		toClient = "200 random.edu\r\n";
	  		cSocketOut.println(toClient);
	  		for(int i = 0; i< 5;i++){
	  			while ((fromClient = cSocketIn.readLine()) == null);
	  			splitter = fromClient.split(" ");
	  			
	  			System.out.println(fromClient);
	  			
	  			if(fromClient.equals("QHIT")){
	  				break;
	  			}
	  			
	  			if(i == 0){
	  				if(splitter[0].equals("HELO")){
	  					toClient = "250 random.edu Hello "+splitter[1];			
	  				}else{
	  					toClient = "503 5.5.2 Send hello first";
	  					i--;
	  				}	  		
	  				cSocketOut.println(toClient);
	  			}else if(i== 1){
	  				if(splitter[0].equals("MAIL") && splitter[1].equals("FROM")){
	  					toClient = "250 2.1.0 Sender OK";
	  				}else{
	  					toClient = "503 5.5.2 Need mail command";
	  					i--;
	  				}
	  				cSocketOut.println(toClient);
	  			}else if(i==2){
	  				if(splitter[0].equals("RCPT") && splitter[1].equals("TO")){
	  					toClient = "250 2.1.5 Recipient OK";
	  				}else{
	  					toClient = "503 5.5.2 Need rcpt command";
	  					i--;
	  				}
	  				cSocketOut.println(toClient);
	  			}else if(i==3){
	  				if(splitter[0].equals("DATA")){
	  					toClient = "354 Start mail input; end with <CRLF>.<CRLF>";
	  				}else{
	  					toClient = "503 5.5.2 Need data command";
	  					i--;
	  				}
	  				cSocketOut.println(toClient);
	  				
	  			}else if(i==4){
	  				if(!fromClient.equals(".")){
	  					i--;
	  				}else{
		  				cSocketOut.println("250 Message received and to be delivered");
		  				i = -1;
	  				}
	  			}
	  		}
	  		cSocketOut.close();
	  		cSocketIn.close();
	  		clientTCPSocket.close();

		} catch (IOException e) {
		    e.printStackTrace();
		}
    }
}
