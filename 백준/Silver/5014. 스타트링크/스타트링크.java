import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int maxFloor = Integer.parseInt(st.nextToken());
        int now = Integer.parseInt(st.nextToken());
        int goal = Integer.parseInt(st.nextToken());
        int upStep = Integer.parseInt(st.nextToken());
        int downStep = Integer.parseInt(st.nextToken());

        boolean[] visited = new boolean[maxFloor + 1];

        Queue<Integer> queue = new LinkedList<>();
        queue.offer(now);

        int click = 0;
        while (!queue.isEmpty()) {

            int size = queue.size();

            for (int i = 0; i < size; i++) {
                int loc = queue.poll();

                if (visited[loc]) continue;
                visited[loc] = true;

                if (loc == goal) {
                    System.out.print(click);
                    return;
                }

                if (loc + upStep <= maxFloor) queue.offer(loc + upStep);
                if (loc - downStep >= 1) queue.offer(loc - downStep);

            }
            click++;
        }

        System.out.print("use the stairs");
    }
}
