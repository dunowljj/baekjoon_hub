import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

import static java.util.Comparator.*;

public class Main {

    static class Info {
        int no;
        int cost;
        int passTime = 0;

        public Info(int no, int cost) {
            this.no = no;
            this.cost = cost;
        }

        public Info(int no, int cost, int passTime) {
            this.no = no;
            this.cost = cost;
            this.passTime = passTime;
        }

        public int updatePassTime(int time) {
            return this.passTime = time;
        }

        public int getCost() {
            return cost;
        }
    }

    static Map<Integer, Info>[] graph;
    static List<Integer> kingVisits;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        graph = new HashMap[N + 1];
        for (int i = 1; i < N + 1; i++) graph[i] = new HashMap();


        st = new StringTokenizer(br.readLine());
        int start = Integer.parseInt(st.nextToken());
        int end = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken()); //고둘라 출발 시간과 상근이 출발 시간 차이
        int G = Integer.parseInt(st.nextToken()); //고둘라 방문 교차로


        st = new StringTokenizer(br.readLine());
        kingVisits = new ArrayList<>();
        for (int i = 0; i < G; i++) {
            int g = Integer.parseInt(st.nextToken());
            kingVisits.add(g);
        }

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int U = Integer.parseInt(st.nextToken());
            int V = Integer.parseInt(st.nextToken());
            int L = Integer.parseInt(st.nextToken());

            graph[U].put(V, new Info(V, L, 0));
            graph[V].put(U, new Info(U, L, 0));
        }


        /**
         * 고둘라가 통과를 완료한 시간을 써놓는다.
         * 차량 통제 시간 x의 범위는 다음과 같다. passTime - cost <= x < passTime
         */
        int before = -K;
        for (int i = 0; i < kingVisits.size() - 1; i++) {
            int goStart = kingVisits.get(i);
            int goEnd = kingVisits.get(i + 1);
            int time = 0;

            Info info1 = graph[goStart].get(goEnd);
            time = info1.updatePassTime(before + info1.cost);

            Info info2 = graph[goEnd].get(goStart);
            time = info2.updatePassTime(before + info2.cost);

            before = time;
        }

        PriorityQueue<Info> pq = new PriorityQueue<>(comparingInt(Info::getCost));
        int d[] = new int[N + 1];
        boolean[] visited = new boolean[N + 1];

        Arrays.fill(d, Integer.MAX_VALUE);
        d[start] = 0;

//        visited[start] = true;
        pq.offer(new Info(start, 0));

        while (!pq.isEmpty()) {

            Info now = pq.poll();
            for (Info next : graph[now.no].values()) {

//                if (visited[next.no]) continue;
//                visited[next.no] = true;
//                printMoveInfo(now, next);

                // 통제시간에 걸린다면 현재 시간에 대기시간 추가
                if (next.passTime - next.cost <= now.cost && now.cost < next.passTime) {

                    int delayed = next.passTime - now.cost;
                    next.cost += delayed;
//                            next.passTime - 1 + next.cost;
//                    System.out.println("delayed = " + delayed);
//                    printDelayInfo(next.passTime, now, next);
                }


                if (d[next.no] > d[now.no] + next.cost) {
                    d[next.no] = d[now.no] + next.cost;
//                    System.out.println("offer:move cost : " + next.cost);
//                    System.out.println("offer:next cost : " + d[next.no]);

                    pq.offer(new Info(next.no, d[next.no]));
                }

//                System.out.println();

            }
        }

        System.out.println(d[end]);
    }

    private static void printDelayInfo(int passTime, Info now, Info next) {
        System.out.println("-------delay log --------");
        System.out.println("delay:now.no = " + now.no  + "-----> " + "delay:next.no = " + next.no);
        System.out.println("passTime = " + passTime);
        System.out.println("startTime = " + next.cost);
        System.out.println("-------delay log --------");
    }

    private static void printMoveInfo(Info now, Info next) {
        System.out.println("[now.no] = " + now.no  + " -----> " + "[next.no] = " + next.no);
        System.out.println("now.no = " + now.no);
        System.out.println("now.cost (현재 시간) = " + now.cost);
        System.out.println("next.no = " + next.no);
        System.out.println("next.cost : " + next.cost);
        System.out.println("cannot pass range : "+ (next.passTime - next.cost) +" <= t < " + next.passTime);
    }
}
/**
 * 같은곳에서 차이 없이 시작한다면?
 */
