package DAY03.P1927;

import java.util.ArrayList;
import java.util.List;

public class MinHeap {
    List<Integer> list;

    public MinHeap() {
        this.list = new ArrayList<>();
        list.add(0); // 첫번째 자리를 0 으로 채워준다.
    }

    public void insert(int val) {
        list.add(val);
        int current = list.size() - 1;
        int parent = current / 2;

        while (true) {
            if (parent == 0 || list.get(parent) <= list.get(current)) {
                break;
            }

            int temp = list.get(parent);
            list.set(parent, list.get(current));
            list.set(current, temp);

            current = parent;
            parent = current / 2;
        }
    }

    public int delete() {
        if (list.size() == 1) {
            return 0;
        }

        // 1. Root 에 leaf 마지막 데이터 가져옴
        int top = list.get(1);
        list.set(1, list.get(list.size() - 1));
        list.remove(list.size() - 1);

        // 2. 자식과 비교 후 조건이 맞지 않으면 swap
        // 3. 조건이 만족되거나 leaf 까지 반복
        int currentPos = 1;
        while (true) {
            int leftPos = currentPos * 2;
            int rightPos = currentPos * 2 + 1;

            // 왼쪽 자식 먼저 확인
            if (leftPos >= list.size()) { // 자식이 없는 경우 (이렇게 확인한 이유는 완전 이진트리이기 떄문에)
                break;
            }

            int minValue = list.get(leftPos);
            int minPos = leftPos;

            // 오른쪽 자식 확인
            if (rightPos < list.size() && minValue > list.get(rightPos)) { // 오른쪽 자식이 있고, 왼쪽 자식보다 작은 경우
                minValue = list.get(rightPos);
                minPos = rightPos;
            }

            // swap
            if (list.get(currentPos) > minValue) {
                int temp = list.get(currentPos);
                list.set(currentPos, minValue);
                list.set(minPos, temp);
                currentPos = minPos;
            }
        }

        return top;
    }
}
