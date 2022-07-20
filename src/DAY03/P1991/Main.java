package DAY03.P1991;

import java.util.*;
import java.io.*;
import java.util.function.Function;

// Samsung SDS Practice

// 실수로 다 pre 호출함

public class Main {
    static Node root;

    static class Node {
        char name;
        Node left;
        Node right;

        Node(char name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "\n{"
                    + "name : " + name + "\n"
                    + "left : " + left + "\n"
                    + "right : " + right + "\n"
                    + "}\n";
        }
    }

    static void pre(Node cur, StringBuilder sb) { // 출력하고 돌음
        sb.append(cur.name);

        if (cur.left != null) {
            pre(cur.left, sb);
        }

        if (cur.right != null) {
            pre(cur.right, sb);
        }
    }

    static void in(Node cur, StringBuilder sb) { // 중간에 출력
        if (cur.left != null) {
            in(cur.left, sb);
        }

        sb.append(cur.name);

        if (cur.right != null) {
            in(cur.right, sb);
        }
    }

    static void post(Node cur, StringBuilder sb) { // 뒤에 출력
        if (cur.left != null) {
            post(cur.left, sb);
        }

        if (cur.right != null) {
            post(cur.right, sb);
        }

        sb.append(cur.name);
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/DAY03/P1991/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        Function<String, Integer> fun = Integer::parseInt;
        HashMap<Character, Node> map = new HashMap<>();
        int N = fun.apply(br.readLine());

        for (int i = 0; i < N; i++) {
            map.put((char) (i + 'A'), new Node((char) (i + 'A')));
        }


        for (int i = 0; i < N; i++) {
            String[] input = br.readLine().split(" ");
            Node cur = map.get(input[0].charAt(0));

            if (input[1].charAt(0) != '.') {
                cur.left = map.get(input[1].charAt(0));
            }

            if (input[2].charAt(0) != '.') {
                cur.right = map.get(input[2].charAt(0));
            }
        }

        root = map.get('A');

        pre(root, sb);
        sb.append("\n");
        in(root, sb);
        sb.append("\n");
        post(root, sb);

        System.out.print(sb);
    }
}
