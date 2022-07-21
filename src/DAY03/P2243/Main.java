package DAY03.P2243;

import java.util.*;
import java.io.*;
import java.util.function.Function;

// Samsung SDS Practice
public class Main {
    static int S;
    static long[] tree;

    static int poll(int left, int right, int cur, long value) {
        if (left == right) {
            return left; // 맛의 번호를 반환한다.
        }

        long lv = tree[cur * 2];

        if (value <= lv) {
            return poll(left, (left + right) / 2, cur * 2, value);
        } else {
            return poll((left + right) / 2 + 1, right, cur * 2 + 1, value - lv);
        }
    }

    static void update(int left, int right, int cur, int target, long diff) {
        if (!(left <= target && target <= right)) { // 범위를 포함하고 있지 않은 경우 반환하게 된다.
            return;
        }

        tree[cur] += diff;

        if (left != right) { // leaf 노드에 달했을 때, 한번더 자식 연산을 실행하여, index error 가 나는 것을 방지한다.
            update(left, (left + right) / 2, cur * 2, target, diff);
            update((left + right) / 2 + 1, right, cur * 2 + 1, target, diff);
        }
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/DAY03/P2243/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        Function<String, Integer> fun = Integer::parseInt;

        int N = fun.apply(br.readLine());

        S = 1 << ((int) Math.ceil(Math.log(1_000_000) / Math.log(2)));
        tree = new long[2 * S];

        for (int i = 0; i < N; i++) {
            String[] input = br.readLine().split(" ");

            if (input[0].equals("2")) { // 사탕을 넣거나 꺼내는 경우 즉, update
                int a = fun.apply(input[1]); // a 는 index 를 의미하고
                long b = Long.parseLong(input[2]); // 빼거나 더할 사탕의 개수를 의미한다.

                update(1, S, 1, a, b); // 그대로 넘기면 된다. 빼는 경우 음수를 주기 때문에
            } else { // 사탕을 꺼내는 경우, poll
                long a = Long.parseLong(input[1]);

                // 범위를 주는 것이 아닌, 솔직히 left, right 로 리프노드인지를 확인하고 반환할 것이며, 다른 범위로 가는 경우는 없고, value <= 왼쪽 값 인 경우 왼쪽, else 인 경우 value - 왼쪽 값 로 다시 넘겨준다.
                // 그리고 poll 에는 꼭 마지막에 update 를 실행해주어야 함
                // 혹은 여기서 그냥 받고 update 를 실행해주면 된다.
                int res = poll(1, S, 1, a); // 맛의 번호를 반환하는 것이니까
                update(1, S, 1, res, -1);
                sb.append(res).append("\n");
            }
        }

        System.out.print(sb);
    }
}
