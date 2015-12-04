
import java.io.*;
import java.net.*;
import java.util.*;

public class client {
    public static void main(String[] args) throws IOException {

        Socket tcpSocket = null;
        PrintWriter socketOut = null;
        BufferedReader socketIn = null; 
        int port = 8989;
    	String type, name, version, user, answer = "yes";
        PrintWriter writer = new PrintWriter("response.htm");
        Scanner scanner = new Scanner(System.in);
        System.out.print("input DNS name: ");
        String address = scanner.nextLine();
            	
        while(answer.equals("yes")){
	        try {
	        	
				long first = System.currentTimeMillis();
	            tcpSocket = new Socket(address, port);
				long second = System.currentTimeMillis();
				
				System.out.print("\n\n"+(second-first)+"ms to connect\n\n");
				socketOut = new PrintWriter(tcpSocket.getOutputStream(), true);
	            socketIn = new BufferedReader(new InputStreamReader(tcpSocket.getInputStream()));
	        } catch (UnknownHostException e) {
	            System.err.println("Don't know about host: "+address);
	            System.exit(1);
	        } catch (IOException e) {
	            System.err.println("Couldn't get I/O for the connection to: "+ address);
	            System.exit(1);
	        }
			
			System.out.print("input the HTTP method Type: ");
			type = scanner.nextLine();
			System.out.print("input name of the htm file: ");
			name = scanner.nextLine();
			System.out.print("input the HTTP version: ");
			version = scanner.nextLine();
			System.out.print("input the User-Agent: ");
			user = scanner.nextLine();	
			
			String response, request = type+" "+name+" "+version+"\r\nHost: "+address+"\r\nUser-Agent: "+user+"\r\n\r\n";
			
			
			long one = System.currentTimeMillis();
	        socketOut.println(request);
	       	        
	        int count = 0, run = 0;
	        String[] splitter = null;
	        String status = null;
	        
	        while(count != 5){
	        	while((response = socketIn.readLine()) == null);
	        		if( run == 0 || run == 1 || run == 2){
	        			if(run == 0){
	        				long two = System.currentTimeMillis();
	        				
	        				System.out.println("\n\nRTT: "+(two-one));
	        				
	        				splitter = response.split(" ", 5);
	        				status = splitter[1]+" "+splitter[2];
	        			}
	        		
	        			System.out.println(response);
	        			run ++;
	        		
	        	}else if(status.equals("OK 200")){
	        		writer.print(response);
	        	}
	        	
	        	if(response.equals("")){
	        		count++;
	        	}else{
	        		count = 0;
	        	}
	        }
	        	
	        System.out.print("do you wish to continue?  ");
	        answer = scanner.nextLine();
	    }
        scanner.close();
        writer.close();
        socketOut.close();
        socketIn.close();
        tcpSocket.close();
    }
}