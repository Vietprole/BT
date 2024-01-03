// A Java program for a Server
import java.net.*;
import java.io.*;

public class Server
{
	//initialize socket and input stream
	private Socket		 socket = null;
	private ServerSocket server = null;
	private DataInputStream in	 = null;
	private DataOutputStream out = null;

	// constructor with port
	public Server(int port)
	{
		// starts server and waits for a connection
		try
		{
			server = new ServerSocket(port);
			//server = new ServerSocket(8080, 0, InetAddress.getByName("10.10.52.5"));
			System.out.println("Server started");

			System.out.println("Waiting for a client ...");

			socket = server.accept();
			System.out.println("Client accepted");

			// takes input from the client socket
			in = new DataInputStream(
				new BufferedInputStream(socket.getInputStream()));
			// push output to the client socket
			out = new DataOutputStream(socket.getOutputStream());
			String line = "";
			String line2 = "";

			line2 = in.readUTF();
			System.out.println(line2);
			int nodes = Integer.parseInt(line2.substring(0,1));

			 //reads message from client until "Over" is sent
			 while (true)//!line.equals("Over"))
			 {
			 	try
			 	{
			 		line = in.readUTF();
			 		int start = Integer.parseInt(line.substring(0,1));
			 		int end = Integer.parseInt(line.substring(2,3));
					String output = Dijkstra.myDijkstra(start, end, line2);
					for (int i = 1; i <= nodes; i++){
						String[] out = Dijkstra.myDijkstra(start, i, line2).split("\n", 2);
						output += "\n" + out[1];
					}
					out.writeUTF(output);
			 		System.out.println(output);
			 	}
			 	catch(IOException i)
			 	{
			 		System.out.println(i);
			 	}
			 }
			//System.out.println("Closing connection");

			 //close connection
			//socket.close();
			//in.close();
		}
		catch(IOException i)
		{
			System.out.println(i);
		}
	}

	public static void main(String args[])
	{
		Server server = new Server(5500);
	}
}
