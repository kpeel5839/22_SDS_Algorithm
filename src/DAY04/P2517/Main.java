package DAY04.P2517;

import java.util.*;
import java.io.*;
import java.util.function.Function;

import static java.lang.Math.log;

// Samsung SDS Practice

/**
 * -- 해맸던 점
 * StringBuilder 안쓰고
 * System.out.println 써가지고 시간초과에 걸렸다
 * 역시 System.out.print 는 thread safe 하기 때문에, 느리다.
 */
public class Main {
    static int N;
    static int S;
    static Person[] value;
    static long[] tree;

    static class Person {
        int index; // 기본 순위
        long value; // 실력

        Person(int index, long value) {
            this.index = index;
            this.value = value;
        }
    }

    static long sum(int left, int right, int cur, int sumLeft, int sumRight) {
        if (right < sumLeft || sumRight < left) {
            return 0;
        }

        if (sumLeft <= left && right <= sumRight) {
            return tree[cur];
        }

        return sum(left , (left + right) / 2, cur * 2, sumLeft, sumRight)
                + sum((left + right) / 2 + 1, right, cur * 2 + 1, sumLeft, sumRight);
    }

    static void update(int left, int right, int cur, int target) {
        if (!(left <= target && target <= right)) {
            return;
        }

        tree[cur] += 1;

        if (left != right) {
            update(left, (left + right) / 2, cur * 2, target);
            update((left + right) / 2 + 1, right, cur * 2 + 1, target);
        }
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/DAY04/P2517/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        Function<String, Integer> fun = Integer::parseInt;

        N = fun.apply(br.readLine());
        S = (1 << (int) Math.ceil(log(N) / log(2)));
        tree = new long[2 * S];
        value = new Person[N];
        long[] res = new long[N + 1]; // 답을 기존의 순위에다가 등록할 것이다.

        for (int i = 0; i < N; i++) {
            value[i] = new Person(i + 1, Long.parseLong(br.readLine()));
        }

        Arrays.sort(value, (o1, o2) -> Long.compare(o1.value, o2.value)); // 오름차순으로 정렬

        for (int i = 0; i < N; i++) {
            Person person = value[i];

            if (value[i].index != 1) {
                long diff = sum(1, S, 1, 1, person.index - 1);
                res[person.index] = (long) person.index - diff;
            } else { // index == 1 인 놈은 따로 처리해준다.
                res[person.index] = person.index;
            }

            update(1, S, 1, person.index);
        }

        for (int i = 1; i < res.length; i++) {
            sb.append(res[i]).append("\n");
        }

        System.out.print(sb);
    }
}
