package DAY02.P2748;

import java.util.*;
import java.io.*;
import java.util.function.Function;

// Samsung SDS Practice

/**
 * 그냥 dp 이용해서 피보나치 구하면 된다.
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/DAY02/P2748/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Function<String, Integer> fun = Integer::parseInt;

        int N = fun.apply(br.readLine());
        long[] dp = new long[N];
        if (N >= 2) {
            dp[0] = 1; // 1
            dp[1] = 1; // 2
        }

        for (int i = 2; i < N; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }

        System.out.println(N == 1 ? 1 : dp[N - 1]);
    }
}
