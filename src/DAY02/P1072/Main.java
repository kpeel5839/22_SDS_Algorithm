package DAY02.P1072;

import java.util.*;
import java.io.*;
import java.util.function.Function;

// Samsung SDS Practice

/**
 * 이 문제는 굉장히 더러운 문제였음
 * 29 / 50 과 같은 double 연산은
 * 무한대로 소수가 가기 때문에,
 * int 형으로 변환하였을 떄, 정확하지 않은 결과가 나왔음
 * 그래서 이것때문에 틀렸었고
 *
 * 조금의 백분율을 구하는 식을 응용하여서
 * y / x * 100 을
 * y * 100 / x 로 응용하여서
 * 그냥 정수나눗셈으로 문제를 해결하였다.
 */
public class Main {
    static long calProb(long y, long x) {
        return (y * 100) / x;
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/DAY02/P1072/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Function<String, Integer> fun = Integer::parseInt;

        String[] input = br.readLine().split(" ");
        long X = fun.apply(input[0]);
        long Y = fun.apply(input[1]);

        long init = (Y * 100) / X;
        long goal = init + 1;
//        System.out.println(goal);

        int left = 0;
        int right = 1_000_000_000;
        int ans = 0;

        while (left <= right) {
            int mid = (left + right) / 2;
            long prob = calProb(Y + mid, X + mid);
//            System.out.println(prob);
//            System.out.println(mid);

            if (prob < goal) {
                left = mid + 1;
            } else { // 짜피 같은 경우 앞에서 걸림
                ans = mid;
                right = mid - 1;
            }
        }

        System.out.println(ans == 0 ? -1 : ans);
    }
}
