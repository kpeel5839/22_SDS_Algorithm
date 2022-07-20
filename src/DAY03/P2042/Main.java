package DAY03.P2042;

// Samsung SDS Practice

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.function.Function;

import static java.lang.Math.log;

/**
 * 기존에 알고 있던 세그먼트 트리에서 굉장히 많은 개념들이 보충이 되었다.
 * init 은 bottom up 이 굉장히 init 에 좋다라는 점을 깨달았고
 * S 가 정말 중요하다는 점도 깨달았다. (S 가 정말 중요하다라고 깨닫게 되었다.)
 *
 * -- 해맸던 점
 * 진짜 미친짓 하고 있었다
 * 범위 1, N 까지로 넘기고 있었다...
 * S 로 넘겨서 실제로 포화 이진 트리인 것처럼 연산이 되어야지
 * 그래야지 범위와 그런 것들도 다 어우러지면서
 * 리프노드까지 도달할 수 있는데
 * 웬지 리프노드까지 제대로 도달 못하는게 진짜 이상했었음
 * 이걸로 세그먼트 트리 마스터이다.
 *
 * 이건 진짜 금방 구현한다 완전 깨달았다.
 */
public class Main {
    static int S;
    static int N;
    static int M;
    static int K;
    static long[] tree;

    static void init() {
        for (int i = S - 1; i != 0; i--) {
            tree[i] = tree[i * 2] + tree[i * 2 + 1];
        }
    }

    static long sum(int left, int right, int cur, long sumLeft, long sumRight) {
        // 범위를 아얘 포함하지 않는 경우
        if (sumRight < left || right < sumLeft) {
            return 0;
        }

        // 범위를 완전히 포함하는 경우
        if (sumLeft <= left && right <= sumRight) {
            return tree[cur];
        }

        // 그 외에 경우
        return sum(left, (left + right) / 2, cur * 2, sumLeft, sumRight)
                + sum((left + right) / 2 + 1, right, cur * 2 + 1, sumLeft, sumRight);
    }

    static void update(int left, int right, int cur, long target, long diff) {
        // target 을 아얘 포함하지 않는 경우
        if (!(left <= target && target <= right)) {
            return;
        }

        // 나머지 경우 (양쪽으로 분기해준다)
        tree[cur] += diff; // 왜 left == right 인 경우가 바뀌지 않지?

        if (left != right) {
            update(left, (left + right) / 2, cur * 2, target, diff);
            update((left + right) / 2 + 1, right, cur * 2 + 1, target, diff);
        }
    }

//    static long sum(int queryLeft, int queryRight) {
//        // Leaf 에서 left, right 설정
//        int left = S + queryLeft - 1;
//        int right = S + queryRight - 1;
//        long sum = 0;
//        while (left <= right) {
//            // 좌측 노드가 홀수이면 현재 노드 값 사용하고 한칸 옆으로
//            if (left % 2 == 1) {
//                sum += tree[left++];
//            }
//            // 우측 노드가 짝수이면 현재 노드 값 사용하고 한칸 옆으로
//            if (right % 2 == 0) {
//                sum += tree[right--];
//            }
//            // 좌측,우측 모두 부모로 이동
//            left /= 2;
//            right /= 2;
//        }
//        return sum;
//    }
//
//    static void update(int target, long value) {
//        //Leaf에서 target을 찾음
//        int node = S + target - 1;
//        //value 반영
//        tree[node] = value;
//        //Root에 도달할 때 까지 부모에 값 반영
//        node /= 2;
//        while (node > 0) {
//            tree[node] = tree[node * 2] + tree[node * 2 + 1];
//            node /= 2;
//        }
//    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/DAY03/P2042/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        // 값을 받고 S 를 계산하자.
        String[] input = br.readLine().split(" ");

        N = Integer.parseInt(input[0]);
        M = Integer.parseInt(input[1]);
        K = Integer.parseInt(input[2]);

        // N 을 계산하기 위해서 log2(N) 을 이용해서, 일단 정수값을 계산하고, Math.ceil 을 이용하면 될 것 같다.
        S = 1 << (int) Math.ceil(log(N) / log(2));
        tree = new long[S * 2]; // 실제로 size 는 2 * S - 1 의 크기이지만 1번째를 비워야 하기 떄문에 이렇게 해야 한다.

        for (int i = 1; i <= N; i++) { // 값을 넣어줬다.
//            value[i] = Long.parseLong(br.readLine());
            tree[S + i - 1] = Long.parseLong(br.readLine());;
        }

        init();

        for (int i = 0; i < M + K; i++) {
            input = br.readLine().split(" ");
            long a = Long.parseLong(input[1]);
            long b = Long.parseLong(input[2]);

            if (input[0].equals("1")) { // update
//                update(a, b);
                update(1, S, 1, (int) a, b - tree[S + (int) a - 1]); // diff 만큼 더해주면 된다.
            } else { // sum
                sb.append(sum(1, S, 1, a, b)).append("\n");
//                sb.append(sum((int) a, (int) b)).append("\n");
            }
        }

//        for (int i = 0; i < N; i++) {
//            System.out.print(value[i] + " ");
//        }

        System.out.print(sb);
    }
}
