package DAY06.P11438;

import java.util.*;
import java.io.*;
import java.util.function.Function;

// Samsung SDS Practice

public class Alone {
    static int N;
    static List<ArrayList<Integer>> graph = new ArrayList<>();
    static int[] depth;
    static int[][] parent;

    static int lca(int a, int b) {
        if (depth[a] < depth[b]) { // depth 가 더 높은 애를 a 로 바꿔주자
            int tmp = a;
            a = b;
            b = tmp;
        }

        for (int i = parent[0].length - 1; i != -1; i--) {
            if (depth[a] - depth[b] >= (1 << i)) {
                a = parent[a][i];
            }
        }

        if (a == b) {
            return a;
        }

        for (int i = parent[0].length - 1; i != -1; i--) {
            if (parent[a][i] != parent[b][i]) {
                a = parent[a][i];
                b = parent[b][i];
            }
        }

        a = parent[a][0];
        return a;
    }

    static void makeParent() { // 여기서 조상을 정할 때 노드를 싹 돌아야하는데 그게 아니라 반대로 돌았음
        for (int i = 1; i < parent[0].length; i++) {
            for (int j = 1; j <= N; j++) {
                parent[j][i] = parent[parent[j][i - 1]][i - 1];
            }
        }
    }

    static void dfs(int d, int p, int c) {
        // p == parent, c == cur, d == depth
        depth[c] = d;

        for (Integer next : graph.get(c)) {
            if (next != p) {
                parent[next][0] = c; // next 의 부모에다가 c 를 넣는다.
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
        depth = new int[N + 1];
        parent = new int[N + 1][(int) (Math.ceil(Math.log(N) / Math.log(2)))]; // 이 height 도 굳이 최적화 하지 말고, 그냥 주어진 N 을 로그로 해줘도 된다.

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

        dfs(0, -1, 1); // 루트 정점을 1로 고정하고 depth 0 으로 던짐

        makeParent();

        int M = fun.apply(br.readLine());

        for (int i = 0; i < M; i++) {
            String[] input = br.readLine().split(" ");
            int a = fun.apply(input[0]);
            int b = fun.apply(input[1]);

            sb.append(lca(a, b)).append("\n");
        }

        System.out.print(sb);
    }
}
