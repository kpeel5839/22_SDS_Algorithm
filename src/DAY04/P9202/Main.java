package DAY04.P9202;

import java.util.*;
import java.io.*;
import java.util.function.Function;

// Samsung SDS Practice

// 얻을 수 있는 최대 점수, 가장 긴 단어, 찾은 단어의 개수를 출력한다.
// 1글자, 2글자로 이루어진 단어는 0점, 3글자, 4글자는 1점, 5글자는 2점, 6글자는 3점, 7글자는 5점, 8글자는 11점
public class Main {
    static int N;
    static int M;
    static int totalScore;
    static int wordCnt;
    static String word; // 얻은 word 리스트
    static Node root = new Node("");
    static int[] dx = {0, 1, 1, 1, 0, -1, -1, -1};
    static int[] dy = {-1, -1, 0, 1, 1, 1, 0, -1};
    static char[][] grid;
    static boolean[][] visited;
    static class Node {
        String word;
        boolean isWord; // 현재 여기가 Word 의 마지막 부분인지
        boolean isHit; // 이미 맞은 적이 있는지
        HashMap<Character, Node> child; // 현재 자식들

        Node(String word) { // 초기화는 이런식으로 한다.
            this.word = word;
            this.child = new HashMap<>();
            isWord = false;
            isHit = false;
        }
    }

    static int calScore(int length) { // 찾은 단어의 length 를 받게 되면 점수를 계산해주는 메소드
        return (length <= 2) ? 0
                : (length <= 4) ? 1
                : (length == 5) ? 2
                : (length == 6) ? 3
                : (length == 7) ? 5 : 11; // 점수를 계산해주는 식이다.
    }

    static void clearHit(Node cur) { // 모든 trie 를 돌면서
        cur.isHit = false; // hit 을 false 로 초기화 해준다.

        if (cur.child.size() == 0) { // 리프 노드이니, return 해준다.
            return;
        }

        for (Character c : cur.child.keySet()) {
            clearHit(cur.child.get(c)); // 이런식으로 재귀 호출 해준다.
        }
    }

    static void init(int depth, Node cur, String word) { // word 를 가지고, init 을 진행해줄 것이다.
        // 일단 처음 딱 받으면 현재 depth 의 글자를 보고 현재 root 에서 map 에 존재하면 그대로 가고, 아니면 만들어서 간다.
        if (depth == word.length()) {
            cur.isWord = true; // 단어라고 표시해준다.
            return; // return 을 안해 줬었다.
        }

        char c = word.charAt(depth); // 현재 글자를 빼낸다.

        if (!cur.child.containsKey(c)) {
            cur.child.put(c, new Node(cur.word.equals("") ? Character.toString(c) : cur.word + c)); // 루트 노드가 아니라면, 현재 단어에다가 char 를 붙혀준다.
        }

        init(depth + 1, cur.child.get(c), word);
    }

    static void dfs(int depth, int y, int x, Node cur) {
        // 현재 Cur Node 의 단어가 끝인지 (isHit 확인하고, Score 계산과 word 에 담고, 찾은 단어 개수 ++)
        if (depth == 8) {
            return;
        }

        visited[y][x] = true; // 방문 체크

        if (cur.isWord && !cur.isHit) { // 단어의 끝이면서, 맞춘적이 없을 때
            totalScore += calScore(cur.word.length());

            if (word.length() < cur.word.length()) {
                word = cur.word;
            } else if (word.length() == cur.word.length()) {
                if (0 < word.compareTo(cur.word)) {
                    word = cur.word;
                }
            }

            wordCnt++;
            cur.isHit = true;
        }

        // 8 방향 탐색
        for (int i = 0; i < 8; i ++) {
            int ny = y + dy[i];
            int nx = x + dx[i];

            if (outOfRange(ny, nx) || visited[ny][nx]) { // 방문한적이 있는지와 범위를 벗어나면
                continue;
            }

            if (cur.child.containsKey(grid[ny][nx])) { // 현재 자식이 가려는 방향의 알파벳을 가지고 있는지, 가지고 있으면 가면 된다
                // 아니니까 이제 가려는 방향으로 가주면 된다.
                dfs(depth + 1, ny, nx, cur.child.get(grid[ny][nx])); // 현재 가지고 있는 자식을 dfs 로 넘겨주면서 진행한다. (visited 배열이 있으니까, 그냥 따로 종료조건은 만들지 않는다.)
            }
        }

        visited[y][x] = false; // 다시 돌아가면서 체크아웃
    }

    static boolean outOfRange(int y, int x) {
        if ((y < 0 || y >= 4) || (x < 0 || x >= 4)) {
            return true;
        } else {
            return false;
        }
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/DAY04/P9202/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        Function<String, Integer> fun = Integer::parseInt;

        // 단어의 개수를 받는다.
        N = fun.apply(br.readLine());

        for (int i = 0; i < N; i++) { // trie 를 초기화해준다.
            init(0, root, br.readLine());
        }

        br.readLine();
        M = fun.apply(br.readLine());

        while (M-- > 0) { // M 이 0 보다 클 때까지만 진행
            grid = new char[4][4];
            totalScore = 0;
            wordCnt = 0;
            word = "";

            for (int i = 0; i < 4; i++) {
                String string = br.readLine();
                for (int j = 0; j < 4; j++) {
                    grid[i][j] = string.charAt(j);
                }
            } // grid 를 채워준다.

            // 대망의 dfs 를 어떻게 실행할지에 대한 이야기이다.
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    if (root.child.containsKey(grid[i][j])) { // grid 의 알파벳 지금 현재 이 알파벳이 시작하는 알파벳으로 있나
                        visited = new boolean[4][4]; // 이제 돌면서 다 초기화 해준다.
                        dfs(0, i, j, root.child.get(grid[i][j])); // 있다면 현재 root.child 가 가지고 있는 자식을 넘겨주면 된다.
                    }
                }
            }

            sb.append(totalScore + " " + (word.equals("") ? "" : word + " ") + wordCnt + "\n"); // 다 출력해준다.
            clearHit(root); // 다 실행한 뒤 clearHit 초기화
            br.readLine(); // 공백 제거
        }

        System.out.print(sb);
    }
}
