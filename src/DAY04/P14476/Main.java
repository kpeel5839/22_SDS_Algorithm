package DAY04.P14476;

import java.util.*;
import java.io.*;
import java.util.function.Function;

// Samsung SDS Practice

/**
 * 이 문제는 누적 gcd 를 이용할 수 있다.
 * gcd 를 A, B, C 이 3개의 숫자에서 구한다라고 했을 때,
 * gcd(A, B, C) -> gcd(gcd(A,B), C) 라고 할 수 있다.
 *
 * 이러한 점을 이용하여서 누적 gcd 를 구하게 되면
 * 각각의 gcd 를 구한 뒤
 * 그걸 이용해서 또 gcd 를 구하게 되면?
 *
 * 바로 이 문제를 풀 수 있게 된다.
 * 그대신 Left -> Right 와
 * Right -> Left 누적 gcd 가 필요하다.
 * 이게 가능한 이유는 끊김이 중간에 단 한번만 발생하기 때문에 가능하다.
 *
 * 1개의 숫자만 빼는게 가능하니까 가능한 것이다.
 */
public class Main {
    static int K;
    static int ans = -1;

    static int gcd(int a, int b) {
        if (b == 0) {
            return a;
        }

        return gcd(b, a % b); // 이런식으로 단 3줄만에 gcd 를 작성가능하다.
    }
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/DAY04/P14476/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Function<String, Integer> fun = Integer::parseInt;

        int N = fun.apply(br.readLine());

        int[] num = new int[N];
        int[] left = new int[N];
        int[] right = new int[N];

        String[] input = br.readLine().split(" ");
        for (int i = 0; i < N; i++) {
            num[i] = fun.apply(input[i]);
        }

        left[0] = num[0];
        for (int i = 1; i < N; i++) {
            left[i] = gcd(left[i - 1], num[i]);
        }


        right[N - 1] = num[N - 1];
        for (int i = N - 2; i != -1; i--) {
            right[i] = gcd(num[i], right[i + 1]);
        }

        for (int i = 0; i < N; i++) {
            int g = 0; // gcd
            int k = 0; // 뺀수
            if (i == 0) { // 특이한 경우
                g = right[1];
                k = num[0];
            } else if (i == N - 1) { // 특이한 경우
                g = left[N - 2];
                k = num[N - 1];
            } else { // 일반적인 경우
                g = gcd(left[i - 1], right[i + 1]);
                k = num[i];
            }

            if (k % g != 0) { // k 의 약수가 g 가 되어서는 안된다.
                if (ans < g) {
                    ans = g;
                    K = k;
                }
            }
        }

        if (ans == -1) {
            System.out.println(ans);
        } else {
            System.out.println(ans + " " + K);
        }
    }
}