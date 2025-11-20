import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int T = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();

        while (T-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int F = Integer.parseInt(st.nextToken());
            int R = Integer.parseInt(st.nextToken());
            int N = Integer.parseInt(st.nextToken());

            List<Integer>[] building = new ArrayList[F + 1];
            List<Integer>[] steps = new ArrayList[F + 1];

            for (int i = 1; i <= F; i++) {
                building[i] = new ArrayList<>();
                steps[i] = new ArrayList<>();
            }

            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());

                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());

                building[a].add(b);
            }

            for (int f = 1; f <= F; f++) {
                Collections.sort(building[f]);
            }

            for (int f = 1; f <= F; f++) {
                if (building[f].size() > 0) steps[f].add(building[f].get(0));

                for (int i = 0; i < building[f].size() - 1; i++) {
                    int step = building[f].get(i + 1) - building[f].get(i);
                    steps[f].add(step);
                }

                if (building[f].size() > 0) steps[f].add(R + 1 - building[f].get(building[f].size() - 1));
            }

            int total = 0;
            for (int f = 1; f <= F; f++) {
                int floorSum = 0;
                int maxStep = 0;
//                System.out.println("[F="+f+"]");
                for (int step :steps[f]){
                    floorSum += step;
                    maxStep = Math.max(maxStep, step);

//                    System.out.print(step+" ");
                }
//                System.out.println();

                total += floorSum - maxStep;
            }

            total = (total*2) + (F * 2) + R + 1;

            sb.append(total).append(System.lineSeparator());
        }

        System.out.print(sb);
    }
}
/**
 * 전광판을 끄기 전에는 왼쪽 엘레베이터만 탑승 가능.
 * 끈 후에는 오른쪽만 탑승 가능.
 *
 * 꺼진 불 0, 켜진 불 1이라고 가정
 * 각 켜진 위치에 대해 사이사이 0의 개수를 센다. (step)
 * 양쪽에서 step이 짧은 것 부터 탐색해서 거리를 잰다.
 *
 * 결국 0이 가장 많은 구간 1개를 스킵 가능한 것.
 */