package DAY02.P2805;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.function.Function;

// Samsung SDS Practice

/**
 * 나무의 높이를 이분 탐색하며
 * 나무를 M 미터 이상 가져갈 수 있다라면 left를 높여서 값을 높이고
 * 만일 M 미터 이상 가져갈 수 없다라면 right 를 낮춰서 나무를 많이가져가야한다.
 *
 * 역시 틀릴 이유가 없다고 생각했는데
 * 자료형의 크기 때문에 틀린거였음
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/DAY02/P2805/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Function<String, Integer> fun = Integer::parseInt;

        String[] input = br.readLine().split(" ");
        int N = fun.apply(input[0]);
        int M = fun.apply(input[1]);
        long ans = 0;
        long[] arr = new long[N];
        input = br.readLine().split(" ");

        long left = 0;
        long right = 0;
        for (int i = 0; i < N; i++) {
            arr[i] = fun.apply(input[i]);
            right = Math.max(right, arr[i]);
        }

        while (left <= right) {
            long mid = (left + right) / 2;
            long sum = 0;

            for (int i = 0; i < N; i++) { // 나무들을 잘라서 가져갈 수 있는 양을 정해야함
                long tree = arr[i] - mid;
                if (tree > 0) {
                    sum += tree;
                }
            }

            if (M <= sum) { // 자른게 M 미터 이상이면 left 를 높여서 더 적게 자를 수 있도록 해야함
                ans = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        System.out.println(ans);
    }
}
