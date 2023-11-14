import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Server1 {
    public static void main(String[] args) throws Exception {
        DatagramSocket serverSocket = new DatagramSocket(9876);
        // Tao cac mang byte de chua du lieu gui va nhan
        System.out.println("Server is started");
        byte[] receiveData = new byte[1024];
        byte[] sendData = new byte[1024];
        while (true) {
            // Tao goi rong de nhan du lieu tu client
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            // Nhan du lieu tu client
            serverSocket.receive(receivePacket);
            // Lay dia chi IP cua may client
            InetAddress IPAddress = receivePacket.getAddress();
            // Lay port cua chuong trinh client
            int port = receivePacket.getPort();
            // Lay ngay gio de gui nguoc lai cho client
            String request = new String(receivePacket.getData());
            System.out.println(request);
            request = request.trim();
            String feedback = "Reversed: " + ReverseString(request) + "\n";
            feedback += "Uppercased: " + request.toUpperCase() + "\n";
            feedback += "Lowercased: " + request.toLowerCase() + "\n";
            feedback += "Upper + Lower: " + toggleCaseSequentially(request) + "\n";
            feedback += "Vowel count: " + countVowels(request);
            sendData = feedback.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
            // Gui du lieu lai cho client
            serverSocket.send(sendPacket);
        }
    }

    public static String ReverseString(String st) {
        return new StringBuilder(st).reverse().toString();
    }

    public static String toggleCaseSequentially(String input) {
        char[] charArray = input.toCharArray();
        boolean isUpperCase = true;

        for (int i = 0; i < charArray.length; i++) {
            char c = charArray[i];
            if (Character.isLetter(c)) {
                if (isUpperCase) {
                    charArray[i] = Character.toLowerCase(c);
                } else {
                    charArray[i] = Character.toUpperCase(c);
                }
                isUpperCase = !isUpperCase;
            }
        }

        return new String(charArray);
    }

    public static int countVowels(String input) {
        input = input.toLowerCase();
        int count = 0;
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u') {
                count++;
            }
        }
        return count;
    }

    public static int countDigits(String input) {
        int count = 0;
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (Character.isDigit(c)) {
                count++;
            }
        }
        return count;
    }
}
