package DAY07.P1753;

import java.util.*;
import java.io.*;
import java.util.function.Function;

// Samsung SDS Practice

public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/DAY07/P1753/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Function<String, Integer> fun = Integer::parseInt;

        String[] input = br.readLine().split(" ");
        int V = fun.apply(input[0]);
        int E = fun.apply(input[1]);
        int K = fun.apply(br.readLine());
        List<ArrayList<int[]>> graph = new ArrayList<>();
        int[] dist = new int[V + 1];
        PriorityQueue<int[]> queue = new PriorityQueue<>((o1, o2) -> o1[1] - o2[1]);

        for (int i = 0; i <= V; i++) {
            graph.add(new ArrayList());
            dist[i] = Integer.MAX_VALUE;
        }


        for (int i = 0; i < E; i++) {
            input = br.readLine().split(" ");

            int a = fun.apply(input[0]);
            int b = fun.apply(input[1]);
            int c = fun.apply(input[2]);

            graph.get(a).add(new int[] {b, c});
        }

        dist[K] = 0;
        queue.add(new int[] {K, 0});

        while (!queue.isEmpty()) {
            int[] point = queue.poll();

            if (dist[point[0]] < point[1]) {
                continue;
            }

            for (int[] next : graph.get(point[0])) {
                if (dist[next[0]] > point[1] + next[1]) {
                    dist[next[0]] = point[1] + next[1];
                    queue.add(new int[] {next[0], dist[next[0]]});
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= V; i++) {
            if (dist[i] == Integer.MAX_VALUE) {
                sb.append("INF\n");
            } else {
                sb.append(dist[i] + "\n");
            }
        }

        System.out.print(sb);
    }
}
