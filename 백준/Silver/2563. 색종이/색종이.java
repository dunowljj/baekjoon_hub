import java.io.*;
import java.util.StringTokenizer;

public class Main {

    private static final int PAPER_LENGTH = 100;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int N = Integer.parseInt(br.readLine());

        int[][] paper = new int[PAPER_LENGTH + 1][PAPER_LENGTH + 1];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            int y = Integer.parseInt(st.nextToken());
            int x = Integer.parseInt(st.nextToken());

            count(paper, y, x);
        }

        int noColor = 0;
        for (int i = 0; i < paper.length; i++) {
            for (int j = 0; j < paper.length; j++) {
                if (paper[i][j] == 0) {
                    noColor++;
                }
            }
        }

        int answer = 101 * 101 - noColor;
        System.out.print(answer);
    }

    // 예시와 다르게 x축에 대칭한 그래프 모양으로 생성. 결과는 같음
    private static void count(int[][] paper, int y, int x) {
        for (int i = y; i < y + 10; i++) {
            for (int j = x; j < x + 10; j++) {
                paper[i][j]++;
            }
        }
    }
}

/**
 * 색종이
 * - 개수는 100개 이하
 * - 가로 세로 최대 100, 도화지를 넘는 경우가 없음
 * --> 카운팅으로 구현하면, 카운팅하는데 최대 10만(N*(10*10))번의 연산을 한다.
 * 주어지는 위치는 색종이 왼쪽아래 점의 y,x좌표이다.
 * 색종이는 10*10 크기이다.
 */