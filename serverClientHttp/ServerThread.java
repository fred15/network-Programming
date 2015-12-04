
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.io.*;

public class ServerThread extends Thread {
    private Socket clientTCPSocket = null;

    public ServerThread(Socket socket) {
		super("ServerThread");
		clientTCPSocket = socket;
    }

    public void run() {


        String[] splitter;
        String fromClient, version = null;
        File f = null;
    	
		try {
	 	   PrintWriter cSocketOut = new PrintWriter(clientTCPSocket.getOutputStream(), true);
	  		BufferedReader cSocketIn = new BufferedReader(
				    new InputStreamReader(
				    clientTCPSocket.getInputStream()));
	  		 int i = 0;
	  		 String status = "OK 200",file;
			  
		 	 while ((fromClient = cSocketIn.readLine()) != null) {
		 		 
					System.out.println(fromClient);
					splitter = fromClient.split(" ", 10);
					
					if(i == 0 && !splitter[0].equals("GET"))
					{
						status= "400 Bad Request";						
					}else if(i == 0){
									
						file = splitter[1];
						version = splitter[2];
						file = file.replace("/", "");
						f = new File(file);
						if(!f.exists()){
							status = "404 Not Found";
						}
					}	
					i++;		
					
					if(i==4){
						
				        final Date currentTime = new Date();
				        final SimpleDateFormat sdf =
				                new SimpleDateFormat("EEE, MMM d, yyyy hh:mm:ss a z");
				        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
				        
						
				        String response, line = null;
						response = version+" "+status+"\r\n"+"Date: "+currentTime+"\r\n"+"Server: 8989\r\n";
						
						
						if( status.equals("OK 200")){
							BufferedReader text  = new BufferedReader(new FileReader(f));
							while((line = text.readLine())!=null){
								response = response+line+"\r\n";
							}
							text.close();
						}
						
						response = response+"\r\n\r\n\r\n\r\n";
						
						cSocketOut.println(response);
						break;
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
