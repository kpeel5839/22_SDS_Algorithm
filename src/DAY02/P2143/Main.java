package DAY02.P2143;

import java.util.*;
import java.io.*;
import java.util.function.Function;

// Samsung SDS Practice

/**
 * -- 틀 설계
 */
public class Main {
    static void listAdd(List<Long> list, long[] arr) {
        for (int i = 0; i < arr.length; i++) {
            long sum = arr[i];
            list.add(sum);
            for (int j = i + 1; j < arr.length; j++) {
                sum += arr[j];
                list.add(sum);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/DAY02/P2143/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Function<String, Integer> fun = Integer::parseInt;

        long T = fun.apply(br.readLine());
        int N = fun.apply(br.readLine());
        long[] a = new long[N];
        long ans = 0;
        String[] input = br.readLine().split(" ");

        for (int i = 0; i < N; i++) {
            a[i] = fun.apply(input[i]);
        }

        N = fun.apply(br.readLine());
        long[] b = new long[N];
        input = br.readLine().split(" ");

        for (int i = 0; i < N; i++) {
            b[i] = fun.apply(input[i]);
        }

        List<Long> aList = new ArrayList<>();
        List<Long> bList = new ArrayList<>();

        listAdd(aList, a);
        listAdd(bList, b); // 부분배열들을 구해주면서, 바로 배열로 옮겨줌

        a = new long[aList.size()];
        int index = 0;

        for (Long num : aList) {
            a[index++] = num;
        }

        b = new long[bList.size()];
        index = 0;

        for (Long num : bList) {
            b[index++] = num;
        }

        Arrays.sort(a);
        Arrays.sort(b);

//        System.out.println(Arrays.toString(a));
//        System.out.println(Arrays.toString(b));

        int l = 0;
        int r = b.length - 1;

        while (true) {
            if (l == a.length || r == -1) {
                break;
            }

            long sum = a[l] + b[r];

            if (sum == T) {
                long aCnt = 0;
                long bCnt = 0;
                long lv = a[l];
                long rv = b[r];

                while (l != a.length && lv == a[l]) {
                    aCnt++;
                    l++;
                }

                while (r != -1 && rv == b[r]) {
                    bCnt++;
                    r--;
                }

                ans += aCnt * bCnt;
            } else if (sum < T) {
                l++;
            } else {
                r--;
            }
        }

        System.out.println(ans);
    }
}
