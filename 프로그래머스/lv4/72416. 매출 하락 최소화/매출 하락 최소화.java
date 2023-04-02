import java.util.*;

class Solution {
    class Node {
        Node leader;
        List<Node> members;
        int sale;
        int index;

        Node (int sale, int index) {
            this.sale = sale;
            this.index = index;
            this.members = new ArrayList<>();
        }

        public void setLeader(Node leader) {
            this.leader = leader;
        }

        public void addMember(Node teamMate) {
            this.members.add(teamMate);
        }

        public boolean isMember() {
            return members.isEmpty();
        }
    }

    public int solution(int[] sales, int[][] links) {
        // sales -> 1번부터 주어짐 인덱스 주의
        int n = sales.length + 1;

        Node[] nodes = new Node[n];
        int[][] dp = new int[n][2]; // 0 불참, 1 참여
        for (int i = 1; i < n; i++) nodes[i] = new Node(sales[i - 1], i);

        for (int[] link : links) {
            Node leader = nodes[link[0]];
            Node member = nodes[link[1]];

            leader.addMember(member);
            member.setLeader(leader);
        }

        find(nodes[1], dp, sales);
        
        return Math.min(dp[1][0], dp[1][1]);
    }

    public void find(Node now, int[][] dp, int[] sales) {
        dp[now.index][1] = now.sale; // 참

        if (now.isMember()) return;
            
        int extra = Integer.MAX_VALUE;

        for (Node child : now.members) {
            find(child, dp, sales);

            int chIdx = child.index;

            // 해당 노드가 불참하면 더 적은 경우
            if (dp[child.index][0] < dp[child.index][1]) {
                dp[now.index][0] += dp[child.index][0];
                dp[now.index][1] += dp[child.index][0];
                extra = Math.min(extra, dp[child.index][1] - dp[child.index][0]);
            } else {
                dp[now.index][0] += dp[child.index][1];
                dp[now.index][1] += dp[child.index][1];
                extra = 0;
            }

        }
        
        dp[now.index][0] += extra;
    }
}