package DAY01.P1062;

import java.io.*;
import java.util.function.Function;

// 삼성 SDS Practice

/**
 * -- 틀 설계
 * a, c, n, t, i 를 먼저 배우고
 * 그 다음에 K - 5 를 한 뒤
 * 순서대로 배울 수 있도록 진행을 하고
 *
 * 그 다음에 read 가 가능한지에 대해서 계속 체크해주면서 결과값을 도출해낼 수 있다.
 * a 는 ascii 코드로 97 임을 유의하고 boolean 형 배열로 배운지 안 배운지를 체크해보자.
 *
 * -- 해맸던 점
 * 여느 dfs 와 다르지 않게, 진행하고 있었는데
 * 시간초과에 벽에 부딪혔었다.
 *
 * 그래서 다시 생각을 해보니, 이전 단어들을 다시 탐색할 이유가 전혀 없다라는 것을 기억했다.
 * 그래서 이전 단어들을 다시 탐색하지 않고 depth 부터 실행을 하니 문제를 해결할 수 있었다.
 */
public class Main {
    static int N;
    static int K;
    static String[] word;
    static boolean[] learn = new boolean[26];
    static int ans = 0;

    static boolean readAble(int index) {
        String read = word[index];

        for (int i = 0; i < read.length(); i++) {
            if (!learn[read.charAt(i) - 97]) {
                return false;
            }
        }

        return true;
    }

    static void dfs(int depth, int cnt) {
        /**
         * 1. 체크인
         * 2. 목적지인가?
         * 3. 연결된 곳을 순회
         * 4. 갈 수 있는가?
         * 5. 간다.
         * 6. 체크아웃
         */

        if (cnt == K) { // 목적지인가?
            int readCount = 0;

            for (int i = 0; i < N; i++) {
                if (readAble(i)) {
                    readCount++;
                }
            }

            ans = Math.max(ans, readCount);
            return;
        }

        if (depth == 26) {
            return;
        }

        for (int i = depth; i < 26; i++) { // 연결된 곳을 순회
            if (!learn[i]) { // 갈 수 있는가?
                learn[i] = true; // 체크인
                dfs(i, cnt + 1); // 간다.
                learn[i] = false; // 체크아웃
            }
        }
    }

    public static void main(String[] args) throws IOException {
//        System.setIn(new FileInputStream("src/DAY01/P1062/input.txt")); // 입력을 프롬프트 -> 파일 로 변경 (이건 진짜 핵꿀팁이다.)
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Function<String, Integer> fun = Integer::parseInt;

        String[] input = br.readLine().split(" ");
        N = fun.apply(input[0]);
        K = fun.apply(input[1]);
        word = new String[N];

        for (int i = 0; i < N; i++) {
            word[i] = br.readLine().replaceAll("[antic]", ""); // 단어를 읽어온다
        }

        learn['a' - 97] = true;
        learn['c' - 97] = true;
        learn['n' - 97] = true;
        learn['t' - 97] = true;
        learn['i' - 97] = true;

        if (5 <= K) {
            dfs(1, 5);
        }

        System.out.println(ans);
    }
}
