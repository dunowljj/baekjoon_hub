import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Comparator.comparingInt;

public class Main {

    public static final String SPACE = " ";

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());

        Map<Integer, Integer> yCountMap = new HashMap<>();
        Map<Integer, Integer> xCountMap = new HashMap<>();

        int spaceWithSeedCount = 0;
        List<Point> seeds = new ArrayList<>();

        for (int y = 1; y <= N; y++) {
            st = new StringTokenizer(br.readLine());
            for (int x = 1; x <= M; x++) {
                boolean isSeed = Integer.parseInt(st.nextToken()) == 1;

                if (isSeed) {
                    spaceWithSeedCount++;

                    yCountMap.put(y, yCountMap.getOrDefault(y, 0) + 1);
                    xCountMap.put(x, xCountMap.getOrDefault(x, 0) + 1);
                    seeds.add(new Point(y, x));
                }
            }
        }

        int totalSeedCount = K * 2;

        StringBuilder sb = new StringBuilder();
        int overlapSize = 0;
        List<Point> overlaps = new ArrayList<>();

        // 완전 겹침
        if (spaceWithSeedCount == K) {
            Collections.sort(seeds, comparingInt(Point::getY).thenComparingInt(Point::getX));
            overlapSize = seeds.size();
            overlaps = seeds;

        // 일부 겹침
        } else if (K < spaceWithSeedCount && spaceWithSeedCount < totalSeedCount) {
            overlapSize = totalSeedCount - spaceWithSeedCount;

            // 같은 행에서 모두 존재. 일부가 겹치는 경우
            if (yCountMap.size() == 1) {
                int y = yCountMap.keySet().iterator().next();
                List<Integer> xs = xCountMap.keySet().stream()
                        .sorted(comparingInt(Integer::intValue))
                        .collect(Collectors.toList());

                int overLapStart = K - overlapSize;
                for (int i = overLapStart; i < overLapStart + overlapSize; i++) {
                    overlaps.add(new Point(y, xs.get(i)));
                }
            }

            // 같은 열에서 모두 존재. 일부가 겹치는 경우
            else if (xCountMap.size() == 1) {
                int x = xCountMap.keySet().iterator().next();
                List<Integer> ys = yCountMap.keySet().stream()
                        .sorted(comparingInt(Integer::intValue))
                        .collect(Collectors.toList());

                int overLapStart = K - overlapSize;
                for (int i = overLapStart; i < overLapStart + overlapSize; i++) {
                    overlaps.add(new Point(ys.get(i), x));
                }
            }

            // 그 외는 크로스로 겹치는 경우 뿐이다.
            else {
                int y = 0;
                int x = 0;
                for (Map.Entry<Integer, Integer> xe : xCountMap.entrySet()) {
                    if (xe.getValue() > 1) {
                        x = xe.getKey();
                        break;
                    }
                }

                for (Map.Entry<Integer, Integer> ye : yCountMap.entrySet()) {
                    if (ye.getValue() > 1) {
                        y = ye.getKey();
                        break;
                    }
                }

                overlaps.add(new Point(y, x));
            }

        // 안 겹침
        } else if (totalSeedCount <= spaceWithSeedCount) {
            sb.append(0);
            System.out.print(sb.toString().trim());
            System.exit(0);
        }

        sb.append(overlaps.size()).append(System.lineSeparator());
        overlaps.forEach((seed) -> sb.append(seed.y).append(SPACE).append(seed.x).append(System.lineSeparator()));

        System.out.print(sb.toString().trim());
    }

    static class Point {
        int y;
        int x;

        public Point(int y, int x) {
            this.y = y;
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public int getX() {
            return x;
        }
    }
}
/**
 * 400만개 -> 16MB
 * 0 애초에 좌표를 처음 읽을떄 위치를 기억하는 게 좋을듯
 *
 * ### 풀이
 * 1) 씨앗수가 K*2 : 안만남
 * - 인접 평행
 * - 떨어져있거나 떨어져서 평행
 * => 0 출력
 *
 * --> 겹침에 해당하는 경우만 구하면 된다.
 *
 * 2) 씨앗수가 K <  < K * 2 : 겹침
 * - 2-1) 크로스로 겹침
 * - 2-2) 같은 라인에서 겹침 --> set으로 좌표값을 종합시, 같은 라인에 있는 경우를 알 수 있다.
 * --> 
 *
 * 3) 씨앗수가 K : 완전 겹침
 * - 완전 같은 위치
 * --> 모든 씨앗 좌표 출력
 *
 *  ### 예외?
 *  K == 1
 * - 겹치는 경우 완전겹침에만 해당, 위 로직으로 잘 처리됨
 *
 * case2) K >= 2
 * - 가능
 *
 * ### 과정
 *
 * 1 초기에 씨앗 위치 좌표기억, 각각 y,x값 Set에 집어넣기
 * 2 초기에 씨앗 전체 개수 세기. 개수에 대해 케이스 분별
 * 3 겹치지 않는 경우 바로 종료, 씨앗 개수 = K이면 완전 겹침으로 바로 종료
 * 4 둘다 아닌 경우 countMap을 이용해 크로스인지 한 라인인지 분별
 * - 크로스라면 겹치는 한개 부분 찾기
 * - 한 라인이라면 겹치는 여러 부분 찾고 정렬하기
 */