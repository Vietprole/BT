import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import java.awt.EventQueue;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Server1 extends JFrame {
    private JPanel contentPane;
    private JTextArea textArea;

    private ServerSocket server;
    private Socket serverSocket;
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Server1 frame = new Server1();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public Server1(){
        setTitle("Xu ly chuoi - server");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 700, 450);

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                try {
                    formWindowOpened(evt);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        contentPane = new JPanel();
        contentPane.setBounds(150,150,700,350);
        contentPane.setLayout(null);
        setContentPane(contentPane);
		
		textArea = new JTextArea();
		textArea.setBounds(10, 11, 621, 343);
		contentPane.add(textArea);

    }
    
    public void formWindowOpened(java.awt.event.WindowEvent evt) throws IOException{
        server = new ServerSocket(5500);
        textArea.append("Server: Server started" + "\n");
        textArea.append("Server: Waiting for client..." + "\n");
        System.out.println("Server started");
        System.out.println("Waiting for a client ...");

        serverSocket = server.accept();
        DataInputStream in = new DataInputStream(serverSocket.getInputStream());
        DataOutputStream out = new DataOutputStream(serverSocket.getOutputStream());

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        String request = in.readUTF();
                        textArea.append("Client: " + request + "\n");
                        String feedback = "Reversed: " + ReverseString(request) + "\n";
                        feedback += "Uppercased: " + request.toUpperCase() + "\n";
                        feedback += "Lowercased: " + request.toLowerCase() + "\n";
                        feedback += "Upper + Lower: " + toggleCaseSequentially(request) + "\n";
                        feedback += "Vowel count: " + countVowels(request) + "\n";
                        out.writeUTF(feedback);
                        System.out.println(request);
                    } catch (IOException i) {
                        System.out.println(i);
                    }
                }
            }
        });
        t.start(); 
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
