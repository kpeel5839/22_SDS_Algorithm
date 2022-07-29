package DAY10.P1102;

import java.util.*;
import java.io.*;

// Samsung SDS Practice

public class Main {
    static int N;
    static int P;
    static int INF = 1000;
    static int[] dp;
    static int[][] map;

    static int dfs(int bit, int on) { // bit == 현재 켜져있는 것들, on == 켜져있는 발전소의 개수
//        System.out.println(on);
        if (on == P) {
            return 0;
        } // 다 켜진 경우 끝내준다.

        if (dp[bit] != -1) {
            return dp[bit];
        }

        int ret = INF;

        for (int i = 0; i < N; i++) {
            if ((bit & (1 << i)) == 0) {
                // 아직 안 켜져 있는 경우
                // 여기서 이제 킬 수 있는 것들을 다 켜줄 것임
                // 현재 bit 에서 봤을 때, 안 켜져 있는 애들을 순서대로 진행하는 것이니까
                // 현재 켜져 있는 애들 중에서 가장 작은 값으로 할 수 있는 애를 찾는다.
                int min = INF;

                for (int j = 0; j < N; j++) {
                    if ((bit & (1 << j)) != 0) {
                        System.out.println("fucking!");
                        min = Math.min(min, map[j][i]);
                    }
                }

                ret = Math.min(ret, dfs((bit | 1 << i), on + 1) + min);
            }
        }

        dp[bit] = ret;
        return dp[bit];
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/DAY10/P1102/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());
        map = new int[N][N];
        dp = new int[1 << N];
        Arrays.fill(dp, -1);

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        String string = br.readLine();
        P = Integer.parseInt(br.readLine());
        int bit = 0;
        int on = 0;

        for (int i = 0; i < N; i++) {
            if (string.charAt(i) == 'Y') {
                bit |= (1 << i);
                on++;
            }
        }

        System.out.println(dfs(bit, on));
    }
}
