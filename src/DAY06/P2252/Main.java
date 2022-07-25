package DAY06.P2252;

import java.util.*;
import java.io.*;
import java.util.function.Function;

// Samsung SDS Practice

public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/DAY06/P2252/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        Function<String, Integer> fun = Integer::parseInt;

        String[] input = br.readLine().split(" ");
        int N = fun.apply(input[0]);
        int M = fun.apply(input[1]);
        int[] entry = new int[N + 1];
        List<ArrayList<Integer>> graph = new ArrayList<>();

        for (int i = 0; i <= N; i++) {
            graph.add(new ArrayList<>());
        }

        for (int i = 0; i < M; i++) {
            input = br.readLine().split(" ");

            int a = fun.apply(input[0]);
            int b = fun.apply(input[1]);
            entry[b]++;

            graph.get(a).add(b);
        }

        Queue<Integer> queue = new LinkedList<>();
        for (int i = 1; i <= N; i++) {
            if (entry[i] == 0) {
                queue.add(i);
            }
        }

        while (!queue.isEmpty()) {
            int a = queue.poll(); // vertex 뽑아낸다.
            sb.append(a + " ");

            for (Integer next : graph.get(a)) {
                if (--entry[next] == 0) {
                    queue.add(next);
                }
            }
        }

        System.out.print(sb);
    }
}
