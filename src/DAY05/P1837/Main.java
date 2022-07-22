package DAY05.P1837;

import java.util.*;
import java.io.*;
import java.util.function.Function;

// Samsung SDS Practice

public class Main {
    static String p;
    static int K;
    static boolean[] isNotPrime;

    static boolean stringDiv(int div) { // 나눠지는지에 대한 여부와 몫을 반환해, 해당 숫자가 소수가 맞는지를 판단한다. 일단 7자리 넘어가면 다 땡임..
        // 몫이 소수인지도 확인해주어야 함, 만일 몫도 소수다? 그러면 true 반환, 짜피 나눠지는 수는 K 미만만 넘어오니까 몫도 소수이면 끝난겨 (근데 너무 큰 수이면? 소수인 것을 알 수 없지 않나??)
        int remain = 0;

        for (int i = 0; i < p.length(); i++) {
            remain *= 10; // 먼저 10 을 곱해주어서 앞으로 밀어주고
            remain += p.charAt(i) - '0'; // 숫자를 추가해준다.
            remain %= div; // 나누는 수로 나눠주고 나머지를 기입해준다, 끝날 때까지 반복한다.
        }

        if (remain == 0) {
            return true;
        }

        return false;
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/DAY05/P1837/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Function<String, Integer> fun = Integer::parseInt;

        String[] input = br.readLine().split(" ");
        p = input[0];
        K = fun.apply(input[1]);

        int limit = (int) Math.floor(Math.sqrt(K));
        isNotPrime = new boolean[K + 1];

        for (int i = 2; i <= limit; i++) { // limit 까지 가면서, 소수를 다 boolean 으로 체크해준다.
            if (!isNotPrime[i]) { // 프라임인 경우
                for (int j = i + i; j <= K; j += i) { // 계속 배수로 가면서 isNotPrime 에다가 true 를 박아넣음
                    isNotPrime[j] = true;
                }
            }
        }

        boolean notGood = false;
        int res = 0;

        for (int i = 2; i < K; i++) { // K 보다 작은 암호이니까
            if (!isNotPrime[i] && stringDiv(i)) {
                notGood = true;
                res = i;
                break;
            }
        } // 나눠지는지 계속 받아줌

        if (notGood) {
            System.out.println("BAD " + res);
        } else {
            System.out.println("GOOD");
        }
    }
}
