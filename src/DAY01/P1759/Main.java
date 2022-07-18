package DAY01.P1759;

import java.util.*;
import java.io.*;
import java.util.function.Function;

// Samsung SDS Practice

/**
 * -- 틀 설계
 * 일단 조합을 이용하고
 * 서로 최소 한개의 모음을 포함하고,
 * 두 개의 자음은 있는 L 개의 문자로 이루어져 있는 좋바을 구하면 된다.
 *
 * 모음이 있는지는 HashSet 으로 관리하고
 * HashSet 에 없다면 그것은 자음이라고하고 진행하자.
 */
public class Main {
    static int L;
    static int C;
    static boolean[] letter = new boolean[26];
    static boolean[] let = new boolean[26];
    static StringBuilder sb = new StringBuilder();
    static Set<Character> set = Set.of('a', 'e', 'i', 'o', 'u');

    static void dfs(int depth, int vo, int con, int cnt) {
        if (cnt == L) {
            if (vo >= 1 && con >= 2) {
                for (int i = 0; i < let.length; i++) {
                    if (let[i]) {
                        sb.append((char) (i + 97));
                    }
                }

                sb.append("\n");
            }

            return;
        }

        for (int i = depth; i < 26; i++) {
            if (letter[i]) {
                char c = (char) (i + 97);
                let[i] = true;

                if (set.contains(c)) {
                    dfs(i + 1, vo + 1, con, cnt + 1);
                } else {
                    dfs(i + 1, vo, con + 1, cnt + 1);
                }

                let[i] = false;
            }
        }
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/DAY01/P1759/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Function<String, Integer> fun = Integer::parseInt;

        String[] input = br.readLine().split(" ");
        L = fun.apply(input[0]);
        C = fun.apply(input[1]);

        input = br.readLine().split(" ");
        for (int i = 0; i < C; i++) {
            letter[input[i].charAt(0) - 97] = true;
        }

        dfs(0, 0, 0, 0);
        System.out.print(sb);
    }
}
