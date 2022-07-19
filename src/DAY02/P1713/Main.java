package DAY02.P1713;

import java.util.*;
import java.io.*;
import java.util.function.Function;

public class Main {
    static class Student implements Comparable<Student> {
        int a; // number
        int b; // time
        int c; // recommend

        Student(int a, int b, int c) {
            this.a = a;
            this.b = b;
            this.c = c;
        }

        @Override
        public int compareTo(Student o) {
            if (c < o.c) {
                return -1;
            } else if (c == o.c) {
                return b - o.b;
            } else {
                return 1;
            }
        }

        public int getA() {
            return a;
        }

        @Override
        public String toString() {
            return "a : " + a + " b : " + b + " c : " + c;
        }
    }
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/DAY02/P1713/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Function<String, Integer> fun = Integer::parseInt;

        int N = fun.apply(br.readLine());
        int M = fun.apply(br.readLine());
        int time = 0;
        String[] input = br.readLine().split(" ");
        List<Student> list = new ArrayList<>();

        for (int i = 0; i < M; i++) {
            int d = fun.apply(input[i]);
            boolean find = false;

            for (Student student : list) {
                if (student.a == d) {
                    find = true;
                    student.c = student.c + 1;
                    break;
                }
            }

            if (!find) {
                if (list.size() == N) {
                    Collections.sort(list);
                    list.set(0, new Student(d, time, 1));
                }

                if (list.size() != N) {
                    list.add(new Student(d, time, 1));
                }
            }

//            System.out.println(list);
            time++;
        }

        Collections.sort(list, Comparator.comparingInt(Student::getA));

        for (Student student : list) {
            System.out.print(student.a + " ");
        }
    }
}
