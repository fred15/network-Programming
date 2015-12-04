


public class MainClass {

	public static void main(String[] args)
	{
		//you have to get the scanner to get inputs then actually feed them into the constructors for
		// the servers bellow, then create a 
		
		int numberOfClients = 10;
		int productsNumber = 5;
		int bufferSize = 1000;
		
		Server server = new Server(productsNumber, bufferSize);
		Client client = new Client(productsNumber);
		ProductionServer pServer = new ProductionServer(productsNumber);
		
		server.start();
		pServer.start();
		
		for(int i = 0; i< numberOfClients; i++)
		{
			client.start();
		}
		
	}
}
