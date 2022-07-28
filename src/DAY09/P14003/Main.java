package DAY09.P14003;

import java.util.*;
import java.io.*;

// Samsung SDS Practice

public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/DAY09/P14003/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        int N = Integer.parseInt(br.readLine());
        long[] num = new long[N];
        long[] seq = new long[N];

        List<Long> list = new ArrayList<>();
        Stack<Long> stack = new Stack<>();
        list.add(Long.MIN_VALUE); // 첫번째를 채워줌

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            num[i] = Long.parseLong(st.nextToken());
//            System.out.println(num[i]);
//            System.out.println(list);
            if (list.get(list.size() - 1) < num[i]) {
                list.add(num[i]);
                seq[i] = list.size() - 1;
            } else {
                int left = 0;
                int right = list.size() - 1;

                while (left < right) {
                    int mid = (left + right) / 2;

                    if (num[i] <= list.get(mid)) {
                        right = mid;
                    } else {
                        left = mid + 1;
                    }
                }

                list.set(right, num[i]);
                seq[i] = right;
            }
        }

        int track = list.size() - 1;
        sb.append(track).append("\n");

        for (int i = seq.length - 1; i != -1; i--) {
            if (seq[i] == track) {
                stack.add(num[i]);
                track--;
            }
        }

        while (!stack.isEmpty()) {
            sb.append(stack.pop() + " ");
        }

        System.out.print(sb);
    }
}
