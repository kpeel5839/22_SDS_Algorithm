package DAY07.P11266;

import java.util.*;
import java.io.*;
import java.util.function.Function;

// Samsung SDS Practice

/**
 * 기록해야 할 것
 * 방문순서 (ID), 방문순서만 기록하면 끝난다.
 */
public class Main {
    static int V;
    static int E;
    static int idValue = 0;
    static int[] id;
    static int ans = 0;
    static boolean[] serv;
    static List<ArrayList<Integer>> graph = new ArrayList<>();

    public static int dfs(int a, boolean isRoot) {
        id[a] = ++idValue;
        int child = 0;
        int min = id[a];

        for (Integer next : graph.get(a)) { // 다음 정점들을 찾아주면서
            if (id[next] == 0) { // 방문 안한 경우만 갈 수 있음
                child++;
                int res = dfs(next, false);

                if (!isRoot && id[a] <= res) { // 이러면 단절점인 것 (그래서 한번 단절이면 계속 단절인 것을 이용한다)
                    serv[a] = true;
                }

                min = Math.min(res, min);
            } else { // 즉 이미 방문한 지점이면
                min = Math.min(min, id[next]);
            }
        }


        if (isRoot && child > 1) {
            serv[a] = true; // size 가 2보다 크면
        }

        return min;
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/DAY07/P11266/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        Function<String, Integer> fun = Integer::parseInt;

        String[] input = br.readLine().split(" ");
        V = fun.apply(input[0]);
        E = fun.apply(input[1]);
        id = new int[V + 1];
        serv = new boolean[V + 1];

        for (int i = 0; i <= V; i++) { // 그래프 초기화
            graph.add(new ArrayList<>());
        }

        for (int i = 0; i < E; i++) {
            input = br.readLine().split(" ");
            int a = fun.apply(input[0]);
            int b = fun.apply(input[1]);

            graph.get(a).add(b);
            graph.get(b).add(a); // 양방향 간선 추가
        }

        for (int i = 1; i <= V; i++) {
            if (id[i] == 0) {
                dfs(i, true);
            }
        }

        for (int i = 1; i <= V; i++) {
            if (serv[i]) {
                ans++;
            }
        }

        sb.append(ans + "\n");

        for (int i = 1; i <= V; i++) {
            if (serv[i]) {
                sb.append(i + " ");
            }
        }

        System.out.print(sb);
    }
}
