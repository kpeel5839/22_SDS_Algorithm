package DAY07.P11404;

import java.util.*;
import java.io.*;

// Samsung SDS Practice

public class Main {
    static int INF = 1_000_000_000;
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/DAY07/P11404/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int V = Integer.parseInt(br.readLine());
        int E = Integer.parseInt(br.readLine());

        int[][] dp = new int[V + 1][V + 1];

        for (int i = 0; i <= V; i++) {
            Arrays.fill(dp[i], INF);
        }

        for (int i = 0; i < E; i++) {
            st = new StringTokenizer(br.readLine());

            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());

            dp[a][b] = Math.min(dp[a][b], c);
        }

        for (int k = 1; k <= V; k++) {
            for (int i = 1; i <= V; i++) {
                for (int j = 1; j <= V; j++) {
                    dp[i][j] = Math.min(dp[i][j], dp[i][k] + dp[k][j]);
                }
            }
        }

        StringBuilder sb = new StringBuilder();

        for (int i = 1; i <= V; i++) {
            for (int j = 1; j <= V; j++) {
                if (i != j) {
                    sb.append((dp[i][j] == INF ? 0 : dp[i][j]) + " ");
                } else {
                    sb.append(0 + " ");
                }
            }

            sb.append("\n");
        }

        System.out.print(sb);
    }
}
