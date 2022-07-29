package DAY10.P11062;

import java.util.*;
import java.io.*;

// Samsung SDS Practice

public class Main {
    static int[] card;
    static int[][] dp;

    static int dfs(int left, int right, int turn) {
        if (left == right) {
            return turn % 2 == 1 ? card[left] : 0;
        }

        if (dp[left][right] != 0) {
            return dp[left][right];
        }

        if (turn % 2 == 1) { // 근우의 턴
            dp[left][right] = Math.max(card[left] + dfs(left + 1, right, turn + 1), card[right] + dfs(left, right - 1, turn + 1));
        } else { // 명우의 턴
            dp[left][right] = Math.min(dfs(left + 1, right, turn + 1), dfs(left, right - 1, turn + 1));
        }

        return dp[left][right];
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/DAY10/P11062/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());

        while (T-- > 0) {
            int N = Integer.parseInt(br.readLine());
            st = new StringTokenizer(br.readLine());
            dp = new int[N][N]; // left, right 로 현재 남은 카드 범위를 나타내기 위한 dp 이다, 선택하지 않는 경우는 없으니까, 0이 아직 방문을 하지 않았다는 증표이다.
            card = new int[N];

            for (int i = 0; i < N; i++) {
                card[i] = Integer.parseInt(st.nextToken());
            }

            sb.append(dfs(0, N - 1, 1)).append("\n");
        }
        
        System.out.print(sb);
    }
}