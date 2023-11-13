// A Java program for a Client
import java.io.*;
import java.net.*;

public class Client {
	// initialize socket and input output streams
	private Socket socket = null;
	private DataInputStream input = null;//from terminal
	private DataInputStream in = null;//from server
	private DataOutputStream out = null;

	// constructor to put ip address and port
	public Client(String address, int port)
	{
		// establish a connection
		try {
			socket = new Socket(address, port);
			System.out.println("Connected");

			// takes input from terminal
			input = new DataInputStream(System.in);

			// takes input from Server
			in = new DataInputStream(socket.getInputStream());

			// sends output to the socket
			out = new DataOutputStream(
				socket.getOutputStream());
		}
		catch (UnknownHostException u) {
			System.out.println(u);
			return;
		}
		catch (IOException i) {
			System.out.println(i);
			return;
		}

		// string to read message from input
		String line = "";

		// keep reading until "Over" is input
		while (true) {//!line.equals("Over")){
			try {
				line = input.readLine();
				out.writeUTF(line);
				
				System.out.println(in.readUTF());
			}
			catch (IOException i) {
				System.out.println(i);
			}
		}

		// close the connection
		// try {
		// 	input.close();
		// 	out.close();
		// 	socket.close();
		// }
		// catch (IOException i) {
		// 	System.out.println(i);
		// }
	}

	public static void main(String args[])
	{
		Client client = new Client("localhost", 5500);
	}
}
