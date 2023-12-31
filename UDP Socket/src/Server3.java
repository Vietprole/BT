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
import java.net.UnknownHostException;

public class Server3 extends JFrame {
    private JPanel contentPane;
    private JTextArea textArea;
    private JTextField textField;
    private JButton button;
    private DatagramSocket serverSocket;
    private InetAddress IPAddress;
    private byte[] sendData = new byte[1024];
    private byte[] receiveData = new byte[1024];
    private String msg = "";
    private int port;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Server3 frame = new Server3();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Server3() {
        setTitle("Chat - server");
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
        contentPane.setBounds(150, 150, 700, 350);
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
            // tao datagram co noi dung yeu cau loai du lieu de gui cho client
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
            serverSocket.send(sendPacket);// gui du lieu cho client
            textArea.append("Client: " + msg + "\n");
            System.out.println("Client: " + msg + "\n");
            textField.setText("");

        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public void formWindowOpened(java.awt.event.WindowEvent evt) throws IOException {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    serverSocket = new DatagramSocket(9876);
                    System.out.println("Server is started");
                    textArea.append("Server is started!" + "\n");
                    receiveData = new byte[1024];
                } catch (SocketException e) {
                    e.printStackTrace();
                }
                while (true) {
                    try {
                        // tao datagram rong de nhan du lieu
                        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                        // nhan du lieu tu client
                        serverSocket.receive(receivePacket);
                        IPAddress = receivePacket.getAddress();
                        port = receivePacket.getPort();
                        // lay du lieu tu packet nhan duoc
                        String str = new String(receivePacket.getData(),0,receivePacket.getLength());
                        str = str.trim();
                        textArea.append("Client: " + str + "\n");
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
