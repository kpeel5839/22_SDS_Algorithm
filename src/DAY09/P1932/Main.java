package DAY09.P1932;

import java.util.*;
import java.io.*;

// Samsung SDS Practice

public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/DAY09/P1932/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int N = Integer.parseInt(br.readLine());

        int[][] dp = new int[N][N];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            int index = 0;

            while (st.hasMoreTokens()) {
                dp[i][index++] = Integer.parseInt(st.nextToken());
            }
        }

        for (int i = 1; i < N; i++) {
            for (int j = 0; j <= i; j++) {
                if (j == 0) {
                    dp[i][j] = dp[i][j] + dp[i - 1][j];
                } else if (j == i) {
                    dp[i][j] = dp[i][j] + dp[i - 1][j - 1];
                } else {
                    dp[i][j] = dp[i][j] + Math.max(dp[i - 1][j], dp[i - 1][j - 1]);
                }
            }
        }

        int ans = 0;

        for (int i = 0; i < N; i++) {
            ans = Math.max(ans, dp[N - 1][i]);
        }

        System.out.println(ans);
    }
}
