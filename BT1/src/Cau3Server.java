import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;
import java.util.List;

public class Cau3Server {

	private static List<Integer> Port = new ArrayList<Integer>();
	private static int port = 5500;
	private static DatagramSocket serverSocket;

	public static void main(String[] args) throws IOException {

		serverSocket = new DatagramSocket(port);

		System.out.println("Server is listening " + port);

		while (true) {
			byte[] receiveData = new byte[2048];
			byte[] sendData = new byte[2048];
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

			serverSocket.receive(receivePacket);

			if (!Port.contains(receivePacket.getPort())) {
				Port.add(receivePacket.getPort());
			}
			
			clientHandler client = new clientHandler(serverSocket, receivePacket, receivePacket.getPort());
			Thread ClientThread = new Thread(client);
			ClientThread.start();
		}
	}

	public static class clientHandler implements Runnable {

		DatagramSocket ClientSocket;
		DatagramPacket receivePacket;
		int port;

		public clientHandler(DatagramSocket ServerSocket, DatagramPacket receivePacket, int port) {
			this.ClientSocket = ServerSocket;
			this.receivePacket = receivePacket;
			this.port = port;
		}

		public void run() {

			String message = new String(receivePacket.getData()).trim();
			System.out.println(message);

			int Number = Integer.parseInt(message);
			String rs = checkAndPrintFibonacciSequence(Number);

			byte[] sendData = new byte[1024];
			byte[] receiveData = new byte[1024];
			sendData = rs.getBytes();
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, receivePacket.getAddress(), port);
			try {
				ClientSocket.send(sendPacket);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public static String checkAndPrintFibonacciSequence(int n) {
		if (isFibonacciNumber(n)) {
			if (n == 1){
				return n + " xuất hiện ở vị trí thứ 2 và 3 trong dãy Fibonacci." + "\n" + "Server yeu cau Client ngung gui";
			}
			int position = findFibonacciPosition(n);
			return n + " xuất hiện ở vị trí thứ " + position + " trong dãy Fibonacci." + "\n" + "Server yeu cau Client ngung gui";
		} else {
			return n + " không thuộc dãy Fibonacci.";
		}
	}

	public static boolean isFibonacciNumber(int n) {
		if (n <= 1) {
			return true;
		}

		int a = 0, b = 1;
		while (b < n) {
			int temp = a + b;
			a = b;
			b = temp;
		}

		return b == n;
	}

	public static int findFibonacciPosition(int n) {
		int position = 0;
		int a = 0, b = 1;

		while (a <= n) {
			position++;
			if (a == n) {
				return position;
			}

			int temp = a + b;
			a = b;
			b = temp;
		}

		return -1; // Trả về -1 nếu không tìm thấy
	}

}
