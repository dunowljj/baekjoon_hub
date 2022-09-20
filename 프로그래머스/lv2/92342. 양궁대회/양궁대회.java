
class Solution {
    int max = Integer.MIN_VALUE;
    int[] answer = new int[11];
    boolean[] visited = new boolean[11];
    
    public int[] solution(int n, int[] info) {
        int[] result = new int[11];
        
        dfs(0, n, info, answer, 10);
        
        // (라이언점수 - 어파치점수)의 최댓값이 0보다 작거나 같으면 어파치 승리
        if (max <= 0) {
            return new int[]{-1};
        } else {
            return answer;        
        }
    }
    
    private void dfs(int depth, int n, int[] info, int[] result, int idx) {
        if (depth == n) {
            int score = getScoreDiff(info, result);
            if (max < score) {
                max = score;
                answer = result.clone();
            }
            return;
        }

        for (int i = idx; i >= 0; i--) {
                if (info[i] < result[i]) continue;
                
                result[i]++;
            
                if (info[i] >= result[i]) {
                    dfs(depth + 1, n, info, result, i);        
                }   
                else {
                    dfs(depth + 1, n, info, result, i - 1);
                }
                
                result[i]--;
        }
    }
    
    private int getScoreDiff(int[] ach, int[] lion) {
        int achScore = 0;
        int lionScore = 0;
        
        for (int i = 0; i < ach.length; i++) {
            if (ach[i] == 0 && lion[i] == 0) continue; //둘다 0인 경우는 점수 획득이 없으므로 계산하지 않는다.
            
            if (ach[i] >= lion[i]) {
                achScore += (10 - i);
            } else {
                lionScore += (10 - i);
            }
        }
        
        return lionScore - achScore;
    }
}
/*
화살을 쏠수있는 총 경우의 수 최대 = 10^11
완전탐색 후 최댓값 구하기
지는 경우 뿐이라면 길이 1 [-1] return

해가 여러가지 -> 가장 낮은 점수를 더 많이 맞힌 경우를 return 뒤부터 탐색 -> 뒤부터 탐색해서 조건없이 탐색해보기
뒤부터 완전탐색을 하니 당연히 시간초과가 난다.

해결 : 화살을 아파치의 화살 개수를 넘을때까지만 과녁을 맞추도록 하기. 화살의 개수는 정해져있으므로, 최대 점수를 구하고도 화살이 남을 일은 없다.

1~10까지 방문처리를 한후, 화살 개수가 넘어가면 탐색 멈춤. 점수가 제일 작은곳부터 화살 쏘기
*/
