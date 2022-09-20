
class Solution {
    int max = Integer.MIN_VALUE;
    int[] answer = new int[11];
    boolean[] visited = new boolean[11];
    public int[] solution(int n, int[] info) {
        int[] result = new int[11];
        
        // int apacheSum = 0;
        // for (int i = 0; i < info.length; i++) {
        //     if (info[i] != 0) apacheSum += 10 - i;
        // }
        // // 점수를 합해놓고 빼니깐, 겹치는 부분에는 점수를 *2로 빼야하는데 그게 안된다.
        
        dfs(0, n, info, answer, 10);
        
        // 차이의 최솟값이 0보다 크거나 같으면 어파치 승리
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
            if (ach[i] == 0 && lion[i] == 0) continue;
            
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

해가 여러가지 -> 가장 낮은 점수를 더 많이 맞힌 경우를 return? 아래부터 탐색 -> 화살이 남는데 점수를 더 얻을 수 없는경우는? -> 뒤부터 탐색해서 조건없이 탐색해보기

뒤부터 완전탐색을 하면, 시간초과가 난다. -> 근데 그냥 실패도 몇개 껴있다.
화살을 아파치의 화살 개수를 넘을때까지만 과녁을 맞추도록 하면 어떨까? 문제는 남는 화살의 처리이다.

1~10까지 방문처리를 한후, 화살 개수가 넘어가면 true 처리, 모두 true라면 제일 작은곳에 화살 쏘기 -> 애초에 모두 true이면 화살이 남지 않는다.


뭘해도 지면 길이 1 [-1] return


*/