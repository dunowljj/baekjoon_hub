import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.StringTokenizer;

public class Main {

    public static final String SPACE = " ";

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int N = Integer.parseInt(br.readLine());

        int[] heights = new int[N + 1];
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= N; i++) {
            heights[i] = Integer.parseInt(st.nextToken());
        }

        int[] receivers = new int[N + 1];
        Stack<Building> senders = new Stack<>();
        for (int no = N; no >= 1; no--) {

            while (!senders.isEmpty() && heights[no] >= senders.peek().height) {
                Building sender = senders.pop();
                receivers[sender.no] = no; // sender 탑이 발사한 레이저 신호를 수신한 탑의 번호를 저장
            }

            senders.push(new Building(heights[no], no));
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= N; i++) {
            sb.append(receivers[i]).append(SPACE);
        }

        System.out.print(sb.toString().trim());
    }

    static class Building {
        int height;
        int no;

        public Building(int height, int no) {
            this.height = height;
            this.no = no;
        }
    }
}

/**
 * 1) 배열에 초기화
 * 2) 뒤부터 순회, 스택에 담기
 * 3) 순회하다 더 큰 탑이 나오면 스택에서 해당 탑보다 큰 요소 나올때까지 제거 및 갱신
 * - 수신탑 없으면 0 출력
 */