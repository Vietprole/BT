import java.sql.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import java.awt.EventQueue;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;


public class Server4 extends JFrame {
    private JPanel contentPane;
    private JTextArea textArea;
    private DatagramSocket serverSocket;
    private InetAddress IPAddress;
    private byte[] sendData = new byte[1024];
    private byte[] receiveData = new byte[1024];
    private int port;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Server4 frame = new Server4();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Server4() {
        setTitle("SQL - server");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 700, 450);

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                try {
                    formWindowOpened(evt);
                } catch (IOException | ClassNotFoundException | SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        contentPane = new JPanel();
        contentPane.setBounds(150, 150, 700, 350);
        contentPane.setLayout(null);
        setContentPane(contentPane);

        textArea = new JTextArea();
        textArea.setBounds(10, 11, 621, 343);
        contentPane.add(textArea);

    }

    public void formWindowOpened(java.awt.event.WindowEvent evt)
            throws IOException, ClassNotFoundException, SQLException {
        
        serverSocket = new DatagramSocket(9876);
        System.out.println("Server is started");
        textArea.append("Server is started!" + "\n");
        receiveData = new byte[1024];

        textArea.append("Server: Server started" + "\n");
        textArea.append("Server: Waiting for client..." + "\n");
        System.out.println("Server started");
        System.out.println("Waiting for a client ...");

        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        System.out.print("SQL Driver OK!");
        textArea.append("SQL Driver OK!" + "\n");
        String URL = "jdbc:sqlserver://localhost:1433;IntegratedSecurity=true;databaseName=QLSV;user=VT-PGH\\OS;encrypt=false";
        Connection connection = DriverManager.getConnection(URL);
        System.out.print("Connection OK");
        textArea.append("Connection OK!" + "\n");
        // // Start a loop to accept incoming client connections
        // while (true) {
        // // Accept an incoming client connection
        // Socket serverSocket = server.accept();
        // System.out.println("Server: Client accepted");
        // DataInputStream in = new DataInputStream(serverSocket.getInputStream());
        // // Create a new thread to handle the client connection
        // Thread clientHandlerThread = new Thread(() -> {
        // try {
        // while (true) {
        // String str = in.readUTF();
        // textArea.append("Client: " + str + "\n");
        // System.out.println(str);
        // }
        // } catch (IOException e) {
        // e.printStackTrace();
        // }
        // });

        // // Start the client handler thread
        // clientHandlerThread.start();
        // }

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        //tao datagram rong de nhan du lieu
                        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                        //nhan du lieu tu client
                        serverSocket.receive(receivePacket);
                        IPAddress = receivePacket.getAddress();
                        port = receivePacket.getPort();
                        //lay du lieu tu packet nhan duoc
                        String str = new String(receivePacket.getData()).trim();
                        textArea.append("Client: " + str + "\n");
                        System.out.println(str);

                        Statement statement = connection.createStatement();
                        ResultSet resultSet = statement.executeQuery(str);
                        // Get the ResultSetMetaData object from the ResultSet object
                        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
                        String Result = "";
                        // Iterate over the ResultSetMetaData object and print the name and value of
                        // each column for each row in the ResultSet object
                        for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
                            System.out.print(resultSetMetaData.getColumnName(i) + ": ");
                            Result += resultSetMetaData.getColumnName(i) + ": ";
                        }
                        System.out.println();
                        Result += "\n";

                        while (resultSet.next()) {
                            for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
                                System.out.print(resultSet.getString(i) + ": ");
                                Result += resultSet.getString(i) + ": ";
                            }
                            System.out.println();
                            Result += "\n";
                        }
                        sendData = Result.getBytes();
                        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
                        serverSocket.send(sendPacket);
                    } catch (IOException i) {
                        System.out.println(i);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        t.start();
    }
}
