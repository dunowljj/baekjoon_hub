import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Map<Character, TreeSet<Integer>> indexMap;

        int T = Integer.parseInt(br.readLine());

        for (int t = 0; t < T; t++) {
            String W = br.readLine();
            int K = Integer.parseInt(br.readLine());

            // 1처리
            if (K == 1) {
                System.out.println(1 +" " + 1);
                continue;
            }

            // init map
            indexMap = new HashMap<>();
            for (int i = 0; i < W.length(); i++) {
                char now = W.charAt(i);
                if (!indexMap.containsKey(now)) {
                    TreeSet set = new TreeSet();
                    set.add(i);
                    indexMap.put(now, set);
                }
                else {
                    indexMap.get(now).add(i);
                }
            }

            int min = Integer.MAX_VALUE;
            int max = Integer.MIN_VALUE;
            for (Character key : indexMap.keySet()) {
                TreeSet<Integer> indexTree = indexMap.get(key);
                if (indexTree.size() >= K) {
                    int[] result = calculateLen(indexTree, K - 1);
                    min = Math.min(result[0], min);
                    max = Math.max(result[1], max);
                }
            }

            if (min == Integer.MAX_VALUE) System.out.println(-1);
            else System.out.println((min + 1) +" "+(max + 1));

        }
    }

    private static int[] calculateLen(TreeSet<Integer> treeSet, int K) {
        List<Integer> list = new ArrayList<>(treeSet);
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < list.size() - K; i++) {
            int len = list.get(i + K) - list.get(i);
            min = Math.min(min, len);
            max = Math.max(max, len);
        }

        return new int[]{min, max};
    }
}

/**
 * 슬라이딩 윈도우?
 * Map에 TreeMap를 달아서 index를 써놓기? -> 결국 n개만큼 탐색해야지 길이를 찾을 수 있다.
 * 그래도 N + N으로 O(N) 나올 것 같다.
 * 만족 없으면 -1
 */