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
            in = in.replaceAll("\\s", "");
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

    public static Double evaluateExpression(String s) {
        Double res = 0.0;
        String[] s_postfix = convert(s);

        res = evaluate(s_postfix);

        return res;
    }

    public static Double evaluate(String[] tokens) {

        Stack<Double> stack = new Stack<>();

        for (String token : tokens) {
            if (!token.isEmpty())
                if (token.equals("+")) {
                    Double a = stack.pop();
                    Double b = stack.pop();
                    stack.push(a + b);
                } else if (token.equals("-")) {
                    Double b = stack.pop();
                    Double a = stack.pop();
                    stack.push(a - b);
                } else if (token.equals("*")) {
                    Double a = stack.pop();
                    Double b = stack.pop();
                    stack.push(a * b);
                } else if (token.equals("/")) {
                    Double b = stack.pop();
                    Double a = stack.pop();
                    stack.push(a / b);
                } else if (token.equals("(")) {
                    // Do nothing
                } else if (token.equals(")")) {
                    // Do nothing
                } else {
                    stack.push(Double.parseDouble(token.toString()));
                }
        }

        return stack.pop();
    }

    private static String[] convert(String infix) {
        Stack<Character> stack = new Stack<>();
        String postfix = "";

        for (int i = 0; i < infix.length(); i++) {
            char c = infix.charAt(i);

            if (Character.isLetterOrDigit(c)) {
                String temp = "";
                temp += c;
                while ((i + 1) < infix.length() && Character.isLetterOrDigit(infix.charAt(i + 1))) {
                    temp += infix.charAt(i + 1);
                    i++;
                }

                postfix = postfix + " " + temp;
            } else if (c == '(') {
                stack.push(c);
            } else if (c == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    postfix = postfix + " " + stack.pop();
                }
                stack.pop();
            } else {
                while (!stack.isEmpty() && precedence(c) <= precedence(stack.peek())) {
                    postfix = postfix + " " + stack.pop();
                }
                stack.push(c);
            }
        }

        while (!stack.isEmpty()) {
            postfix = postfix + " " + stack.pop();
        }

        return postfix.split(" ");
    }

    private static int precedence(char c) {
        if (c == '+' || c == '-') {
            return 1;
        } else if (c == '*' || c == '/') {
            return 2;
        } else {
            return 0;
        }
    }
}