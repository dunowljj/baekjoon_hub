import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken()); // 1~100
        int K = Integer.parseInt(st.nextToken()); // 1~100_000

        // 각 무게에서 최대 가치
        int dp[] = new int[K + 1];

        // visited[w] : dp[w]에서 무게가 w일때 사용한 items의 인덱스의 집합
        Set<Integer>[] visited = new HashSet[K + 1];
        for (int i = 0; i < visited.length; i++) {
            visited[i] = new HashSet<>();
        }


        List<Item> items = new ArrayList<>();
        for (int itemNo = 0; itemNo < N; itemNo++) {
            st = new StringTokenizer(br.readLine());

            int weight = Integer.parseInt(st.nextToken());
            int value = Integer.parseInt(st.nextToken());

            // 초깃값
            if (weight < dp.length && dp[weight] < value) {
                dp[weight] = value;
                visited[weight].add(itemNo);
            }

            items.add(new Item(weight, value));
        }

        for (int weight = 1; weight < dp.length; weight++) {
            for (int itemNo = 0; itemNo < items.size(); itemNo++) {

                Item item = items.get(itemNo);

                // 무게 초과
                if (weight + item.weight >= dp.length) continue;

                // 이미 넣은 물건인지 확인
                if (visited[weight].contains(itemNo)) continue;
                if (visited[weight + item.weight].contains(itemNo)) continue;

                if (dp[weight + item.weight] < dp[weight] + item.value) {
                    dp[weight + item.weight] = dp[weight] + item.value;

                    // 사용한 물건 목록 갱신
                    visited[weight + item.weight] = new HashSet<>();
                    visited[weight + item.weight].addAll(visited[weight]);
                    visited[weight + item.weight].add(itemNo);
                }
            }
        }

        int maxVal = 0;
        for (int i = 0; i < dp.length; i++) {
            maxVal = Math.max(maxVal, dp[i]);
        }

        System.out.print(maxVal);
    }

    static class Item {
        int weight;
        int value;

        public Item(int weight, int value) {
            this.weight = weight;
            this.value = value;
        }
    }
}
