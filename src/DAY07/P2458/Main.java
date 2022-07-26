package DAY07.P2458;

import java.util.*;
import java.io.*;
import java.util.function.Function;

// Samsung SDS Practice

public class Main {
    static int N;
    static int O;
    static boolean[] visited;
    static List<ArrayList<Integer>> small = new ArrayList<>();
    static List<ArrayList<Integer>> big = new ArrayList<>();

    static int dfs(int a, int dir) {
        visited[a] = true;
        int res = 1;
//        System.out.println(a);

        if (dir == 1) { // 작은애를 탐색 (본인보다)
            for (Integer next : small.get(a)) {
                if (!visited[next]) { // 아직 방문하지 않았으면
                    res += dfs(next, dir);
                }
            }
        } else { // 큰애를 탐색 (본인보다)
            for (Integer next : big.get(a)) {
                if (!visited[next]) {
                    res += dfs(next, dir);
                }
            }
        }

        return res;
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/DAY07/P2458/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Function<String, Integer> fun = Integer::parseInt;

        String[] input = br.readLine().split(" ");
        N = fun.apply(input[0]);
        O = fun.apply(input[1]);

        for (int i = 0; i <= N; i++) {
            small.add(new ArrayList<>());
            big.add(new ArrayList<>());
        }

        for (int i = 0; i < O; i++) {
            input = br.readLine().split(" "); // 뒤에 들어오는 놈이 더 큰놈임

            int a = fun.apply(input[0]);
            int b = fun.apply(input[1]);

            big.get(a).add(b);
            small.get(b).add(a);
        }

        int ans = 0;

        for (int i = 1; i <= N; i++) {
            int cnt = 0;
            visited = new boolean[N + 1];

//            System.out.println(i + " 보다 작은애 탐색 시작! ");
            cnt += dfs(i, 1); // small 탐색
//            System.out.println(i + " 보다 큰애 탐색 시작! ");
            cnt += dfs(i, 2); // big 탐색

            if (cnt - 2 == N - 1) { // 본인을 두번이나 세버리니까 - 2 해준다
                ans++;
            }
        }

        System.out.println(ans);
    }
}
