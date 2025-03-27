import java.util.*;

class Solution {
    
    int answer = Integer.MAX_VALUE;
    
    public int solution(int n, int m, int[][] timetable) {
        
        if (m <= 1) return 0;
        
        int[] sum = new int[1321];
        for (int[] time : timetable) {
            sum[time[0]]++;
            sum[time[1] + 1]--;
        }
        
        int maxVisit = 0; // 최대 방문이 겹치는 경우
        for (int i = 1; i < sum.length; i++) {
            sum[i] += sum[i - 1];
            maxVisit = Math.max(maxVisit, sum[i - 1]);
        }
        
        // 이용이 안겹침
        if (maxVisit < 2) return 0;
            
        
        
        int lo = 0;
        int hi = 2 * (n - 1) + 1;
        
        // T T T T T (F)
        // T T T F F F
        // 마지막 T 찾기
        while (lo + 1 < hi) {
            int mid = (lo + hi) / 2;
            
            if (isEnable(mid, maxVisit, n)) {
                lo = mid;
            } else {
                hi = mid;
            }
        }
        
        return lo;
    }
    
    public boolean isEnable(int distance, int maxVisit, int n) {
        List<Point> guests = new ArrayList<>();
        int count = 0;
        
        for (int sx = 0; sx < n; sx++) {
            guests.clear();
            count = 1;
            guests.add(new Point(0, sx));

            for (int y = 0; y < n; y++) {
                for (int x = 0; x < n; x++) {

                    boolean canUse = true;  
                    for (Point guest : guests) {

                        if (guest.distTo(y,x) < distance) {
                            canUse = false;
                            break;
                        }
                    }

                    if (canUse) {
                        guests.add(new Point(y,x));
                        count++;

                        if (count == maxVisit) return true;
                    }
                }
            }
        }
        
        return false;
    }
    
    
    class Point {
        int y;
        int x;
        
        Point(int y, int x) {
            this.y = y;
            this.x = x;
        }
        
        public int distTo(int y, int x) {
            return Math.abs(this.y - y) + Math.abs(this.x - x);
        }
    }
}
/**
거리별로 모든 경우 탐색하기
*/