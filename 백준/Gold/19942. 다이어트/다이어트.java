import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {

    private static int N,mp,mf,ms,mv;
    private static int minPrice = Integer.MAX_VALUE;
    private static List<Integer> minComb;
    private static List<Ingredient> ingredients;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());
        st = new StringTokenizer(br.readLine());
        mp = Integer.parseInt(st.nextToken());
        mf = Integer.parseInt(st.nextToken());
        ms = Integer.parseInt(st.nextToken());
        mv = Integer.parseInt(st.nextToken());

        ingredients = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            int p = Integer.parseInt(st.nextToken());
            int f = Integer.parseInt(st.nextToken());
            int s = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());

            ingredients.add(new Ingredient(p, f, s, v, cost));
        }

        findComb(0, 0, new Ingredient(0, 0, 0, 0, 0), new ArrayList<>());

        if (minPrice == Integer.MAX_VALUE) {
            System.out.print(-1);
            return;
        }

        // 재료 인덱스 +1
        System.out.println(minPrice);
        minComb.stream()
                .sorted()
                .forEach((no) -> System.out.print((no + 1) + " "));
    }

    private static void findComb(int depth, int idx, Ingredient total, List<Integer> comb) {
        if (total.satisfy(mp,mf,ms,mv) && minPrice > total.cost) {
            minComb = new ArrayList<>(comb);
            minPrice = total.cost;
            return;
        }

        if (depth == N) {
            return;
        }

        for (int i = idx; i < ingredients.size(); i++) {
            comb.add(i);
            findComb(depth + 1, i + 1, total.add(ingredients.get(i)), comb);
            comb.remove(comb.size() - 1);
        }
    }

    static class Ingredient {
        int p;
        int f;
        int s;
        int v;
        int cost;

        public Ingredient(int p, int f, int s, int v, int cost) {
            this.p = p;
            this.f = f;
            this.s = s;
            this.v = v;
            this.cost = cost;
        }

        public boolean satisfy(int mp, int mf, int ms, int mv) {
            return p >= mp && f >= mf && s >= ms && v >= mv;
        }

        public Ingredient add(Ingredient i) {
            return new Ingredient(
                    p + i.p,
                    f + i.f,
                    s + i.s,
                    v + i.v,
                    cost + i.cost
            );
        }
    }
}

/**
 * 식재료 최대 15 종류
 * 동일한 식재료를 여러 개 선택할 수 있는지?
 */