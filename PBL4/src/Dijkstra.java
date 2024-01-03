import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class Dijkstra {
    static final int MAX_NODES = 1000;
    static final int MAX_DISTANCE = 1000000000;
    static int[][] graph = new int[MAX_NODES][MAX_NODES];
    static int[] distance = new int[MAX_NODES];
    static int[] previousNode = new int[MAX_NODES];
    static boolean[] visited = new boolean[MAX_NODES];

    public static String myDijkstra(int start, int end, String line) throws IOException {
        int numNodes, numEdges;
        Scanner inp = new Scanner(line);
        numNodes = inp.nextInt();
        numEdges = inp.nextInt();

        // Initialize the graph
        for (int i = 1; i <= numNodes; i++) {
            Arrays.fill(graph[i], -1);
        }

        // Read the edges and weights from the input
        for (int i = 1; i <= numEdges; i++) {
            int node1 = inp.nextInt();
            int node2 = inp.nextInt();
            int weight = inp.nextInt();
            graph[node1][node2] = weight;
            graph[node2][node1] = weight;
        }
        inp.close();

        // Initialize distance, visited, and previousNode arrays
        for (int i = 1; i <= numNodes; i++) {
            distance[i] = MAX_DISTANCE;
            visited[i] = true;
            previousNode[i] = 0;
        }

        distance[start] = 0;
        previousNode[start] = 0;
        int currentNode = start, minDistance;

        // Dijkstra's algorithm
        while (currentNode != end) {
            // Find the node with the minimum distance
            minDistance = MAX_DISTANCE;
            for (int i = 1; i <= numNodes; i++) {
                if (visited[i] && minDistance > distance[i]) {
                    minDistance = distance[i];
                    currentNode = i;
                }
            }
            if (minDistance == MAX_DISTANCE)
                break;

            // Remove currentNode from visited
            visited[currentNode] = false;

            // Optimize the distances of the neighboring nodes
            for (int i = 1; i <= numNodes; i++) {
                if (graph[currentNode][i] > 0 && distance[i] > distance[currentNode] + graph[currentNode][i]) {
                    distance[i] = distance[currentNode] + graph[currentNode][i];
                    previousNode[i] = currentNode;
                }
            }
        }

        if (distance[end] == MAX_DISTANCE) {
            System.out.println("NO PATH");
            return "NO PATH";
        } else {
            StringBuilder result = new StringBuilder();
            result.append(distance[end]).append("\n");

            // Construct the shortest path
            int[] path = new int[MAX_NODES];
            int d = 0;
            d++;
            path[d] = end;
            while (previousNode[end] != 0) {
                end = previousNode[end];
                d++;
                path[d] = end;
            }

            // Append the nodes in the shortest path to the result
            for (int i = d; i > 0; i--) {
                result.append(path[i]).append(" ");
            }
            return result.toString();
        }
    }

    public static void main(String[] args) throws IOException {
        String line = "6 7" + "\n" + "1 2 8" + "\n" + "1 6 2" + "\n" + "2 3 3" + "\n" + "2 5 3" + "\n" + "3 4 2" + "\n"
                + "4 5 1" + "\n" + "5 6 3";
        String pathResult = myDijkstra(2, 4, line);
        int start = 2;
        int end = 4;
        int numNodes = 7;
        String output = myDijkstra(start, end, line);
        for (int i = 1; i <= numNodes; i++) {
            String[] out = Dijkstra.myDijkstra(start, i, line).split("\n", 2);
            output += "\n" + out[1];
        }
        System.out.println(output);
    }
}
