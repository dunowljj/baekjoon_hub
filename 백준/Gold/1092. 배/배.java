import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

import static java.util.Comparator.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int N = Integer.parseInt(br.readLine());

        // 크레인 운반가능 무게
        st = new StringTokenizer(br.readLine());
        List<Integer> cranes = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            cranes.add(Integer.parseInt(st.nextToken()));
        }

        int M = Integer.parseInt(br.readLine());

        List<Integer> boxes = new ArrayList<>();
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < M; i++) {
            boxes.add(Integer.parseInt(st.nextToken()));
        }

        Collections.sort(cranes, comparingInt(Integer::intValue).reversed());
        Collections.sort(boxes, comparingInt(Integer::intValue).reversed());

        if (cranes.get(0) < boxes.get(0)) {
            System.out.println(-1);
            return;
        }

        int time = 0;
        while(!boxes.isEmpty()){
            int boxIdx = 0;
            int craneIdx = 0;

            while (craneIdx < N) {
                if (boxIdx == boxes.size())
                    break;
                else if (cranes.get(craneIdx) >= boxes.get(boxIdx)) {
                    boxes.remove(boxIdx);
                    craneIdx++;
                } else
                    boxIdx++;
            }

            time++;
        }

        System.out.print(time);
    }

    static class Box {
        int weight;
        boolean lifted;

        public Box(int weight, boolean lifted) {
            this.weight = weight;
            this.lifted = lifted;
        }

        public int getWeight() {
            return weight;
        }
    }
}
