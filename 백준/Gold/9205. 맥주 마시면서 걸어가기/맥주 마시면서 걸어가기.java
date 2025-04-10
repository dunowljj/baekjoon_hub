import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        StringBuilder sb = new StringBuilder();

        int t = Integer.parseInt(br.readLine());
        for (int i = 0; i < t; i++) {
            String answer = "sad";

            int n = Integer.parseInt(br.readLine());

            List<Point> points = new ArrayList<>();

            st = new StringTokenizer(br.readLine());
            int homeY = Integer.parseInt(st.nextToken());
            int homeX = Integer.parseInt(st.nextToken());
            Point home = new Point(homeY, homeX);

            for (int j = 0; j < n; j++) {
                st = new StringTokenizer(br.readLine());
                int storeY = Integer.parseInt(st.nextToken());
                int storeX = Integer.parseInt(st.nextToken());
                Point store = new Point(storeY, storeX);
                points.add(store);
            }

            st = new StringTokenizer(br.readLine());
            int destY = Integer.parseInt(st.nextToken());
            int destX = Integer.parseInt(st.nextToken());
            Point dest = new Point(destY, destX);
            points.add(dest);

            Queue<Point> queue = new LinkedList<>();

            queue.offer(home);
            boolean[] visited = new boolean[points.size()];

            while (!queue.isEmpty()) {
                Point now = queue.poll();

                if (now.equals(dest)) {
                    answer = "happy";
                    break;
                }

//                System.out.println("now.y = " + now.y);
//                System.out.println("now.x = " + now.x);

                for (int j = 0; j < points.size(); j++) {
                    Point next = points.get(j);
                    if (next.equals(now)) continue;
                    if (now.distanceTo(next) > 1000) continue;
                    if (visited[j]) continue;

                    visited[j] = true;
                    queue.offer(next);
                }
            }

            sb.append(answer).append("\n");
        }

        System.out.print(sb.toString().trim());
    }

    static class Point {
        int y;
        int x;

        public Point(int y, int x) {
            this.y = y;
            this.x = x;
        }

        public int distanceTo(Point point) {
            return Math.abs(this.y - point.y) + Math.abs(this.x - point.x);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            Point point = ((Point) o);

            if (y != point.y) return false;
            return x == point.x;
        }

        @Override
        public int hashCode() {
            int result = y;
            result = 31 * result + x;
            return result;
        }
    }
}