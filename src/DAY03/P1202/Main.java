package DAY03.P1202;

import java.util.*;
import java.io.*;
import java.util.function.Function;

// Samsung SDS Practice

public class Main {
    static class Jewel {
        long weight;
        long value;

        Jewel(long weight, long value) {
            this.weight = weight;
            this.value = value;
        }

        @Override
        public String toString() {
            return "weight : " + weight + " value : " + value;
        }
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/DAY03/P1202/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Function<String, Integer> fun = Integer::parseInt;

        PriorityQueue<Jewel> queue = new PriorityQueue<>((o1, o2) -> Long.compare(o2.value, o1.value));
        String[] input = br.readLine().split(" ");
        int N = fun.apply(input[0]);
        int M = fun.apply(input[1]);

        Jewel[] jewel = new Jewel[N];
        for (int i = 0; i < N; i++) {
            input = br.readLine().split(" ");
            jewel[i] = new Jewel(fun.apply(input[0]), fun.apply(input[1]));
        }

        int[] bag = new int[M];
        for (int i = 0; i < M; i++) {
            bag[i] = fun.apply(br.readLine());
        }

        Arrays.sort(jewel, (o1, o2) -> Long.compare(o1.weight,o2.weight));
        Arrays.sort(bag);

        int pointer = 0;
        long ans = 0;
//        System.out.println(Arrays.toString(bag));
//        System.out.println(Arrays.toString(jewel));

        for (int i = 0; i < M; i++) {
            while (pointer < N && jewel[pointer].weight <= bag[i]) {
                queue.add(jewel[pointer++]);
            }

            if (!queue.isEmpty()) {
                ans += queue.poll().value;
            }
        }

        System.out.println(ans);
    }
}
