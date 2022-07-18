package DAY01.P1713;

import java.util.*;
import java.io.*;
import java.util.function.Function;

// Samsung SDS Practice

/**
 * -- 틀 설계
 * N (틀의 개수)
 * M (추천 받은 개수)
 * 그 다음에 추천을 받는 학생들의 개수가 주어진다.
 *
 * 이 문제는 N = 20 틀의 개수 최대가 20이기 때문에 굉장히 쉽게 풀 수 있다.
 * 그냥 아무렇게나 구현하면 된다.
 *
 * 1. 등록된 후보수를 등록하여, 그냥 후보로 등록할 수 있는지에 대해서 map 에다가 저장한다.
 * 2. 등록된 후보수 즉, map 의 사이즈가 현재 N 보다 더 커지게 되면, 가장 작은 애를 찾아야 하기 때문에 순회한다.
 * 3. 순회하면서, 일단 먼저 추천의 min 값을 기준으로 보고, 만일 min 값이 같은 애가 있다, 그러면 시간이 작은 애를 기준으로 한다.
 * 4. 모든 추천이 끝나면 map -> List 로 변환하여 Sort 를 학생 번호를 기준으로 정렬하여 출력한다.
 */
public class Main {
    static int N;
    static int M;
    static int time;
    static HashMap<Integer, Student> map = new HashMap<>();

    static class Student {
        int num;
        int time;
        int recommend;

        Student(int num, int time, int recommend) {
            this.num = num;
            this.time = time;
            this.recommend = recommend;
        }

        @Override
        public String toString() {
            return "num : " + num + " time : " + time + " recommend : " + recommend;
        }
    }

    static void contains(int number) {
        if (map.containsKey(number)) {
            map.put(number, new Student(number, map.get(number).time, map.get(number).recommend + 1));
        } else {
            map.put(number, new Student(number, time, 1));
        }
    }

    static void solve(int number) {
        if (map.size() < N) { // 그냥 넣음
            contains(number);
        } else {
            if (map.containsKey(number)) {
                contains(number);
                return;
            }

            int minRe = Integer.MAX_VALUE;
            int minTi = Integer.MAX_VALUE;
            int minKey = 0;

            for (Integer key : map.keySet()) {
                Student student = map.get(key);

                if (student.recommend <= minRe) { // 더 작은가
                    if (minRe == student.recommend) {
                        if (student.time < minTi) {
                            minRe = student.recommend;
                            minTi = student.time;
                            minKey = key;
                        }
                    } else {
                        minRe = student.recommend;
                        minTi = student.time;
                        minKey = key;
                    }
                }
            }

            map.remove(minKey);
            contains(number);
        }
    }

    public static void main(String[] args) throws IOException {
//        System.setIn(new FileInputStream("src/DAY01/P1713/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb=  new StringBuilder();
        Function<String, Integer> fun = Integer::parseInt;

        N = fun.apply(br.readLine());
        M = fun.apply(br.readLine());
        String[] input = br.readLine().split(" ");

        for (int i = 0; i < M; i++) {
            solve(fun.apply(input[i]));
//            System.out.println(fun.apply(input[i]));
//            System.out.println(map);
            time++;
        }

        List<Integer> res = new ArrayList<>();
        for (Integer number : map.keySet()) {
            res.add(map.get(number).num);
        }

        Collections.sort(res);
        for (Integer number : res) {
            sb.append(number + " ");
        }

        System.out.print(sb);
    }
}
