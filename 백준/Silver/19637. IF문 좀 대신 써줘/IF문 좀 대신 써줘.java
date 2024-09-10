import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder result = new StringBuilder();

        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        List<Title> titles = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());

            String name = st.nextToken();
            int power = Integer.parseInt(st.nextToken());

            titles.add(new Title(name, power));
        }

        for (int i = 0; i < M; i++) {
            int power = Integer.parseInt(br.readLine());
            String findName = binarySearch(titles, power);

            result.append(findName)
                    .append(System.lineSeparator());
        }

        System.out.print(result.toString().trim());
    }

    private static String binarySearch(List<Title> titles, int power) {
        int lo = 0;
        int hi = titles.size() - 1;

        while (lo < hi) {
            int mid = (lo + hi) / 2;

            // titles :
            // F F F T T T T -> 맨 앞 T 찾기
            // T T T
            // F F F
            // 1 2 3 4 5 6
            // 10_000 100_000 1_000_000
            if (power <= titles.get(mid).power) {
                hi = mid;
            } else {
                lo = mid + 1;
            }
        }

        return titles.get(lo).name;
    }

    static class Title {
        String name;
        int power;

        public Title(String name, int power) {
            this.name = name;
            this.power = power;
        }
    }
}

/**
 * two pointer 사용해보기
 *
 * M,N 10^5
 *
 * 입력 순서대로 이분탐색으로 처리하는 경우의 시간 복잡도
 * => NlogN
 * 정렬을 해서 투포인터를 사용하는 경우의 시간 복잡도
 * - 주어지는 전투력들을 정렬 O(MlogM)
 * - 투포인터 탐색 N + M
 * => MlogM + (N + M)
 */