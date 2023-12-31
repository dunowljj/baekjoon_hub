class Solution {

    int maxCount = 0;
    boolean[] visited;

    public int solution(int k, int[][] dungeons) {
        visited = new boolean[dungeons.length];
        dfs(0, k, dungeons);

        return maxCount;
    }

    private void dfs(int depth, int k, int[][] dungeons) {
        maxCount = Math.max(depth, maxCount);

        for (int i = 0; i < dungeons.length; i++) {
            int needToEnter = dungeons[i][0];
            int cost = dungeons[i][1];

            if (!visited[i] && k >= needToEnter) {
                visited[i] = true;
                dfs(depth + 1, k - cost, dungeons);
                visited[i] = false;
            }
        }
    }
}