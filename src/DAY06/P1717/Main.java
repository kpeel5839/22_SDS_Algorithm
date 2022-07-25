package DAY06.P1717;

// Samsung SDS Practice

import java.util.*;
import java.io.*;
import java.util.function.Function;

public class Main {
    static int[] parent;
//    static int[] rank;

    static int find(int a) {
        if (parent[a] == a) {
            return a;
        } else {
            parent[a] = find(parent[a]);
            return parent[a];
        }
    }

    static void union(int a, int b) {
        a = find(a);
        b = find(b);
//        if (rank[a] <= rank[b]) { // a 가 높이가 더 낮거나 같은 경우
//            parent[a] = b;
//
//            if (rank[a] == rank[b]) { // 같았던 경우에는 a 의 랭크를 하나 올려준다.
//                rank[a]++;
//            }
//        } else { // b 가 더 낮은 경우
//            parent[b] = a;
//        }

        if (a < b) {
            parent[a] = b;
        } else {
            parent[b] = a;
        }
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/DAY06/P1717/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        Function<String, Integer> fun = Integer::parseInt;

        String[] input = br.readLine().split(" ");
        int n = fun.apply(input[0]);
        int m = fun.apply(input[1]);

//        rank = new int[n + 1];
        parent = new int[n + 1];

        for (int i = 0; i <= n; i++) {
            parent[i] = i;
        }

        for (int i = 0; i < m; i++) {
            input = br.readLine().split(" ");

            if (input[0].equals("0")) { // union
                union(fun.apply(input[1]), fun.apply(input[2]));
            } else { // find
                int a = find(fun.apply(input[1]));
                int b = find(fun.apply(input[2]));

                if (a == b) {
                    sb.append("YES\n");
//                    bw.write("YES\n");
                } else {
                    sb.append("NO\n");
//                    bw.write("NO\n");
                }
            }
        }

//        bw.flush();
//        bw.close();
        System.out.print(sb);
    }
}
