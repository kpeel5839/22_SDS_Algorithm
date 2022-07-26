package DAY07.P1854;

import java.util.*;
import java.io.*;

// Samsung SDS Practice

/**
 * 해야할 것
 * Entry 없는 애들 조사.
 * 방문횟수를 저장할 수 있는 count 변수 초기화
 * Entry 없는 애들 개수랑, 위치 알아내서 -1 로 답을 채운다음, count 변수가 K 이상일 때, 카운트해서 V - Entry 없는 애들 수로 해서 종료조건 설정
 * 그렇게 해서
 * 계속 PriorityQueue 에서 담는다.
 *
 * 그리고서 poll 하면서 값을 처리할 때, 해당 지점이 나왔다라는 것은 현재 무조건 최소값이기에,
 * count 를 ++ 해준다.
 * 만약 이미 값에 도달한 상태라면 ans 에 넣지 않고
 * 값에 도달하지 않은 상태라면 k를 달성한 애들 count++ 해주고 만일 딱 K 가 되었다면 답 변수에도 넣어준다.
 *
 * 그리고 K 가 1 이라면 특수한 상황이다.
 * 시작 정점에 답을 0으로 넣어주자.
 */
public class Main2 {
    static int V;
    static int E;
    static int K;
    static int[] count;
    static int[] res;
    static List<ArrayList<int[]>> graph = new ArrayList<>();
    static PriorityQueue<int[]> queue = new PriorityQueue<>((o1, o2) -> o1[1] - o2[1]);

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/DAY07/P1854/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        V = Integer.parseInt(st.nextToken());
        E = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        count = new int[V + 1];
        res = new int[V + 1];

        for (int i = 0; i <= V; i++) {
            graph.add(new ArrayList<>());
        }

        for (int i = 0; i < E; i++) {
            st = new StringTokenizer(br.readLine());

            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());

            graph.get(a).add(new int[] {b, c});
        }

        Arrays.fill(res, -1);
        queue.add(new int[] {1, 0});

        while (!queue.isEmpty()) {
            int[] p = queue.poll();

            if (count[p[0]] == K) {
                continue;
            }

            count[p[0]]++;
            res[p[0]] = p[1];

            for (int[] next : graph.get(p[0])) {
                queue.add(new int[] {next[0], next[1] + p[1]}); // 다음 정점으로 증가된 값을 넘겨준다.
            }
        }

        StringBuilder sb = new StringBuilder();

        for (int i = 1; i <= V; i++) {
            sb.append(count[i] == K ? res[i] : -1).append("\n");
        }

        System.out.print(sb);
    }
}
