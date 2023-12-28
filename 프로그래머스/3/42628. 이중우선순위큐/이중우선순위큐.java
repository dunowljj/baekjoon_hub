import java.util.*;

class Solution {
    public int[] solution(String[] operations) {
        int[] answer = new int[2];

        PriorityQueue<Integer> pq = new PriorityQueue<>();
        TreeMap<Integer, Integer> treeMap = new TreeMap();

        for (String operation : operations) {
            int num = Integer.parseInt(
                    operation.split(" ")[1]
            );

            if (operation.startsWith("I")) {
                treeMap.put(num, treeMap.getOrDefault(num , 0) + 1);
            }

            // 최솟값 삭제
            else if (!treeMap.isEmpty() && operation.startsWith("D -")) {
                treeMap.remove(treeMap.firstKey());
            }

            // 최댓값 삭제
            else if (!treeMap.isEmpty() && operation.startsWith("D")) {
                treeMap.remove(treeMap.lastKey());
            }
        }

        if (!treeMap.isEmpty()) {
            answer = new int[]{treeMap.lastKey(), treeMap.firstKey()};
        }

        return answer;
    }
}
/**
 * 최댓값/최솟값을 삭제하는 연산에서 최댓값/최솟값이 둘 이상인 경우, 하나만 삭제
 * 큐가 비어있다면 삭제연산 무시
 * 중복된 값의 처리를 신경쓰지 못했다. --> Map으로 바꿔야할듯
 */