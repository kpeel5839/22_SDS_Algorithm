package DAY07.P11657;

import java.util.*;
import java.io.*;

// Samsung SDS Practice

/**
 * 밸만 포드 알고리즘을 사용할 떄 저번에는 visited 배열을 사용하여 아직 갱신되지 않은 정점에 대해서는 값을 처리하지 않았는데
 * 이번에는 그냥 처리하는 것도 상관없다라는 것을 깨닫고 처리했더니, long 범위 바깥으로 나가서 자꾸 결과가 이상하게 났었음
 *
 * 그래서 long 으로 변경해주니 잘 돌아감
 */
public class Main {
    static final int INF = 100_000_000;
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/DAY07/P11657/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        int V = Integer.parseInt(st.nextToken());
        int E = Integer.parseInt(st.nextToken());
        long[] dist = new long[V + 1];
        List<int[]> edges = new ArrayList<>();

        for (int i = 0; i < E; i++) {
            st = new StringTokenizer(br.readLine());

            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());

            edges.add(new int[] {a, b, c});
        }

        Arrays.fill(dist, INF);
        dist[1] = 0;
        boolean cycle = false;

        for (int i = 0; i < V; i++) {
            for (int[] edge : edges) {
                if (dist[edge[0]] == INF) {
                    continue;
                }

                if (dist[edge[1]] > dist[edge[0]] + (long) edge[2]) {
                    if (i == V - 1) {
                        cycle = true;
                    }

                    dist[edge[1]] = dist[edge[0]] + (long) edge[2];
                }
            }
        }

        StringBuilder sb = new StringBuilder();

        if (cycle) {
            sb.append(-1);
        } else {
            for (int i = 2; i <= V; i++) {
                sb.append(dist[i] == INF ? -1 : dist[i]).append("\n");
            }
        }

        System.out.print(sb);
    }
}
