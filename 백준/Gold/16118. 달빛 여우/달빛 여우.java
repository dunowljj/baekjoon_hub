import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

import static java.util.Comparator.*;

public class Main {

    public static final int RUN = 0;
    public static final int WALK = 1;
    static List<Node>[] adj;
    static int[] minDistOfFox;
    static int[][] minDistOfWolf;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        adj = new ArrayList[N + 1];
        for (int i = 0; i < adj.length; i++) {
            adj[i] = new ArrayList<>();
        }

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());

            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken());

            // 정수 계산 편의를 위해 거리 * 2 -> 4천개 * 10만 * 2 -> 16억
            adj[a].add(new Node(b, d * 2));
            adj[b].add(new Node(a, d * 2));
        }

        // 여우 다익스트라
        minDistOfFox = new int[N + 1];
        Arrays.fill(minDistOfFox, Integer.MAX_VALUE);
        minDistOfFox[1] = 0;

        Queue<Node> queue = new PriorityQueue<>();
        queue.offer(new Node(1, 0));
        while (!queue.isEmpty()) {
            Node now = queue.poll();

            if (minDistOfFox[now.no] < now.distance) continue;

            for (Node next : adj[now.no]) {

                if (minDistOfFox[next.no] > minDistOfFox[now.no] + next.distance) {
                    minDistOfFox[next.no] = minDistOfFox[now.no] + next.distance;
                    queue.offer(new Node(next.no, minDistOfFox[next.no]));
                }
            }
        }

//        for (int i = 1; i < minDistOfFox.length; i++) {
//            System.out.print(minDistOfFox[i] + " ");
//        }
//        System.out.println();

        // 늑대 다익스트라
        minDistOfWolf = new int[N + 1][2]; // 늑대의 최소거리
        for (int i = 1; i < minDistOfWolf.length; i++) {
            Arrays.fill(minDistOfWolf[i], Integer.MAX_VALUE);
        }
        minDistOfWolf[1][WALK] = 0;

        queue.clear();
        queue.offer(new Node(1, 0, true));
        while (!queue.isEmpty()) {
            Node now = queue.poll();

            int runStatus = now.timeToRun ? WALK : RUN; // 뛸차례면 직전에 걸어온 것, 걸을차례면 뛰어온 것
            if (minDistOfWolf[now.no][runStatus] < now.distance) continue;

//            System.out.println("[now.no = " + now.no+"]");

            for (Node next : adj[now.no]) {
                if (now.timeToRun) {
                    if (minDistOfWolf[next.no][RUN] > minDistOfWolf[now.no][WALK] + next.distance / 2) {
//                        System.out.println("--run to" + next.no);
                        minDistOfWolf[next.no][RUN] = minDistOfWolf[now.no][WALK] + next.distance / 2;
//                        System.out.println("----minDistOfWolf[next.no][RUN] = " + minDistOfWolf[next.no][RUN]);

                        queue.offer(new Node(next.no, minDistOfWolf[next.no][RUN], false));
                    }
                } else {
                    if (minDistOfWolf[next.no][WALK] > minDistOfWolf[now.no][RUN] + next.distance * 2) {
//                        System.out.println("--walk to" + next.no);
                        minDistOfWolf[next.no][WALK] = minDistOfWolf[now.no][RUN] + next.distance * 2;
//                        System.out.println("----minDistOfWolf[next.no][WALK] = " + minDistOfWolf[next.no][WALK]);
                        queue.offer(new Node(next.no, minDistOfWolf[next.no][WALK], true));
                    }
                }
            }
        }

//        System.out.println("wolf 0");
//        for (int i = 1; i < minDistOfWolf.length; i++) {
//            System.out.print(minDistOfWolf[i][0]+" ");
//        }
//        System.out.println();
//
//        System.out.println("wolf 1");
//        for (int i = 1; i < minDistOfWolf.length; i++) {
//            System.out.print(minDistOfWolf[i][1]+ " ");
//        }
//        System.out.println();

        int answer = 0;
        for (int i = 1; i <= N; i++) {
            int minOfWolf = Math.min(minDistOfWolf[i][0], minDistOfWolf[i][1]);
//            System.out.print(minOfWolf + " ");
            if (minOfWolf > minDistOfFox[i]) {
                answer++;
            }
        }
//        System.out.println();

        System.out.print(answer);
    }

    static class Node implements Comparable<Node> {
        int no;
        int distance;
        boolean timeToRun;

        public Node(int no, int distance) {
            this.no = no;
            this.distance = distance;
        }

        public Node(int no, int distance, boolean timeToRun) {
            this.no = no;
            this.distance = distance;
            this.timeToRun = timeToRun;
        }

        public int getDistance() {
            return distance;
        }

        @Override
        public int compareTo(Node node) {

            if (distance == node.distance) {
                // 거리가 같다면 이번에 뛸차례인 것부터
                if (timeToRun) return -1;
                else return 1;
            }

            return distance - node.distance;
        }
    }
}
/**
 * 다익스트라를 하되, 늑대는 도착한 속도에 따른 거리체크가 필요할듯 하다.
 * 늑대는 출발할때 2배속도, 그다음 0.5배 반복
 * - 다익스트라는 가장 짧은 거리를 우선으로 찾아간다.
 * - 하지만 2배속도일때 가장 큰거리를 가는게 이득일수도 있고, 작은거리를 가는게 이득일 수도 있다.
 * - 이 문제에서는 긴곳을 빠르게 달리기 위해 일부러 재방문하고 돌아가는 경우를 고려하지 않는 것 같다.

5 6
1 2 4
2 3 5
3 4 5
1 4 5
2 4 5
4 5 1000

5 4
1 2 5
2 3 5
1 3 5
3 4 1000

5 4
1 2 5
2 3 7
1 4 3
4 5 1000

 */