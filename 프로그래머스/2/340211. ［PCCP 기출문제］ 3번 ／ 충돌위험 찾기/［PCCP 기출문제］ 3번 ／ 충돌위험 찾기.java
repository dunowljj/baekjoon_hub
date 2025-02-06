import java.util.*;

class Solution {

    // 최단 경로가 여러가지면 r보다 c가 변하는 곳 우선
    private static int n,m;

    private static int answer = 0;

    public int solution(int[][] points, int[][] routes) {
        Queue<Robot> queue = new LinkedList<>();

        for (int[] route : routes) {
            Robot robot = new Robot(route, points);
            queue.offer(robot);
        }

        while (!queue.isEmpty()) {
            int turnSize = queue.size();

            Set<Point> alreadyChecked = new HashSet<>();
            Set<Point> turnConflict = new HashSet<>();
            for (int i = 0; i < turnSize; i++) {
                Robot robot = queue.poll();
                
                Point now = robot.now;
                Point key = new Point(now);

                if (turnConflict.contains(key) && !alreadyChecked.contains(key)) {
                    // System.out.println("r:"+ key.r+",c:"+key.c);
                    alreadyChecked.add(key);
                    answer++;
                }
                turnConflict.add(key);

                robot.move();
                
                if (!robot.isArrived) queue.offer(robot);
            }
        }
        return answer;
    }

    static class Robot {

        Point now;
        List<Point> ends;
        int targetIdx = 0;
        boolean isArrived = false;

        public Robot(int[] route, int[][] points) {
            this.now = new Point(points[route[0] - 1]);

            ends = new ArrayList<>();
            for (int i = 1; i < route.length; i++) {
                ends.add(new Point(points[route[i] - 1]));
            }
        }

        public void move() {
            Point end = ends.get(targetIdx);
            
            if (now.equals(end)) {                
                if (targetIdx == ends.size() - 1) {
                    isArrived = true;
                    return;
                }
                
                if (targetIdx < ends.size() - 1) {
                    targetIdx++;
                    end = ends.get(targetIdx);
                }
            }
            
            if (now.r > end.r) {
                now.r -= 1;
                return;
            }

            if (now.r < end.r) {
                now.r += 1;
                return;
            }

            if (now.c > end.c) {
                now.c -= 1;
                return;
            }

            if (now.c < end.c) {
                now.c += 1;
                return;
            }
            
            if (targetIdx == ends.size() - 1) {
                isArrived = true;
                return;
            }
        }
    }

    static class Point {
        int r;
        int c;

        public Point(int r, int c) {
            this.r = r;
            this.c = c;
        }

        public Point(Point p) {
            this.r = p.r;
            this.c = p.c;
        }

        public Point(int[] point) {
            this(point[0] - 1, point[1] - 1);
        }

        @Override
        public boolean equals(Object o) {
            return this.r == ((Point) o).r && this.c == ((Point) o).c;
        }

        @Override
        public int hashCode() {
            return (this.r * 1000) + this.c;
        }
    }
}