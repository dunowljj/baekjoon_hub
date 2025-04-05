import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int d = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        int c = Integer.parseInt(st.nextToken());

        int[] belt = new int[N + 1];
        for (int i = 0; i < N; i++) {
            belt[i] = Integer.parseInt(br.readLine());
        }

        Map<Integer, Integer> counts = new HashMap<>();
        // window 생성
        int left = 0;
        int right = k;
        boolean useCoupon = false;
        for (int i = left; i < right; i++) {
            int kind = belt[i % N];
            if (kind == c) useCoupon = true;
            counts.put(
                    kind,
                    counts.getOrDefault(kind, 0) + 1
            );
        }

        int maxKind = counts.size() + (useCoupon ? 0 : 1);
        // 여기도 쿠폰 처리 필요

        for (int i = 0; i < N; i++) {
            int exclude = belt[(left + i) % N];
            int add = belt[(right + i) % N];

            if (counts.containsKey(exclude)) {
                int count = counts.get(exclude);

                if (count == 1) counts.remove(exclude);
                else counts.put(exclude, count - 1);
            }

            counts.put(
                    add,
                    counts.getOrDefault(add, 0) + 1
            );

            /**
             * 쿠폰 체크
             */
            useCoupon = counts.containsKey(c);
            maxKind = Math.max(maxKind, counts.size() + (useCoupon ? 0 : 1));

//            System.out.print("left = " + (left + i) % N);
//            System.out.println(", excluded = " + belt[(left + i) % N]);
//            System.out.print("right = " + (right + i) % N);
//            System.out.println(", added = " + belt[(right + i) % N]);
//            System.out.println("maxKind = " + maxKind);
//            System.out.println();
        }

        System.out.print(maxKind);
    }
}
/**
 * 쿠폰이 포함되지 않은 채로 k개 먹었는데 거기에 쿠폰 초밥이 없다면, size를 1 추가할 수 있다.
 */