package DAY01.P3055;

import java.util.*;
import java.io.*;
import java.util.function.Function;

// Samsung SDS Practice

/**
 * -- 틀 설계
 * 일단 입력을 받으면서 현재 물의 위치
 * 비버의 위치를 저장해놓고
 * 물을 먼저 움직여준다.
 * 그러고서 비버를 움직여준다.
 *
 * 물이 있는 곳은 비버가 못움직인다
 * 그거 이외에는 신경쓸 부분이 없다.
 */
public class Main {
    static int H;
    static int W;
    static int ans = 0;
    static Point D; // Destination
    static int[] dx = {0, 1, 0, -1};
    static int[] dy = {-1, 0, 1, 0};
    static char[][] map;
//    static boolean[][] visited;
    static Queue<Point> beaver = new LinkedList<>();
    static Queue<Point> water = new LinkedList<>();

    static class Point {
        int y;
        int x;
        int v;

        Point(int y, int x) {
            this.y = y;
            this.x = x;
        }

        Point(int y, int x, int v) {
            this(y, x);
            this.v = v;
        }
    }
    
    static boolean outOfRange(int y, int x) {
        if ((y < 0 || y >= H) || (x < 0 || x >= W)) {
            return true;
        } else {
            return false;
        }
    }
    
    static void bfs() {
        while (!beaver.isEmpty()) {
            int size = water.size();
            for (int i = 0; i < size; i++) {
                Point point = water.poll();
                for (int j = 0; j < 4; j++) {
                    int ny = point.y + dy[j];
                    int nx = point.x + dx[j];

                    if (outOfRange(ny, nx) || map[ny][nx] != '.') { // 바깥으로 나가거나, 벽이거나, 물일때
                        continue;
                    }

                    map[ny][nx] = '*';
                    water.add(new Point(ny, nx));
                }
            }

            size = beaver.size();
            for (int i = 0; i < size; i++) {
                Point point = beaver.poll();

                if (point.y == D.y && point.x == D.x) {
                    ans = point.v;
                }

                for (int j = 0; j < 4; j++) {
                    int ny = point.y + dy[j];
                    int nx = point.x + dx[j];

                    if (outOfRange(ny, nx) || map[ny][nx] == 'X' || map[ny][nx] == '*' || map[ny][nx] == 'S') {
                        continue;
                    }

                    map[ny][nx] = 'S';
                    beaver.add(new Point(ny, nx, point.v + 1));
                }
            }

//            mapPrint();
        }
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/DAY01/P3055/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Function<String, Integer> fun = Integer::parseInt;

        String[] input = br.readLine().split(" ");
        H = fun.apply(input[0]);
        W = fun.apply(input[1]);
        map = new char[H][W];
//        visited = new boolean[H][W];

        for (int i = 0; i < H; i++) {
            String string = br.readLine();
            for (int j = 0; j < W; j++) {
                map[i][j] = string.charAt(j);

                if (map[i][j] == 'S') {
                    beaver.add(new Point(i, j, 0));
                } else if (map[i][j] == 'D') {
                    D = new Point(i, j);
                } else if (map[i][j] == '*') {
                    water.add(new Point(i, j));
                }
            }
        }
        
        bfs();
        System.out.println(ans == 0 ? "KAKTUS" : ans);
    }

    static void mapPrint() {
        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
    }
}
