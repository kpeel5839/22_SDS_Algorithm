package DAY06.P11438;

import java.util.*;
import java.io.*;
import java.util.function.Function;

// Samsung SDS Practice

public class Main {
    static int N;
    static int height;
    static int[] depth;
    static int[][] parent;
    static List<ArrayList<Integer>> graph = new ArrayList<>();

    static int lca(int a, int b) {
        if (depth[a] < depth[b]) {
            int tmp = a;
            a = b;
            b = tmp;
        }

        for (int i = height - 1; i != -1; i--) {
            if (depth[a] - depth[b] >= (1 << i)) {
                a = parent[a][i];
            }
        }

        if (a == b) {
            return a;
        }

        for (int i = height - 1; i != -1; i--) {
            if (parent[a][i] != parent[b][i]) {
                a = parent[a][i];
                b = parent[b][i];
            }
        }

        return parent[a][0];
    }

    static void fillParent() {
        for (int i = 1; i < height; i++) {
            for (int j = 1; j <= N; j++) {
                parent[j][i] = parent[parent[j][i - 1]][i - 1];
            }
        }
    }

    static void dfs(int d, int p, int c) {
        depth[c] = d;

        for (Integer next : graph.get(c)) {
            if (next != p) {
                parent[next][0] = c;
                dfs(d + 1, c, next);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/DAY06/P11438/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        Function<String, Integer> fun = Integer::parseInt;

        N = fun.apply(br.readLine());
        height = (int) Math.ceil(Math.log(N) / Math.log(2));
        parent = new int[N + 1][height];
        depth = new int[N + 1];

        for (int i = 0; i <= N; i++) {
            graph.add(new ArrayList<>());
        }

        for (int i = 0; i < N - 1; i++) {
            String[] input = br.readLine().split(" ");

            int a = fun.apply(input[0]);
            int b = fun.apply(input[1]);

            graph.get(a).add(b);
            graph.get(b).add(a);
        }

        dfs(0, -1, 1);
        fillParent();
        int M = fun.apply(br.readLine());

        for (int i = 0; i < M; i++) {
            String[] input = br.readLine().split(" ");

            int a = fun.apply(input[0]);
            int b = fun.apply(input[1]);

            sb.append(lca(a, b) + "\n");
        }

        System.out.print(sb);
    }
}
