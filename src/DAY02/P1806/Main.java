package DAY02.P1806;

import java.util.*;
import java.io.*;
import java.util.function.Function;

// Samsung SDS Practice

/**
 * 그냥 슬라이딩 윈도우 사용하는 문제인데
 * 부분합으로 S 를 충족하였을 경우
 * 길이를 계속 보아 min 값을 취하는 문제이다.
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/DAY02/P1806/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Function<String, Integer> fun = Integer::parseInt;

        String[] input = br.readLine().split(" ");
        int N = fun.apply(input[0]);
        int S = fun.apply(input[1]);
        int[] list = new int[N + 1];
        int ans = Integer.MAX_VALUE;

        input = br.readLine().split(" ");
        for (int i = 0; i < N; i++) {
            list[i] = fun.apply(input[i]);
        }

        int left = 0;
        int right = 0;
        int sum = list[right];

        while (true) {
//            System.out.println(sum);
//            System.out.println(left + " " + right);
            if (S <= sum) {
                ans = Math.min(ans, right - left + 1);
                sum -= list[left++];
            } else {
                sum += list[++right];
            }

            if (right == N) {
                break;
            }
        }

        System.out.println(ans == Integer.MAX_VALUE ? 0 : ans);
    }
}
