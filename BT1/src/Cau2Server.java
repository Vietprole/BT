import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class Cau2Server {
	private static List<ClientHandler> clients = new ArrayList<>();

	public static void main(String[] args) {

		int port = 5500;
		
		ServerSocket serverSocket = null;

	    try {
	        serverSocket = new ServerSocket(port);
	        System.out.println("Server is listening on port " + port);

	        while (true) {
	            Socket clientSocket = serverSocket.accept();
	            //System.out.println("Client connected: " + clientSocket.getInetAddress().getHostAddress());
	            ClientHandler clientHandler = new ClientHandler(clientSocket);
	            clients.add(clientHandler);
	            Thread clientThread = new Thread(clientHandler);
	            clientThread.start();
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	}
	
	private static class ClientHandler implements Runnable {

		private Socket socket;
		DataOutputStream dos;
		DataInputStream dis;
		
		public ClientHandler(Socket socket) {
			this.socket = socket;
		}
		
		@Override
		public void run() {
			
			try {
				dos = new DataOutputStream(socket.getOutputStream());
				dis = new DataInputStream(socket.getInputStream());
				
				String st ="";
                while ((st = dis.readUTF())!=null) {
                	
                	if(st.equals("Disconnect")) {
                		socket.close();
                	}else {
                    	
                        System.out.println("Client: " + st);
                        int Number = Integer.parseInt(st);
                        
                        String message = new String();
                        
                        if(isBinarySymmetric(Number) == true) {
                        	message = st +" La mot so doi xung";
							message += "\n" + "Server yeu cau Client ngung gui";
                        	dos.writeUTF(message);

							//socket.close();
                        }else {
                        	message =st + " Khong phai la mot so doi xung";
                        	dos.writeUTF(message);
                        }
                        System.out.println(message);
                	}

                }
				
			} catch (IOException e) {


			}
			
		}

	}
	
    public static boolean isBinarySymmetric(int n) {
        // Định dạng số nhị phân với độ dài cố định là 8 bit
        String binaryRepresentation = String.format("%8s", Integer.toBinaryString(n)).replace(' ', '0');

        // Chuyển đổi biểu diễn nhị phân thành mảng ký tự
        char[] binaryArray = binaryRepresentation.toCharArray();

        // Kiểm tra đối xứng
        int length = binaryArray.length;
        for (int i = 0; i < length / 2; i++) {
            if (binaryArray[i] != binaryArray[length - 1 - i]) {
                return false;
            }
        }

        return true;
    }

}
