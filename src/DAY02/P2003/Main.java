package DAY02.P2003;

import java.util.*;
import java.io.*;
import java.util.function.Function;

// Samsung SDS Practice

/**
 * -- 틀 설계
 * 양 수만 존재하니까
 * 두 포인터를 이용하면 된다. (슬라이딩 윈도우)
 *
 * sum > M Left++
 * sum < M right++
 * sum == M Left++
 *
 * right == size 이면 끝낸다
 * 왜냐하면? right 가 커졌다라는 것은 sum 이 현재 M 보다 작다라는 것이고, 이 경우 어떤 식으로라든 답을 더 찍어낼 수가 없다.
 *
 * 겨우 슬라이딩 윈도우 이렇게 쉬운 문제를 이렇게 쩔쩔매다니..
 * 어디서 예외가 있는건지 아직 잘 모르겠다.
 *
 * 주석을 조금 더 꼼꼼히 작성해보고 진행하는 것이 중요할 것 같고
 * 설계도 더 촘촘히 해야할 것 같다.
 *
 * 쉬운 문제라고 너무 낮게 봤다.
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/DAY02/P2003/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Function<String, Integer> fun = Integer::parseInt;

        String[] input = br.readLine().split(" ");
        int N = fun.apply(input[0]);
        int M = fun.apply(input[1]);
        int[] list = new int[N + 1];

        input = br.readLine().split(" ");
        for (int i = 0; i < N; i++) {
            list[i] = fun.apply(input[i]);
        }

        int left = 0;
        int right = 0;
        int sum = list[0];
        int ans = 0;

        while (true) {
            if (sum == M) {
                ans++;
                sum -= list[left++];
            } else if (sum < M) {
                sum += list[++right];
            } else {
                sum -= list[left++];
            }

            if (right == N) {
                break;
            }
        }

        System.out.println(ans);
    }
}
