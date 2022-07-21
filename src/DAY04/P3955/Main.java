package DAY04.P3955;

import java.util.*;
import java.io.*;

// Samsung SDS Practice

/**
 * -K * X + C * Y = 1 이라는 방정식을 가지고
 * 주어지는
 * a * X + b * Y = gcd(a, b) 를 가지고 위와 같은 방정식을 구해야 한다.
 * 마지막에 1 % gcd(a, b) == 0 이 맞는지 확인하고 아니라면 IMPOSSIBLE 를 출력한다.
 *
 * 이렇게 X0, Y0 라는 초기해를 구하게 되면 (위의 과정을 통해서)
 * 유도 공식을 통해서 얻어낸 x = X0 + (b / gcd(a, b)) * k 와
 * y = Y0 + (b / gcd(a, b)) * k 를 이용해서
 *
 * k 와 c 가 (1 ≤ K, C ≤ 10 ^ 9) 이 범위 안에 들어오도록 수를 설정하면 된다.
 *
 * 이 과정에서 k 의 범위를 구하는 방법은
 *
 * 0 < x 와
 *
 * 문제의 조건으로 주어진 사탕봉지를 10 ^ 9 개 구할 수 없다는 것을 이용해서
 * 0 < y <= 10 ^ 9 사이에서
 *
 * 수식을 이용해 k 의 범위를 이용해
 * 거기서 가장 첫번째 값을 가져가면 될 것 같고
 * k 는 음수가 될 수 없다는 것에 주의하자.
 *
 * y 는 음수가 될 수 없다는 것에 유의하자.
 *
 * -- 해맸던 점
 * 그냥 범위가 안맞았던 것 같음
 * 내가 공식 유도해서 다시 침착하게 범위 지정해보니까
 * 맞았음
 *
 * 역시 나는 혼자서 생각해야 하는 사람인가보다..
 */
public class Main {
    static int K;
    static int C;

    static class Gcd {
        long s;
        long t;
        long r;

        Gcd(long s, long t, long r) {
            this.s = s;
            this.t = t;
            this.r = r;
        }
    }

    static Gcd extendGcd(long a, long b) {
        long s0 = 1;
        long t0 = 0;
        long r0 = a;

        long s1 = 0;
        long t1 = 1;
        long r1 = b;

        while (r1 != 0) { // gcd 가 아닐 때까지
            long q = r0 / r1; // 몫을 구하는 과정

            long temp = r0 - (r1 * q);
            r0 = r1;
            r1 = temp;

            // s 와 t 를 계산해주어야 함
            temp = s0 - (s1 * q);
            s0 = s1;
            s1 = temp;

            temp = t0 - (t1 * q);
            t0 = t1;
            t1 = temp;
        }

        return new Gcd(s0, t0, r0);
    }

    static void execute(long a, long b, long x0, long y0) {
        // 이제 0 < x 근데 우리가 처음에 -KX + CY = 1 이 되었으니까, K(-X) + CY = 1 이니, x < 0 이 된다. 반대가 되니
        // x < 0 -> x0 + b * k < 0 -> k < - x0 / b
        // 0 < y <= 10 ^ 9 를 이용해서 해를 구해야 한다.
        // 0 < y0 * (a / d) * k <= 10 ^ 9
        // 0 < y0 + -(a / d) * k <= 10 ^ 9
        // -y0 < - (a / d) * k <= 10 ^ 9 - y0
        // (y0 - 10 ^ 9) / a <= k < y0 / a

        /**
         * 1
         * 1000000000 1 이 반례가 통과가 됨
         *
         * 원래 impossible 이 되어야 하지만
         * 그렇게 안됐음
         *
         * 왜지?
         * 일부로 사탕봉지가 이것도 안넘게 해봤다.
         * 수식으로 했을 때 이게 왜 안되는지 모르겠네
         * 내가 수식을 잘못 적었나?
         */

//        long kFromX = (long) (Math.ceil((double) -x0 / (double) b) - 1);
//        long kFromY = (long) (Math.ceil((double) y0 / (double) a) - 1);
//        long k = Math.min(kFromX, kFromY);
//        long kLimitY = (long) (Math.floor((y0 - 1e9) / (double) a)); // Math.floor 해줘도 된다. 같다도 있응게
//
//        if (kLimitY <= k && 0 <= y0 - a * k && y0 - a * k < 1e9) {
//            System.out.println(y0 - a * k);
//        } else {
//            System.out.println("IMPOSSIBLE");
//        }

        long kLimitX = (long) Math.floor((double) x0 / (double) b) + 1;
        long kLimitY = (long) Math.floor((double) -y0 / (double) a) + 1;
        long kFromY = (long) Math.floor((1e9 - y0) / (double) a);
        long k = Math.max(kLimitX, kLimitY);

        if (k <= kFromY) {
            System.out.println(y0 + a * k);
        } else {
            System.out.println("IMPOSSIBLE");
        }
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/DAY04/P3955/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());

        for (int i = 0; i < N; i++) {
            String[] input = br.readLine().split(" ");
            K = Integer.parseInt(input[0]);
            C = Integer.parseInt(input[1]);

            Gcd res = extendGcd(K, C);

            if (1 % res.r != 0) {
                System.out.println("IMPOSSIBLE");
            } else { // 근데 여기서는 C 가 계속 1이지 않나? c 가 계속 1이니까, d 도 1이다. 그러니 여기서 곱해줘서 넘겨줄 필요는 없다.
                execute(K, C, res.s, res.t); // r 은 최대 공약수
            }
        }
    }
}
