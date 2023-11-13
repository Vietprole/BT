
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.Stack;

public class Server2 {
    public static void main(String[] args) throws IOException {
    ServerSocket server = new ServerSocket(5500);
		System.out.println("Server is started");
		Socket socket = server.accept();
		DataInputStream din = new DataInputStream(socket.getInputStream());
		DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
		Scanner kb = new Scanner(System.in);
		while(true) {
			String in = din.readUTF();
            try {
                double result = evaluateExpression(in);
                dos.writeUTF("Kết quả: " + result);
                dos.flush();
            } catch (IllegalArgumentException e) {
                dos.writeUTF("Lỗi: " + e.getMessage());
                dos.flush();
            } catch (ArithmeticException e) {
                dos.writeUTF("Lỗi: " + e.getMessage());
                dos.flush();
            } catch (Exception e) {
                dos.writeUTF("Lỗi trong biểu thức toán học.");
                dos.flush();
            }
            kb.reset();
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