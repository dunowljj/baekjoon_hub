import java.util.*;

import static java.util.Comparator.*;

class Solution {

    static int[] parent;

    public int solution(int n, int[][] costs) {
        Arrays.sort(costs, comparingInt((cost) -> cost[2]));

        parent = new int[n];
        for (int i = 0; i < parent.length; i++) {
            parent[i] = i;
        }

        int totalCost = 0;
        for (int[] cost : costs) {
            int start = cost[0];
            int end = cost[1];
            int buildCost = cost[2];

            if (alreadyConnected(start, end)) continue;

            union(start, end);
            totalCost += buildCost;
        }

        return totalCost;
    }

    private boolean alreadyConnected(int start, int end) {
        return find(start) == find(end);
    }

    public void union(int n1, int n2) {
        int p1 = find(n1);
        int p2 = find(n2);

        if (p1 < p2) {
            parent[p2] = p1;
        } else {
            parent[p1] = p2;
        }
    }

    private int find(int no) {
        if (parent[no] == no) {
            return no;
        } else {
            return find(parent[no]);
        }
    }
}
/**
 * 우선적으로 가격이 싼 다리부터 건설
 */