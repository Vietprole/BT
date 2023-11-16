import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import java.awt.EventQueue;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Server3 extends JFrame {
    private JPanel contentPane;
    private JTextArea textArea;
    private JTextField textField;
    private JButton button;
    private ServerSocket server;
    private Socket serverSocket;
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
        String str = textField.getText();
        DataOutputStream out = new DataOutputStream(serverSocket.getOutputStream());
        out.writeUTF(str);
        textArea.append("Server: " + str + "\n");
        textField.setText("");
    } catch (IOException e) {
        e.printStackTrace();
    }
    }
    public void formWindowOpened(java.awt.event.WindowEvent evt) throws IOException{
        server = new ServerSocket(5500);
        textArea.append("Server: Server started" + "\n");
        textArea.append("Server: Waiting for client..." + "\n");
        System.out.println("Server started");
        System.out.println("Waiting for a client ...");

        // // Start a loop to accept incoming client connections
        // while (true) {
        //     // Accept an incoming client connection
        //     Socket serverSocket = server.accept();
        //     System.out.println("Server: Client accepted");
        //     DataInputStream in = new DataInputStream(serverSocket.getInputStream());
        //     // Create a new thread to handle the client connection
        //     Thread clientHandlerThread = new Thread(() -> {
        //         try {
        //             while (true) {
        //                 String str = in.readUTF();
        //                 textArea.append("Client: " + str + "\n");
        //                 System.out.println(str);
        //             }
        //         } catch (IOException e) {
        //             e.printStackTrace();
        //         }
        //     });

        //     // Start the client handler thread
        //     clientHandlerThread.start();
        //}
        
        serverSocket = server.accept();
        DataInputStream in = new DataInputStream(serverSocket.getInputStream());
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        String str = in.readUTF();
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
