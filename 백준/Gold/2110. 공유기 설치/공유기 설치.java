import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int C = Integer.parseInt(st.nextToken());

        List<Integer> points = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            int x = Integer.parseInt(br.readLine());
            points.add(x);
        }

        // 거리를 기준으로 이분탐색 실행
        Collections.sort(points);
        int low = 1;
        int high = points.get(points.size() - 1) + 1;

        while (low + 1 < high) {
            int mid = (low + high) / 2;

            if (canInstallAll(mid, C, points)) {
                low = mid;
            } else {
                high = mid;
            }
        }

        System.out.print(low);
    }

    private static boolean canInstallAll(int distance, int needInstallCount, List<Integer> points) {
        int before = points.get(0);
        int installCount = 1; // 최초 맨 앞에 설치

        for (int i = 1; i < points.size(); i++) {
            int now = points.get(i);

            if (now - before >= distance) {
                installCount ++;
                before = now;
            }
//            if (installCount >= wifiCount) return true;
        }
        return installCount >= needInstallCount;
    }
}
