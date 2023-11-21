import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class Dijkstra {
    static final int maxn = 1000;
    static final int maxf = 1000000000;
    static int[][] a = new int[maxn][maxn];
    static int[] f = new int[maxn];
    static int[] trace = new int[maxn];
    static boolean[] checkt2 = new boolean[maxn];

    public static String mydijkstra(int s, int e, String line) throws IOException {
        int n, m;
        Scanner inp = new Scanner(new File("D:/tai xuong/input.txt"));
        //line = "6 7"+"\n"+"1 2 8"+"\n"+"1 6 2"+"\n"+"2 3 3"+"\n"+"2 5 3"+"\n"+"3 4 2"+"\n"+"4 5 1"+"\n"+"5 6 3";
        //Scanner inp = new Scanner(line);
        n = inp.nextInt();
        m = inp.nextInt();
        for (int i = 1; i <= n; i++) {
            Arrays.fill(a[i], -1);
        }
        for (int i = 1; i <= m; i++) {
            int u = inp.nextInt();
            int v = inp.nextInt();
            int w = inp.nextInt();
            a[u][v] = w;
            a[v][u] = w;
        }
        inp.close();
        for (int i = 1; i <= n; i++) {
            f[i] = maxf;
            checkt2[i] = true;
            trace[i] = 0;
        }
        f[s] = 0;
        trace[s] = 0;
        int v = s, fmin;
        while (v != e) {
            // Find v
            fmin = maxf;
            for (int i = 1; i <= n; i++) {
                if (checkt2[i] && fmin > f[i]) {
                    fmin = f[i];
                    v = i;
                }
            }
            if (fmin == maxf)
                break;
            // Remove v from T2
            checkt2[v] = false;
            // Optimize the vertices in T2
            for (int i = 1; i <= n; i++) {
                if (a[v][i] > 0 && f[i] > f[v] + a[v][i]) {
                    f[i] = f[v] + a[v][i];
                    trace[i] = v;
                }
            }
        }

        if (f[e] == maxf) {
            System.out.println("NO PATH");
            return "NO PATH";
        } else {
            //System.out.println(f[e]);
            StringBuilder result = new StringBuilder();
            result.append(f[e]).append("\n");
            int[] path = new int[maxn];
            int d = 0;
            d++;
            path[d] = e;
            while (trace[e] != 0) {
                e = trace[e];
                d++;
                path[d] = e;
            }
           
            for (int i = d; i > 0; i--) {
                result.append(path[i]).append(" ");
            }
            //System.out.print(result);
            return result.toString();
        }
    }

    public static void main(String[] args) throws IOException {
        String line = "6 7"+"\n"+"1 2 8"+"\n"+"1 6 2"+"\n"+"2 3 3"+"\n"+"2 5 3"+"\n"+"3 4 2"+"\n"+"4 5 1"+"\n"+"5 6 3";
        String pathResult = mydijkstra(2, 4, line);
        int start = 2;
        int end = 4;
        int nodes = 7;
        String output = mydijkstra(start, end, line);
        for (int i = 1; i <= nodes; i++){
            String[] out = Dijkstra.mydijkstra(start, i, line).split("\n", 2);
            output += "\n" + out[1];
        }
        System.out.println(output);
    }
}
