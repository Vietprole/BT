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

			// reads message from client until "Over" is sent
			while (true)//!line.equals("Over"))
			{
				try
				{
					line = in.readUTF();
                    StringBuilder reversed = new StringBuilder(line).reverse();
					String Upper = line.toUpperCase();
					String Lower = line.toLowerCase();
					StringBuilder UpperLower = new StringBuilder(line);
					for (int i = 0; i < line.length(); i++) {
					  if (i % 2 == 0) {
						UpperLower.append(line.charAt(i) ^ 32);
					  } else {
						UpperLower.append(line.charAt(i));
					  }
					}
					out.writeUTF("Server gui ve: \n" + reversed + "\n" + Upper + "\n" + Lower + "\n" + UpperLower.toString());
					System.out.println(out);

				}
				catch(IOException i)
				{
					System.out.println(i);
				}
			}
			//System.out.println("Closing connection");

			// close connection
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
