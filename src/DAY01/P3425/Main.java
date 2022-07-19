package DAY01.P3425;

import java.util.*;
import java.io.*;
import java.util.function.Function;

import static java.lang.Math.abs;

// Samsung SDS Practice

/**
 * -- 틀 설계
 * NUM X: X를 스택의 가장 위에 저장한다. (0 ≤ X ≤ 10 ^ 9)
 * POP : 스택 가장 위의 숫자를 제거한다.
 * INV : 첫 번째 수의 부호를 바꾼다. (42 -> -42)
 * DUP : 첫 번째 숫자를 하나 더 스택의 가장 위에 저장한다.
 * SWP : 첫 번째 숫자와 두 번째 숫자의 위치를 서로 바꾼다.
 * ADD : 첫 번째 숫자와 두 번째 숫자를 더한다.
 * SUB : 첫 번째 숫자와 두 번째 숫자를 뺀다. (두 번째 - 첫 번째)
 * MUL : 첫 번째 숫자와 두 번째 숫자를 곱한다.
 * DIV : 첫 번째 숫자로 두 번째 숫자를 나눈 몫을 저장한다. 두 번째 숫자가 피제수, 첫 번째 숫자가 제수이다.
 * MOD : 첫 번째 숫자로 두 번째 숫자를 나눈 나머지를 저장한다. 두 번째 숫자가 피제수, 첫 번째 숫자가 제수이다.
 *
 * 이러한 명령어들이 있고
 * 이것을 그냥 각각 처리하면 된다.
 * 하지만 이 프로그램 command List 들을 저장해놓고
 * 초기에 값이 주어진 뒤, 모든 연산이 진행된 후의 stack 에 대한 연산을 진행해야 한다.
 * 이 때, Stack 의 Size 가 1 이 아니라면 ERROR 라는 문구를 출력하면 된다.
 *
 * 그리고 또 처리할 때의 특이사항들은 이렇게 존재한다.
 *
 * 숫자가 부족해서 연산을 수행할 수 없을 때,
 * 0으로 나눴을 때 (DIV, MOD),
 * 연산 결과의 절댓값이 10 ^ 9를 넘어갈 때는 모두 프로그램 에러이다.
 *
 * 음수 나눗셈에 대한 모호함을 피하기 위해 다음과 같이 계산한다.
 * 나눗셈의 피연산자에 음수가 있을 때는,
 * 그 수를 절댓값을 씌운 뒤 계산한다. 그리고 나서 몫과 나머지의 부호는 다음과 같이 결정한다.
 * 피연산자중 음수가 한 개일때는 몫의 부호가 음수이다. 이 경우를 제외하면 몫의 부호는 항상 양수이다.
 * 나머지의 부호는 피제수의 부호와 같다. 따라서,
 * 13 div -4 = -3, -13 mod 4 = -1, -13 mod -4 = -1이다.
 *
 * 프로그램 에러가 발생했을 경우에는,
 * 현재 프로그램의 수행을 멈추고, 그 다음 어떤 명령도 수행하지 않는다.
 */
public class Main {
    static String[] command;
    static Stack<Long> stack;
    static long errorCritical = 1_000_000_000; // Stack 내부의 절댓값이 해당 값을 넘어가면 Error 이다. (에러 임계치)

    static boolean execute() {
        for (int i = 0; i < command.length; i++) {
            String[] input = command[i].split(" ");

            // 연산 결과의 절댓값이 10 ^ 9를 넘어갈 때는 모두 프로그램 에러이다.
            // 이항 연산자의 경우에 첫 번째 숫자가 오른쪽에 있는 수이고,
            // 두 번째 숫자가 왼쪽에 있는 수이다.
            // 또, 연산을 수행하기 전에 두 숫자를 모두 스택에서 제거한 뒤
            // 결과를 다시 스택에 저장하는 것이다.
            // 숫자가 부족해서 연산을 수행할 수 없을 때,
            // 0으로 나눴을 때 (DIV, MOD)

            /**
             * 나눗셈의 피연산자에 음수가 있을 때는,
             * 그 수를 절댓값을 씌운 뒤 계산한다. 그리고 나서 몫과 나머지의 부호는 다음과 같이 결정한다.
             * 피연산자중 음수가 한 개일때는 몫의 부호가 음수이다. 이 경우를 제외하면 몫의 부호는 항상 양수이다.
             * 나머지의 부호는 피제수의 부호와 같다. 따라서,
             * 13 div -4 = -3, -13 mod 4 = -1, -13 mod -4 = -1이다.
             */
            long a;
            long b;
            long res;
            boolean minus;

            switch (input[0]) {

                case "NUM" : // NUM X: X를 스택의 가장 위에 저장한다. (0 ≤ X ≤ 10 ^ 9)
                    stack.add(Long.parseLong(input[1])); // 저장
                    break;
                case "POP" : // POP : 스택 가장 위의 숫자를 제거한다.
                    if (stack.isEmpty()) {
                        return false;
                    }

                    stack.pop();
                    break;
                case "INV" : // INV : 첫 번째 수의 부호를 바꾼다. (42 -> -42)
                    if (stack.isEmpty()) {
                        return false;
                    }

                    long number = stack.pop();
                    stack.add(-1 * number);
                    break;
                case "DUP" : // DUP : 첫 번째 숫자를 하나 더 스택의 가장 위에 저장한다.
                    if (stack.isEmpty()) {
                        return false;
                    }

                    stack.add(stack.peek());
                    break;
                case "SWP" : // SWP : 첫 번째 숫자와 두 번째 숫자의 위치를 서로 바꾼다.
                    if (stack.size() < 2) {
                        return false;
                    }

                    a = stack.pop();
                    b = stack.pop();

                    stack.add(a);
                    stack.add(b);
                    break;
                case "ADD" : // ADD : 첫 번째 숫자와 두 번째 숫자를 더한다.
                    if (stack.size() < 2) {
                        return false;
                    }

                    a = stack.pop();
                    b = stack.pop();

                    if (abs(b + a) > errorCritical) {
                        return false;
                    }

                    stack.add(b + a);
                    break;
                case "SUB" : // SUB : 첫 번째 숫자와 두 번째 숫자를 뺀다. (두 번째 - 첫 번째)
                    if (stack.size() < 2) {
                        return false;
                    }

                    a = stack.pop();
                    b = stack.pop();

                    if (abs(b - a) > errorCritical) {
                        return false;
                    }

                    stack.add(b - a);
                    break;
                case "MUL" : // MUL : 첫 번째 숫자와 두 번째 숫자를 곱한다.
                    if (stack.size() < 2) {
                        return false;
                    }

                    a = stack.pop();
                    b = stack.pop();

                    if (abs(a * b) > errorCritical) {
                        return false;
                    }

                    stack.add(a * b);
                    break;
                case "DIV" : // DIV : 첫 번째 숫자로 두 번째 숫자를 나눈 몫을 저장한다. 두 번째 숫자가 피제수, 첫 번째 숫자가 제수이다. (둘중 하나만 음수일 때 음수)
                    if (stack.size() < 2) {
                        return false;
                    }

                    a = stack.pop();
                    b = stack.pop();

                    minus = false;

                    if ((a < 0 && b >= 0) || (b < 0 && a >= 0)) {
                        minus = true;
                    }

                    if (a == 0) { // 0 로 나눌 때
                        return false;
                    }

                    res = abs(b) / abs(a);
                    res = minus ? -1 * res : res;

                    stack.add(res);
                    break;
                case "MOD" : // MOD : 첫 번째 숫자로 두 번째 숫자를 나눈 나머지를 저장한다. 두 번째 숫자가 피제수, 첫 번째 숫자가 제수이다. (무조건 제수의 부호를 따라감)
                    if (stack.size() < 2) {
                        return false;
                    }

                    a = stack.pop();
                    b = stack.pop();

                    minus = b < 0;

                    if (a == 0) {
                        return false;
                    }

                    res = abs(b) % abs(a);
                    res = minus ? -1 * res : res;

                    stack.add(res);
                    break;
            }
        }

        return true;
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/DAY01/P3425/input.txt"));
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Function<String, Integer> fun = Integer::parseInt;

        Loop:
        while (true) { // 여기서 계속 받아주어야 함
            List<String> commandList = new ArrayList<>();

            while (true) { // End 까지 받기
                String string = br.readLine();

                if (string.equals("END")) { // Quit 일 때에도 break;
                    break;
                }

                if (string.equals("QUIT")) { // 아얘 탈출
                    break Loop;
                }

                commandList.add(string); // End 혹은 Quit 이 아니라면 그냥 계속 받아준다.
            }

            command = commandList.toArray(String[]::new); // 변환
//            System.out.println(Arrays.toString(command));
            int N = fun.apply(br.readLine());

            for (int i = 0; i < N; i++) {
                stack = new Stack<>();
                stack.add(Long.parseLong(br.readLine()));
                if (execute()) { // 비정상 종료가 아니라면
                    if (stack.size() == 1) { // stack size 가 1 이라면 값을 출력
                        sb.append(stack.pop()).append("\n");
                    } else { // 아니라면 Error 를 출력
                        sb.append("ERROR\n");
                    }
                } else { // 비정상 종료이면
                    sb.append("ERROR\n");
                }

            }

            sb.append("\n");
            br.readLine(); // 공백제거
        }

        System.out.print(sb);
    }
}
