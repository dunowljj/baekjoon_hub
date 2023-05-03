import java.util.*;

class Solution {
    public int solution(int bridge_length, int weight, int[] truck_weights) {
        Queue<Integer> bridge = new LinkedList<>();
        
        int time = bridge_length;
        int onWeight = truck_weights[0];        
        bridge.offer(truck_weights[0]);
        time ++;
        
        for (int i = 1; i < truck_weights.length; i++) {
            int truck_weight = truck_weights[i];
            
            if (weight >= onWeight + truck_weight) {
                bridge.offer(truck_weight);
                onWeight += truck_weight;
            } else {
                bridge.offer(0);
                i--;
            }
            
            if (bridge.size() == bridge_length) {
                onWeight -= bridge.poll();
            }
            
            time ++;
        }
        
        return time;
    }
}
