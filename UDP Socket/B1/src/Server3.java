import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import java.awt.EventQueue;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;


public class Server3 extends JFrame {
    private JPanel contentPane;
    private JTextArea textArea;
    private JTextField textField;
    private JButton button;
    private DatagramSocket serverSocket;
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
    public Server3(){
        setTitle("Xu ly chuoi");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 450);

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

        button = new JButton();
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
        InetAddress IPAddress = InetAddress.getByName("localhost");
        byte[] sendData = new byte[1024];
        sendData = textArea.getText().getBytes();
        //tao datagram co noi dung yeu cau loai du lieu de gui cho server
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
        serverSocket.send(sendPacket);//gui du lieu cho server

    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(null, "Không phải số nguyên hợp lệ.");
    } catch (IOException e1) {
        e1.printStackTrace();
    }
    }
    public void formWindowOpened(java.awt.event.WindowEvent evt) throws IOException{
        serverSocket = new DatagramSocket();
        System.out.println("Server is started");
        byte[] receiveData = new byte[1024];
        byte[] sendData = new byte[1024];

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        //tao datagram rong de nhan du lieu
                        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length );
                        //nhan du lieu tu server
                        serverSocket.receive(receivePacket);
                        //lay du lieu tu packet nhan duoc
                        String str = new String(receivePacket.getData());
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
