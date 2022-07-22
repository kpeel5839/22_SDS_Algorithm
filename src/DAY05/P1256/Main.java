package DAY05.P1256;

import java.util.*;
import java.io.*;
import java.util.function.Function;

// Samsung SDS Practice

public class Main {
    static int N;
    static int M;
    static int T;
    static int K;
    static int[][] pascal;

    static void find() {
        if (T == 0) {
            return;
        }

        // Z 를 넣지 않는 경우, 현재 상황에서, 그게 위의 경우이고, Z 를 넣은 경우는 아래 경우이다. (사전적으로 그러니까) a to z
        int pas = pascal[T - 1][M]; // Z 를 선택하지 않은 경우냐

        if (K <= pas) { // 사전의 단어가 a 를 현재 a 를 선택한 경우에 가있음
            System.out.print("a");
        } else { // 사전의 단어가 현재 z 를 선택한 경우에 가있음
            System.out.print("z");
            K = K - pas;
            M--;
        }

        T--;
        find();
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/DAY05/P1256/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Function<String, Integer> fun = Integer::parseInt;

        String[] input = br.readLine().split(" ");
        N = fun.apply(input[0]);
        M = fun.apply(input[1]);
        K = fun.apply(input[2]);
        T = N + M;

        pascal = new int[T + 1][T + 1];

        for (int i = 0; i <= T; i++) {
            for (int j = 0; j <= i; j++) {
                if (j == 0) {
                    pascal[i][j] = 1;
                } else if (j == i) {
                    pascal[i][j] = 1;
                } else {
                    pascal[i][j] = pascal[i - 1][j - 1] + pascal[i - 1][j]; // nCk = (n-1)C(k-1) + (n-1)Ck
                }

                if (K < pascal[i][j]) {
                    pascal[i][j] = K; // K 보다 크면 K 로 만들어줘도 알고리즘에 무넺가 없다.
                }
            }
        } // pascal 삼각형 생성 완료

        // 이제 실제로 K 부터 찾아가야함
        // Z 를 기준, 즉 M 개를 기준으로 찾아갈 것이다. K 와
        if (K > pascal[T][M]) { // K 가 이것의 초과인 경우는 전체 경우의 수보다 많은 것이다.
            System.out.println(-1);
        } else {
            find();
        }
    }
}
