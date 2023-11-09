import java.util.Scanner;

public class test {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        //String inputString = scanner.nextLine();
        StringBuilder sb = new StringBuilder();
        // System.out.println("Nhap so nut: ");
        int so_nut = 6;// scanner.nextInt();
        int so_duong = so_nut + 1;

        for (int i = 0; i < so_duong; i++) {
            System.out.println("Nhap ten 2 nut va khoang cach giua chung: ");
            String temp = scanner.nextLine();
            sb.append(" "+temp);
        }
        System.out.println(sb);
    }
}
