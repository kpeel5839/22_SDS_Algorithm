package DAY10.P7579;

// Samsung SDS Practice

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/DAY10/P7579/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int[] cost = new int[N];
        int[] memory = new int[N];
        int ans = Integer.MAX_VALUE;
        int totalCost = 0;

        st = new StringTokenizer(br.readLine());

        for (int i = 0; i < N; i++) {
            memory[i] = Integer.parseInt(st.nextToken());
        }

        st = new StringTokenizer(br.readLine());

        for (int i = 0; i < N; i++) {
            cost[i] = Integer.parseInt(st.nextToken());
            totalCost += cost[i];
        }

        int[][] dp = new int[totalCost + 1][N];

        for (int i = 0; i <= totalCost; i++) { // 사전 준비
            dp[i][0] = i < cost[0] ? 0 : memory[0]; // 현재 행이 cost[0] 보다 작으면 0 같거나 크면 cost[0] 를 넣어서 사전 준비를 해준다.

            if (M <= dp[i][0]) {
                ans = Math.min(ans, i);
            }
        }

        for (int i = 0; i <= totalCost; i++) {
            for (int j = 1; j < N; j++) {
                if (i < cost[j]) { // 선택지가 따로 없을 때
                    dp[i][j] = dp[i][j - 1];
                } else { // 선택지가 있을 때
                    dp[i][j] = Math.max(dp[i - cost[j]][j - 1] + memory[j], dp[i][j - 1]);
                }

                if (M <= dp[i][j]) {
                    ans = Math.min(ans, i);
                }
            }
        }

        System.out.print(ans);
    }
}
