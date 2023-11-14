import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import java.awt.EventQueue;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


public class Client4 extends JFrame {
    private JPanel contentPane;
    private JTextArea textArea;
    private JTextField textField;
    private JButton button;
    private Socket clientSocket;
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Client4 frame = new Client4();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public Client4(){
        setTitle("SQL - client");
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
        String str = textField.getText();
        DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
        out.writeUTF(str);
        textArea.append("Client: " + str + "\n");
        textField.setText("");

    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(null, "Không phải số nguyên hợp lệ.");
    } catch (IOException e1) {
        e1.printStackTrace();
    }
    }
    public void formWindowOpened(java.awt.event.WindowEvent evt) throws IOException{
        clientSocket = new Socket("localhost", 5500);
        DataInputStream in = new DataInputStream(clientSocket.getInputStream());
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        String str = in.readUTF();
                        textArea.append("Server: " + str + "\n");
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
