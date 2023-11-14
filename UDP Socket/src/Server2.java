import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import java.util.Stack;

public class Server2 {
    public static void main(String[] args) throws Exception {
        DatagramSocket serverSocket = new DatagramSocket(9876);
        System.out.println("Server is started");
        byte[] receivedData = new byte[1024];
        byte[] sendData = new byte[1024];
        //Scanner kb = new Scanner(System.in);
        while (true) {
            // Tao goi rong de nhan du lieu tu client
            DatagramPacket receivePacket = new DatagramPacket(receivedData, receivedData.length);
            // Nhan du lieu tu client
            serverSocket.receive(receivePacket);
            // Lay dia chi IP tu may client
            InetAddress ipAddress = receivePacket.getAddress();
            // Lay port cua chuong trinh client
            int port = receivePacket.getPort();
            String msgout = new String(receivePacket.getData());
            System.out.println(msgout + ":" + msgout.trim().length());
            if (!msgout.trim().equals("")) {
                try {
                    double result = evaluateExpression(msgout.trim());
                    sendData = ("Kết quả: " + result).getBytes();
                } catch (IllegalArgumentException e) {
                    sendData = ("Lỗi: " + e.getMessage()).getBytes();

                } catch (ArithmeticException e) {
                    sendData = ("Lỗi: " + e.getMessage()).getBytes();

                } catch (Exception e) {
                    sendData = ("Lỗi trong biểu thức toán học.").getBytes();

                }
            } else {
                sendData = "Server does not know what you want?".getBytes();
            }
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, ipAddress, port);
            // Gui du lieu lai cho client
            serverSocket.send(sendPacket);
        }
    }

    public static double evaluateExpression(String expression) {
        Stack<Double> numbers = new Stack<>();
        Stack<Character> operators = new Stack<>();

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            if (c == ' ') {
                continue;
            }

            if (Character.isDigit(c)) {
                StringBuilder sb = new StringBuilder();
                while (i < expression.length() && (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.')) {
                    sb.append(expression.charAt(i));
                    i++;
                }
                i--;
                numbers.push(Double.parseDouble(sb.toString()));
            } else if (c == '(') {
                operators.push(c);
            } else if (c == ')') {
                while (operators.peek() != '(') {
                    double b = numbers.pop();
                    double a = numbers.pop();
                    char op = operators.pop();
                    numbers.push(performOperation(a, b, op));
                }
                operators.pop();
            } else if (c == '+' || c == '-' || c == '*' || c == '/') {
                while (!operators.isEmpty() && hasPrecedence(c, operators.peek())) {
                    double b = numbers.pop();
                    double a = numbers.pop();
                    char op = operators.pop();
                    numbers.push(performOperation(a, b, op));
                }
                operators.push(c);
            } else {
                throw new IllegalArgumentException("Biểu thức không hợp lệ: " + c);
            }
        }

        while (!operators.isEmpty()) {
            double b = numbers.pop();
            double a = numbers.pop();
            char op = operators.pop();
            numbers.push(performOperation(a, b, op));
        }

        if (numbers.size() != 1 || !operators.isEmpty()) {
            throw new IllegalArgumentException("Biểu thức không hợp lệ");
        }

        return numbers.pop();
    }

    public static boolean hasPrecedence(char op1, char op2) {
        if ((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-')) {
            return true;
        }
        return false;
    }

    public static double performOperation(double a, double b, char operator) {
        switch (operator) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                if (b == 0) {
                    throw new ArithmeticException("Lỗi chia cho 0.");
                }
                return a / b;
            default:
                throw new IllegalArgumentException("Toán tử không hợp lệ: " + operator);
        }
    }
}