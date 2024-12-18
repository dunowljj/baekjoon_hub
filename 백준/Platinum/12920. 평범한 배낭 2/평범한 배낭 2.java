import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        // <물건 번호, 개수>[무게]
        Map<Integer, Integer>[] used = new HashMap[M + 1];
        for (int i = 0; i < used.length; i++) {
            used[i] = new HashMap<>();
        }
        int dp[] = new int[M + 1];

        List<Item> items = new ArrayList<>();
        int limits[] = new int[N];
        for (int itemNo = 0; itemNo < N; itemNo++) {
            st = new StringTokenizer(br.readLine());
            int weight = Integer.parseInt(st.nextToken());
            int value = Integer.parseInt(st.nextToken());
            int limit = Integer.parseInt(st.nextToken());

//            int temp = 1;
//            while (temp <= count && weight * temp <= M) {
//                dp[weight * temp] = Math.max(dp[weight * temp], value);
//                items.add(new Item(weight, value * temp, temp));
//                temp *= 2;
//            }

            items.add(new Item(weight, value, 1));
            limits[itemNo] = limit;

            if (dp[weight] < value) {
                used[weight] = new HashMap<>();
                used[weight].put(itemNo, 1);
                dp[weight] = value;
            }
        }

        for (int weight = 1; weight <= M; weight++) {
            if (dp[weight] < dp[weight - 1]) {
                dp[weight] = dp[weight- 1];

                used[weight] = new HashMap<>();
                used[weight].putAll(used[weight- 1]);
            }

            for (int itemNo = 0; itemNo < items.size(); itemNo++) {
                Item item = items.get(itemNo);

                if (weight + item.weight > M) continue;

                // 사용한 물품 개수 체크
                int countInBag = used[weight].getOrDefault(itemNo, 0);
                if (countInBag + item.count > limits[itemNo]) continue;

                if (dp[weight + item.weight] < dp[weight] + item.value) {
                    used[weight + item.weight] = new HashMap<>();
                    used[weight + item.weight].putAll(used[weight]);
                    used[weight + item.weight].put(itemNo, countInBag + item.count);

                    dp[weight + item.weight] = dp[weight] + item.value;
                }
            }
        }
//
//        for (int weight = 0; weight <= M; weight++) {
//            System.out.println("dp[weight] = " + dp[weight]);
//        }

        System.out.print(dp[M]);
    }

    static class Item {
        int weight;
        int value;
        int count;

        public Item(int weight, int value, int count) {
            this.weight = weight;
            this.value = value;
            this.count = count;
        }
    }
}
