import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;

public class Cau1 {

    public static void main(String[] args) throws Exception {

        try {
            // Get hostname by textual representation of IP address
            InetAddress addr = InetAddress.getByName("www.google.com");
            // Get hostname by a byte array containing the IP address
            // byte[] ipAddr = new byte[] { 8, 8, 8, 8 };
            // addr = InetAddress.getByAddress(ipAddr);
            String hostname = addr.getHostName(); // Get the host name
            // Get canonical host name
            String hostnameCanonical = addr.getCanonicalHostName();
            System.out.println(hostname);

            // Create a URL object for the homepage of www.google.com
            URL url = new URL("http://" + hostname);

            // Open a connection to the URL
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Check the response code
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                System.out.println("Error connecting to URL: " + connection.getResponseCode());
                return;
            }

            // Get the input stream from the connection
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            // Read the homepage content line by line
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            // Close the reader and connection
            reader.close();
            connection.disconnect();
        } catch (UnknownHostException e) {
        }

    }
}