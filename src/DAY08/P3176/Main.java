package DAY08.P3176;

import java.util.*;
import java.io.*;

// Samsung SDS Practice

public class Main {
    static int N;
    static int K;
    static int height;
    static int[] depth;
    static int[][] parent;
    static long[][] low;
    static long[][] high;
    static StringBuilder sb = new StringBuilder();
    static List<ArrayList<int[]>> graph = new ArrayList<>();

    static void lca(int a, int b) {
        long min = Long.MAX_VALUE; // 답을 담을 변수
        long max = 0; // 답을 담을 변수
//        System.out.println(a + " " + b);

        if (depth[a] < depth[b]) { // a 의 depth 가 무조건 높도록 설정해주자.
            int tmp = a;
            a = b;
            b = tmp;
        }

        for (int i = height - 1; i != -1; i--) {
            if (depth[a] - depth[b] >= (1 << i)) {
                min = Math.min(min, low[a][i]);
                max = Math.max(max, high[a][i]);
                a = parent[a][i]; // 옮겨준다.
            }
        }

//        System.out.println("1차 min : " + min);
//        System.out.println("1차 max : " + max);

        if (a == b) {
            sb.append(min + " " + max).append("\n");
            return;
        }

        for (int i = height - 1; i != -1; i--) {
            if (parent[a][i] != parent[b][i]) {
                min = Math.min(Math.min(min, low[a][i]), low[b][i]);
                max = Math.max(Math.max(max, high[a][i]), high[b][i]);
                a = parent[a][i];
                b = parent[b][i];
            }
        }

        min = Math.min(Math.min(min, low[a][0]), low[b][0]);
        max = Math.max(Math.max(max, high[a][0]), high[b][0]);

//        System.out.println("2차 min : " + min);
//        System.out.println("2차 max : " + max);

        sb.append(min + " " + max).append("\n");
    }

    static void fillParent() {
        // parent, low, high 를 채워주어야 함
        for (int i = 1; i < height; i++) {
            for (int j = 1; j <= N; j++) {
                parent[j][i] = parent[parent[j][i - 1]][i - 1];
                low[j][i] = Math.min(low[j][i - 1], low[parent[j][i - 1]][i - 1]);
                high[j][i] = Math.max(high[j][i - 1], high[parent[j][i - 1]][i - 1]); // 현재 정점의 조상과, 나의 조상의 조상과 비교해서 위와 같이 갱신해준다.
            }
        }
    }

    static void dfs(int d, int p, int c) {
        // depth, parent, high 를 채워주어야함
        depth[c] = d;

        for (int[] next : graph.get(c)) {
            if (next[0] != p) { // 부모가 아니면
                parent[next[0]][0] = c; // 현재 가려는 곳의 부모는 얘이다.
                low[next[0]][0] = next[1];
                high[next[0]][0] = next[1]; // 모두 동일하게 부모로 가는데에 값을 넣어준다.
                dfs(d + 1, c, next[0]);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/DAY08/P3176/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());
        height = (int) Math.ceil(Math.log(N) / Math.log(2));

        depth = new int[N + 1];
        parent = new int[N + 1][height];
        low = new long[N + 1][height];
        high = new long[N + 1][height];

        for (int i = 0; i <= N; i++) {
            graph.add(new ArrayList<>());
        }

        for (int i = 0; i < N - 1; i++) {
            st = new StringTokenizer(br.readLine());

            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());

            graph.get(a).add(new int[] {b, c});
            graph.get(b).add(new int[] {a, c});
        }

        dfs(0, -1, 1); // 루트를 그냥 무조건 1로
        fillParent();

//        System.out.println("low!");
//        for (int i = 1; i <= N; i++) {
//            System.out.println(Arrays.toString(low[i]));
//        }
//
//        System.out.println("high!");
//        for (int i = 1; i <= N; i++) {
//            System.out.println(Arrays.toString(high[i]));
//        }

        K = Integer.parseInt(br.readLine());

        for (int i = 0; i < K; i++) {
            st = new StringTokenizer(br.readLine());

            lca(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
        }

        System.out.print(sb);
    }
}
