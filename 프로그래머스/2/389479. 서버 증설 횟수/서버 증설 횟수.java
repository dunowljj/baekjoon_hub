import java.util.*;

class Solution {
    public int solution(int[] players, int m, int k) {
        int answer = 0;
        
        int[] servers = new int[24];
        
        for (int i = 0; i < 24; i++) {
            int connect = players[i];
            int nowServer = servers[i];
            int needServer = (connect / m);
            
            if (nowServer < needServer) {
                int added = needServer - nowServer;
                
                answer += added;
                for (int j = i; j < i + k && j < 24; j++) servers[j] += added;
            }
        }
        
        return answer;
    }
}