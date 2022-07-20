package DAY03.P10845;

import java.util.*;
import java.io.*;
import java.util.function.Function;

// Samsung SDS Practice

public class Main {
    static LinkedList<Integer> queue = new LinkedList<>();

    static int execute(String command) {
        String[] input = command.split(" ");

        switch (input[0]) {
            case "push":
                queue.add(Integer.parseInt(input[1]));
                return -2;
            case "pop":
                if (queue.isEmpty()) {
                    return -1;
                }

                return queue.poll();
            case "size":
                return queue.size();
            case "empty":
                return queue.isEmpty() ? 1 : 0;
            case "front":
                if (queue.isEmpty()) {
                    return -1;
                }

                return queue.peekFirst();
            case "back":
                if (queue.isEmpty()) {
                    return -1;
                }

                return queue.peekLast();
        }

        return -2;
    }
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/DAY03/P10845/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        Function<String, Integer> fun = Integer::parseInt;
        int N = fun.apply(br.readLine());

        for (int i = 0; i < N; i++) {
            int judge = execute(br.readLine());

            if (judge != -2) {
                sb.append(judge).append("\n");
            }
        }

        System.out.print(sb);
    }
}
