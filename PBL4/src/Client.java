
// A Java program for a Client
import java.io.*;
import java.net.*;

public class Client {
	// initialize socket and input output streams
	private Socket socket = null;
	// protected DataInputStream input = null;// from terminal
	protected DataInputStream in = null;// from server
	protected DataOutputStream out = null;

	// constructor to put ip address and port
	public Client(String address, int port) {
		// establish a connection
		try {
			socket = new Socket(address, port);
			System.out.println("Connected");

			// takes input from terminal
			// input = new DataInputStream(System.in);//new ByteArrayInputStream("1
			// 4".getBytes()));

			// takes input from Server
			in = new DataInputStream(socket.getInputStream());

			// sends output to the socket
			out = new DataOutputStream(
					socket.getOutputStream());
		} catch (UnknownHostException u) {
			System.out.println(u);
			return;
		} catch (IOException i) {
			System.out.println(i);
			return;
		}

		// string to read message from input
		String line = "";
		String line2 = "";

		try {
			DataInputStream fileinput = new DataInputStream(new FileInputStream(new File("E:/Viet/Lap trinh mang/BT/PBL4/src/input.txt")));
			while ((line = fileinput.readLine()) != null) {
				line2 += line + "\n";
			}
			out.writeUTF(line2.trim());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// keep reading until "Over" is input
		// while (true) {// !line.equals("Over")){
		// try {
		// line = input.readLine();
		// out.writeUTF(line);

		// System.out.println(in.readUTF());
		// } catch (IOException i) {
		// System.out.println(i);
		// }
		// }
	}

	// close the connection
	// try {
	// input.close();
	// out.close();
	// socket.close();
	// }
	// catch (IOException i) {
	// System.out.println(i);
	// }
	// }

	public static void main(String args[]) {
		Client client = new Client("172.21.20.90", 8080);
		// String line = "";
		// keep reading until "Over" is input
		// while (true) {//!line.equals("Over")){
		// try {
		// line = client.input.readLine();
		// client.out.writeUTF(line);

		// System.out.println(client.in.readUTF());
		// }
		// catch (IOException i) {
		// System.out.println(i);
		// }
		// }
	}
}
