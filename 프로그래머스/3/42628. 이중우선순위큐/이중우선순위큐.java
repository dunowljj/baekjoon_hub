import java.util.*;

class Solution {
    public int[] solution(String[] operations) {
        int[] answer = new int[2];

        TreeSet<Integer> treeSet = new TreeSet<>();

        for (String operation : operations) {
            int num = Integer.parseInt(
                    operation.split(" ")[1]
            );

            if (operation.startsWith("I")) {
                treeSet.add(num);
            }

            // 최솟값 삭제
            else if (!treeSet.isEmpty() && operation.startsWith("D -")) {
                treeSet.remove(treeSet.first());
            }

            // 최댓값 삭제
            else if (!treeSet.isEmpty() && operation.startsWith("D")) {
                treeSet.remove(treeSet.last());
            }
        }

        if (!treeSet.isEmpty()) {
            answer = new int[]{treeSet.last(), treeSet.first()};
        }

        return answer;
    }
}
/**
 * 최댓값/최솟값을 삭제하는 연산에서 최댓값/최솟값이 둘 이상인 경우, 하나만 삭제
 * 큐가 비어있다면 삭제연산 무시
 */