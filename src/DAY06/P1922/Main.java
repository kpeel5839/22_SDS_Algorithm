package DAY06.P1922;

import java.util.*;
import java.io.*;
import java.util.function.Function;

// Samsung SDS Practice

public class Main {
    static int[] parent;
    static int[] rank;

    static int find(int a) {
        if (parent[a] == a) {
            return a;
        } else {
            parent[a] = find(parent[a]);
            return parent[a];
        }
    }

    static void union(int a, int b) {
        if (rank[a] <= rank[b]) {
            if (rank[a] == rank[b]) {
                rank[b]++;
            }

            parent[a] = b;
        } else {
            parent[b] = a;
        }
    }

    static class Edge {
        int a;
        int b;
        int c;

        Edge(int a, int b, int c) {
            this.a = a;
            this.b = b;
            this.c = c;
        }
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/DAY06/P1922/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        List<Edge> edges = new ArrayList<>();
        Function<String, Integer> fun = Integer::parseInt;

        int N = fun.apply(br.readLine());
        int M = fun.apply(br.readLine());
        parent = new int[N + 1];
        rank = new int[N + 1];

        for (int i = 0; i <= N; i++) {
            parent[i] = i;
            rank[i] = 1;
        }

        for (int i = 0; i < M; i++) {
            String[] input = br.readLine().split(" ");

            int a = fun.apply(input[0]);
            int b = fun.apply(input[1]);
            int c = fun.apply(input[2]);

            edges.add(new Edge(a, b, c));
        }

        Collections.sort(edges, (o1, o2) -> o1.c - o2.c);
        long ans = 0;

        for (Edge edge : edges) {
            int a = find(edge.a);
            int b = find(edge.b);

            if (a != b) {
                union(a, b);
                ans += edge.c;
            }
        }

        System.out.println(ans);
    }
}
