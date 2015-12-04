import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;


public class server {
    public static void main(String[] args) throws IOException {
    	
    	String[][] info = new String[][]{
			  { "00001", "New Inspiron 15", "$379.99", "157" },
			  { "00002", "New Inspiron 17", "$449.99", "128"  },
			  { "00003", "New Inspiron 15R", "$549.99", "202"  },
			  { "00004", "New Inspiron 15z Ultrabook", "$749.99", "315" },
			  {	"00005", "XPS 14 Ultrabook", "$999.99", "261" },
			  { "00006", "New XPS 12 UltrabookXPS", "$1199.99","178" }
		};
    	
    	DatagramSocket udpServerSocket = null;
        DatagramPacket udpPacket = null, udpPacket2 = null;
		String fromClient = null, ID = null, Description = null, Price = null, Inv = null;
        boolean morePackets = true;

		byte[] buf = new byte[256];
		
		int Port = getport();
		udpServerSocket = new DatagramSocket(Port);
    	
		while (morePackets) {
            try {

                // receive UDP packet from client
                udpPacket = new DatagramPacket(buf, buf.length);
                udpServerSocket.receive(udpPacket);

				fromClient = new String(udpPacket.getData(), 0, udpPacket.getLength());
				
				
				int index = Integer.parseInt(fromClient)-1;
							
				// get the response
				ID = info[index][0];
				Description = info[index][1];
				Price = info[index][2];
				Inv = info[index][3];
				String toClient = ID+" "+Description+" "+Price+" "+Inv;	 
				// send the response to the client at "address" and "port"
                InetAddress address = udpPacket.getAddress();
                int port = udpPacket.getPort();
                
                
				byte[] buf2 = toClient.getBytes();
                udpPacket2 = new DatagramPacket(buf2, buf2.length, address, port);
                udpServerSocket.send(udpPacket2);

            } catch (IOException e) {
                e.printStackTrace();
					 morePackets = false;
            }
        }
		  
        udpServerSocket.close();

    	
	
	
	
	
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
	
}
