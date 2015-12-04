
import java.io.*;
import java.net.*;
import java.util.*;

public class client {
    public static void main(String[] args) throws IOException {

        Socket tcpSocket = null;
        PrintWriter socketOut = null;
        BufferedReader socketIn = null; 
        int port = 8989;
    	String response, answer = "yes", toServer, sendEmail = null, recieveEmail=null, subject=null, contents=null;
        PrintWriter writer = new PrintWriter("response.htm");
        Scanner scanner = new Scanner(System.in);
        System.out.print("input DNS name: ");
        String address = scanner.nextLine();
        
        
            	

        try {
        	
            tcpSocket = new Socket(address, port);
			
			socketOut = new PrintWriter(tcpSocket.getOutputStream(), true);
            socketIn = new BufferedReader(new InputStreamReader(tcpSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: "+address);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: "+ address);
            System.exit(1);
        }

		while((response = socketIn.readLine()) == null );
		System.out.println(response);
		
		while(!answer.equals("QUIT")){
			System.out.print("input sender’s email address: ");
			sendEmail = scanner.nextLine();
			System.out.print("input receiver’s email address: ");
			recieveEmail = scanner.nextLine();
			System.out.print("input subject: ");
			subject = scanner.nextLine();
			System.out.print("input email contents: ");
			contents = scanner.nextLine() + "\r\n.\r\n";
				
			toServer = "HELO xyz.com\r\n";
			socketOut.println(toServer);
			while((response = socketIn.readLine()) == null );
			System.out.println(response);
			toServer = "MAIL FROM "+sendEmail+"\r\n";
			socketOut.println(toServer);
			while((response = socketIn.readLine()) == null );
			System.out.println(response);
			toServer = "RCPT TO "+recieveEmail+"\r\n";
			socketOut.println(toServer);
			while((response = socketIn.readLine()) == null );
			System.out.println(response);
			toServer = "DATA\r\n";
			socketOut.println(toServer);
			while((response = socketIn.readLine()) == null );
			System.out.println(response);
			toServer ="Subject: "+subject+"\r\n"+contents;
			socketOut.println(toServer);
			while((response = socketIn.readLine()) == null );
			System.out.println(response);
			
			
			
			System.out.print("do you want to continue? ");
			answer = scanner.nextLine();
			if(!answer.equals("yes")){
				socketOut.println("QHIT");
				break;
			}
		}
	    
        scanner.close();
        writer.close();
        socketOut.close();
        socketIn.close();
        tcpSocket.close();
    }
}