package DAY01.P1062;

import java.util.*;
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
 */
public class P1062 {
    static int N;
    static int K;
    static String[] word;
    static boolean[] learn = new boolean[26];
    static int ans = 0;

    static boolean readAble(int index) {
        return false;
    }

    static void permutation(int depth ) {

    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/DAY01/P1062/input.txt")); // 입력을 프롬프트 -> 파일 로 변경 (이건 진짜 핵꿀팁이다.)
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Function<String, Integer> fun = Integer::parseInt;

        System.out.println(br.readLine());
//        String[] input = br.readLine().split(" ");
//        N = fun.apply(input[0]);
//        K = fun.apply(input[1]);
//        word = new String[N];
//
//        for (int i = 0; i < N; i++) {
//
//        }
    }
}
