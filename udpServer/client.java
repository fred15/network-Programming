
import java.util.Scanner;
import java.io.*;
import java.net.*;

public class client {
	
	public static void main(String[] args) throws IOException{

		int port = getport();
       	String dns = getDNS();
		DatagramSocket udpSocket = new DatagramSocket();

        BufferedReader sysIn = new BufferedReader(new InputStreamReader(System.in));
        String fromServer,fromUser, ID, Des = "", Price, Inventory;  
        String[] splitter;
        

		PrintWriter writer = new PrintWriter("testResultsClient.txt");
        
        
       	System.out.print("\n\nItem ID	Item Description\n"+
       					 "00001		New Inspiron 15\n"+
       					 "00002		New Inspiron 17\n"+
       					 "00003		New Inspiron 15R\n"+
       					 "00004		New Inspiron 15z Ultrabook\n"+
       					 "00005		XPS 14 Ultrabook\n"+
       					 "00006		New XPS 12 UltrabookXPS\n\n");
        	

        System.out.print("Enter item ID: ");
        
        while ((fromUser = sysIn.readLine()) != null) {

        	fromUser = checkID(fromUser);

	        // send request
        	InetAddress address = InetAddress.getByName(dns);
			byte[] buf = fromUser.getBytes();
			DatagramPacket udpPacket = new DatagramPacket(buf, buf.length, address,port);
			
			long first = System.currentTimeMillis();
			
			udpSocket.send(udpPacket);
	
	        // get response
		    byte[] buf2 = new byte[256];
		    DatagramPacket udpPacket2 = new DatagramPacket(buf2, buf2.length);
		    udpSocket.receive(udpPacket2);
		    
		    long second = System.currentTimeMillis();
		    
		    
		    fromServer = new String(udpPacket2.getData(), 0, udpPacket2.getLength());
		    
		    splitter = fromServer.split(" ", 10);
		    ID = splitter[0];
		    int size = splitter.length -1;
		    Inventory = splitter[size];
		    size--;
		    Price = splitter[size];
		    for(int i = 1; i < size; i++){
		    	Des = Des + splitter[i]+" ";
		    }
		    
		    long time = (second - first);
		    

		    System.out.println("Item ID\t\tItem Description\t\tUnit Price\t\tRTT of Query");
		    if(ID.equals("00001")||ID.equals("00002")||ID.equals("00003")||ID.equals("00005"))	{
				System.out.println(ID+"\t\t"+Des+"\t\t\t"+Price+"\t\t\t"+time);
				writer.println(ID+"\t\t"+Des+"\t\t"+Price+"\t\t\t"+time);
				
		    }else{
				System.out.printf(ID+"\t\t"+Des+"\t"+Price+" \t\t"+ time);
				writer.println(ID+"\t\t"+Des+"\t"+Price+" \t\t"+ time);
		    }
		    
		    
		    
		    System.out.print("\n\nWould you like to search another Item? ");
		    Scanner sc = new Scanner(System.in);
		    String response = sc.nextLine();
		    if(!response.equals("yes")){
		    	break;
		    }
		    
	        System.out.print("Enter item ID: ");
		    Des = "";
		    

 	  }
	writer.close();
    udpSocket.close();
}
        
    public static int getport(){

        System.out.print("enter socket port number: ");
        Scanner sc = new Scanner(System.in);
        int i = sc.nextInt();
        int length = String.valueOf(i).length();
        while(length != 4){
            System.out.print("wrong length, try again: ");
            i = sc.nextInt();
            length = String.valueOf(i).length();
        }

        System.out.println("correct input\n");
        return i;
    }    
    public static String getDNS(){

        System.out.print("enter socket desired DNS: ");
        Scanner sc = new Scanner(System.in);
        String dns = sc.nextLine();
        return dns;

    }    
    public static String checkID(String i){
    	
        switch (i) {
        case "00001":	break;
        case "00002":	break;
        case "00003":	break;
        case "00004":	break;	
        case "00005":	break;
        case "00006": 	break;
        default:	System.out.println("no such ID exists");
        			System.out.print("enter item ID number: ");
        			Scanner sc = new Scanner(System.in);
        	        i = sc.nextLine();
        	        sc.close();
        			i = checkID(i);
        
        }
        return i;
    }
}
