package DAY08.P3830;

import java.util.*;
import java.io.*;

// Samsung SDS Practice

public class Main {
    static int[] parent;
    static long[] value; // 현재 본인이 가르키고 있는 parent 까지의 value 가 어떻게 되는지 갖고 있음

    static long[] find(int a) {
        if (parent[a] == a) {
            return new long[] {a, value[a]};
        } else {
            long[] res = find(parent[a]);
            parent[a] = (int) res[0]; // 경로 압축
            value[a] += res[1]; // 기존에 갖고 있던 값과, return 받은 value 를 더해줘서 현재 새로 가르키게 된 부모까지의 값을 갱신
            return new long[] {res[0], value[a]}; // 그리고 return
        }
    }

    static void union(int a, int b, int cost) {
        // a 가 무조건 가벼운 것이라고 가정하고
        long[] aRes = find(a);
        long[] bRes = find(b);

        if (aRes[0] != bRes[0]) { // 부모가 같지 않을 때
            parent[(int) bRes[0]] = (int) aRes[0]; // 부모로 지정해주고
            value[(int) bRes[0]] = (aRes[1] + cost) - bRes[1]; // b 의 부모가 가르키고 있는 부모의 값을 갱신해준다.
        }
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/DAY08/P3830/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        while (true) {
            st = new StringTokenizer(br.readLine());

            int N = Integer.parseInt(st.nextToken());
            int M = Integer.parseInt(st.nextToken());

            if (N == 0 && M == 0) {
                break;
            }

            parent = new int[N + 1];
            value = new long[N + 1];

            for (int i = 1; i <= N; i++) {
                parent[i] = i;
            }

            for (int i = 0; i < M; i++) {
                st = new StringTokenizer(br.readLine());

                if (st.nextToken().equals("!")) { // union
                    int a = Integer.parseInt(st.nextToken());
                    int b = Integer.parseInt(st.nextToken());
                    int c = Integer.parseInt(st.nextToken());
                    union(a, b, c);
                } else { // find
                    int a = Integer.parseInt(st.nextToken());
                    int b = Integer.parseInt(st.nextToken());

                    long[] aRes = find(a);
                    long[] bRes = find(b);

                    if (aRes[0] != bRes[0]) { // 두 개의 부모가 같지 않다면
                        sb.append("UNKNOWN").append("\n");
                    } else { // 같다면?
                        sb.append(bRes[1] - aRes[1]).append("\n");
                    }
                }
            }
        }

        System.out.print(sb);
    }
}
