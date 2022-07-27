package DAY08.P5719;

import java.util.*;
import java.io.*;

// Samsung SDS Practice

/**
 * 5 8
 * 0 4
 * 0 1 1
 * 0 2 1
 * 1 3 1
 * 2 3 1
 * 3 4 1
 * 0 4 6
 * 1 4 4
 * 2 4 4
 * 0 0
 *
 * 나를 살려준 반례
 *
 * dfs, bfs 버전 둘 다 방문처리만 잘 해주면 맞음
 */
public class Main {
    static int V;
    static int E;
    static int S;
    static int D;
    static int[] dist;
    static boolean[] visited;
    static boolean[][] cutRoad;
    static List<ArrayList<int[]>> graph = new ArrayList<>();
    static List<ArrayList<int[]>> tracking = new ArrayList<>();

    static int dijkstra(boolean end) {
        int min = -1;
        PriorityQueue<int[]> queue = new PriorityQueue<>((o1, o2) -> o1[1] - o2[1]); // 오름차순
        dist[S] = 0;
        queue.add(new int[] {S, 0});

        while (!queue.isEmpty()) {
            int[] p = queue.poll();

            if (dist[p[0]] < p[1]) { // 현재 비용보다 이미 이전에 기록해놓은게 같지도 않고 더 작으면 올 필요가 없음
                continue;
            }

            if (p[0] == D) { // 목적지에 도달한 경우
                min = p[1];

                if (end) {
                    break;
                }

                continue;
            }

            for (int[] next : graph.get(p[0])) {
                if (!cutRoad[p[0]][next[0]] && dist[next[0]] > p[1] + next[1]) { // 도로가 없어지지 않았으면서, dist 가 더 이득인 경우
                    dist[next[0]] = p[1] + next[1];
                    queue.add(new int[] {next[0], dist[next[0]]});
                }
            }
        }

        return min;
    } // 최솟값을 return 한다. (최소 경로가 없는 경우 -1 을 return 한다.

    static void bfs(int v, int remain) { // 최솟값을 따라서 진행한다. (tracking 그래프를 써야한다, 그리고 a -> b 를 지울 것을 찾았을 때, b -> a 를 지워줘야 한다)
        // dist[] = remain - 간선 일때만 지워준다.
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[] {v, remain});
        visited[v] = true;

        while (!queue.isEmpty()) {
            int[] p = queue.poll();

            if (p[0] == S) {
                continue;
            }

            for (int[] next : tracking.get(p[0])) {
                if (dist[next[0]] == p[1] - next[1]) {
                    cutRoad[next[0]][p[0]] = true;

                    if (!visited[next[0]]) { // 이미 방문한 지점에 대해서는, 가지는 않아도 된다, 다른 놈이 갈테니까 하지만, 간선은 지워줘야한다.
                        queue.add(new int[] {next[0], p[1] - next[1]});
                    }

                    visited[next[0]] = true;
                }
            }
        }
    }

    static void dfs(int v, int remain) { // 이 방법도 되는데 너무 느림
        if (v == S) {
            return;
        }

        for (int[] next : tracking.get(v)) {
            if (dist[next[0]] == remain - next[1]) {
                cutRoad[next[0]][v] = true;

                if (!visited[next[0]]) {
                    visited[next[0]] = true;
                    dfs(next[0], remain - next[1]);
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/DAY08/P5719/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        while (true) {
            st = new StringTokenizer(br.readLine());

            V = Integer.parseInt(st.nextToken());
            E = Integer.parseInt(st.nextToken());

            if (V == 0 && E == 0) {
                break;
            }

            st = new StringTokenizer(br.readLine());
            S = Integer.parseInt(st.nextToken());
            D = Integer.parseInt(st.nextToken());

            graph = new ArrayList<>();
            tracking = new ArrayList<>();
            dist = new int[V];
            cutRoad = new boolean[V][V];

            for (int i = 0; i < V; i++) {
                graph.add(new ArrayList<>());
                tracking.add(new ArrayList<>());
            }

            for (int i = 0; i < E; i++) {
                st = new StringTokenizer(br.readLine());

                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());
                int c = Integer.parseInt(st.nextToken());

                graph.get(a).add(new int[] {b, c});
                tracking.get(b).add(new int[] {a, c}); // 역으로 오는 간선을 구성해놔준다.
            }

            Arrays.fill(dist, Integer.MAX_VALUE);
            int min = dijkstra(false); // dijkstra 를 통해 얻은 min 최소비용을 이용해서 cutRoad 를 채워놓는다.

            if (min != -1) {
                visited = new boolean[V];
//                bfs(D, min);
                dfs(D, min); // 이 방법도 되는데 너무 느림
                dist = new int[V];
                Arrays.fill(dist, Integer.MAX_VALUE);
                sb.append(dijkstra(true)).append("\n"); // 없는 경우 -1을 출력한다.
            } else {
                sb.append(min).append("\n");
            }
        }

        System.out.print(sb);
    }
}
