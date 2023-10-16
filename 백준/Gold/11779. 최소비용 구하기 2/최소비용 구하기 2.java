import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

import static java.util.Comparator.*;

public class Main {
    static class City {
        int no;
        int fare;

        public City(int no, int fare) {
            this.no = no;
            this.fare = fare;
        }

        public int getFare() {
            return fare;
        }
    }

    static class Info {
        int minDistance;
        int cityCount;
        boolean visited;
        List<Integer> visitList;

        public Info(int minDistance, int cityCount, boolean visited, List<Integer> visitList) {
            this.minDistance = minDistance;
            this.cityCount = cityCount;
            this.visited = visited;
            this.visitList = visitList;
        }

        public void updateVisitList(List<Integer> nowVisitList, int nextNo) {
            List<Integer> newVisitList = new ArrayList<>();
            newVisitList.addAll(nowVisitList);
            newVisitList.add(nextNo);

            this.visitList = newVisitList;
        }
    }

    static List<City>[] adjList;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int n = Integer.parseInt(br.readLine());
        int m = Integer.parseInt(br.readLine());

        Info[] infos = new Info[n + 1]; // 최소거리, 도시의 개수, 방문 순서 저장
        for (int i = 1; i <= n; i++) {
            infos[i] = new Info(Integer.MAX_VALUE, 1, false, new ArrayList<>(List.of(i)));
        }

        adjList = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++) {
            adjList[i] = new ArrayList<>();
        }

        // 버스 비용 0~100,000
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int start = Integer.parseInt(st.nextToken());
            int end = Integer.parseInt(st.nextToken());
            int fare = Integer.parseInt(st.nextToken());

            if (start == end) continue;
            adjList[start].add(new City(end, fare));
        }

        st = new StringTokenizer(br.readLine());
        int start = Integer.parseInt(st.nextToken());
        int end = Integer.parseInt(st.nextToken());

        PriorityQueue<City> pq = new PriorityQueue<>(comparingInt(City::getFare));
        pq.offer(new City(start, 0));
        infos[start].minDistance = 0;

        while (!pq.isEmpty()) {
            City now = pq.poll();
            Info nowInfo = infos[now.no];
            nowInfo.visited = true;

            if (now.fare > infos[end].minDistance) break;

            for (City next : adjList[now.no]) {
                Info nextInfo = infos[next.no];

                if (!nextInfo.visited && nextInfo.minDistance > nowInfo.minDistance + next.fare) {
                    nextInfo.minDistance = nowInfo.minDistance + next.fare;
                    nextInfo.cityCount = nowInfo.cityCount + 1;
                    nextInfo.updateVisitList(nowInfo.visitList, next.no);

                    pq.offer(new City(next.no, nextInfo.minDistance));
                }
            }
        }

        System.out.println(infos[end].minDistance);
        System.out.println(infos[end].cityCount);
        for (int visit : infos[end].visitList) {
            System.out.print(visit + " ");
        }
    }
}

/**
 * 시작점과 끝점이 같은 경우를 따져야 한다? 그럴 필요가 있나? 어짜피 방문체크하는데
 */