package DAY03.P10828;

import java.util.*;
import java.io.*;
import java.util.function.Function;

// Samsung SDS Practice

public class Main {
    static Stack<Integer> stack = new Stack<>();

    static int execute(String command) {
        String[] input = command.split(" ");

        switch (input[0]) {
            case "push" :
                stack.push(Integer.parseInt(input[1]));
                return -2;
            case "pop" :
                if (stack.isEmpty()) {
                    return -1;
                }

                return stack.pop();
            case "size" :
                return stack.size();
            case "empty" :
                return stack.isEmpty() ? 1 : 0;
            case "top" :
                if (stack.isEmpty()) {
                    return -1;
                }

                return stack.peek();
        }

        return -2;
    }
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/DAY03/P10828/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        Function<String, Integer> fun = Integer::parseInt;
        int N = fun.apply(br.readLine());

        for (int i = 0; i < N; i++) {
            int judge = execute(br.readLine());

            if (judge == -2) {
                continue;
            } else {
                sb.append(judge).append("\n");
            }
        }

        System.out.print(sb);
    }
}
