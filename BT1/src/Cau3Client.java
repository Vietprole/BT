import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import java.awt.EventQueue;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;


public class Cau3Client extends JFrame {
    private JPanel contentPane;
    private JTextArea textArea;
    private JTextField textField;
    private JButton button;
    private DatagramSocket clientSocket;
    private String msg = "";
    private InetAddress IPAddress;
    private byte[] sendData = new byte[1024];
    private byte[] receiveData = new byte[1024];
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Cau3Client frame = new Cau3Client();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public Cau3Client(){
        setTitle("Fibonaci - client");
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
		
		textField = new JTextField();
		textField.setBounds(10, 365, 516, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textArea = new JTextArea();
		textArea.setBounds(10, 11, 621, 343);
		contentPane.add(textArea);

        button = new JButton("Send");
        button.setBounds(542, 364, 89, 23);
        button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSendActionPerformed(evt);
            }
        });
        contentPane.add(button);
    }
    private void jButtonSendActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButtonSendActionPerformed'
    try {
        String message = textField.getText();
        msg = message;
        sendData = new byte[1024];
        sendData = msg.getBytes();
        IPAddress= InetAddress.getByName("localhost");
        //tao datagram co noi dung yeu cau loai du lieu de gui cho client
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 5500);
        clientSocket.send(sendPacket);//gui du lieu cho server
        textArea.append("Client: " + message + "\n");
        System.out.println("Client: " + message + "\n");
        textField.setText("");

    } catch (IOException e1) {
        e1.printStackTrace();
    }
    }
    public void formWindowOpened(java.awt.event.WindowEvent evt) throws IOException{


        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    clientSocket = new DatagramSocket();

                    System.out.println("client is started");
                    textArea.append("Client is started!" + "\n");
                    
                } catch (SocketException e) {
                    e.printStackTrace();
                }
                
                sendData = new byte[1024];
                while (true) {
                    try {
                        //tao datagram rong de nhan du lieu
                        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                        //nhan du lieu tu server
                        clientSocket.receive(receivePacket);
                        //lay du lieu tu packet nhan duoc
                        String str = new String(receivePacket.getData(),0,receivePacket.getLength());
                        str = str.trim();
                        textArea.append("Server: " + str + "\n");
                        if(str.endsWith("Server yeu cau Client ngung gui")) {
                            textArea.append("Ngat ket noi den server" + "\n");
                            
                            clientSocket.close();
                        }
                        System.out.println(str);
                    } catch (IOException i) {
                        System.out.println(i);
                    }
                }
            }
        });
        t.start(); 
    }
}

