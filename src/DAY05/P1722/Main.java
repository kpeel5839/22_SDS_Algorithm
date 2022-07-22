package DAY05.P1722;

import java.util.*;
import java.io.*;
import java.util.function.Function;

// Samsung SDS Practice

public class Main {
    static long[] fac = new long[21];
    static boolean[] visited;

    static void calFac() {
        fac[0] = 1;
        fac[1] = 1;

        for (int i = 2; i <= 20; i++) {
            fac[i] = fac[i - 1] * i;
        }
    }

    static void judge1(int N, long n) { // 순번을 받고 수열을 출력
        // 이거는 똑같이 방문하면서 계속 n 을 변환시켜준다.
        // 아직 선택되지 않은 애들부터 순서대로 팩토리얼을 곱해주면서 n 을 변경해간다.
        // n <= fac 이면 해당 숫자를 선택하고 넘어간다.
        // 그 행위를 N == 0 이 될 때 까지 진행한다.
        if (N == 0) {
            return;
        }

        for (int i = 1; i < visited.length; i++) {
            if (!visited[i]) { // 지금까지 방문하지 않은애들로만 해야함
                if (n <= fac[N - 1]) {
                     System.out.print(i + " ");
                     visited[i] = true;
                     break;
                 } else {
                    n -= fac[N - 1];
                 }
            }
        }

        N--;
        judge1(N, n);
   }

    static void judge2(int N, int[] arr, long n) { // 수열을 받고 몇번째인지
        // 이거는 일단 해당 숫자를 고정시키기 위해 visited 배열을 보고 현재 visited[arr[N]] = true 로 고정 시켜준다.
        // 그리고 현재 숫자보다 낮은 놈은 내 앞에 있을테니까 그 수를 보고 팩토리얼을 계산해준다.
        // 그러면서 순차적으로 수를 더해준다.
        if (N == 0) {
            System.out.println(n + 1L);
            return;
        }

        visited[arr[N]] = true; // 이미 방문했다.

        for (int i = 1; i < arr[N]; i++) {
            if (!visited[i]) { // 아직 방문하지 않은 애들에만 한해서
                n += fac[N - 1];
            }
        }

        N--;
        judge2(N, arr, n);
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/DAY05/P1722/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Function<String, Integer> fun = Integer::parseInt;

        int N = fun.apply(br.readLine());
        visited = new boolean[N + 1];
        calFac();
        String[] input = br.readLine().split(" ");
        int judge = fun.apply(input[0]);

        if (judge == 1) { // 1이면 순번을 받고
            judge1(N, Long.parseLong(input[1])); // 호출해주고
        } else {
            int[] arr = new int[N + 1];
            int index = 1;

            for (int i = N; i != 0; i--) {
                arr[i] = fun.apply(input[index++]);
            }

            judge2(N, arr, 0);
        }
    }
}
