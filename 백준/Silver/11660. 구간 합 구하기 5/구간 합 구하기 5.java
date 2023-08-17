import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    public static final String LINE_BREAK = "\n";

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        int[][] sumGrid = new int[N + 1][N + 1];
        for (int i = 1; i < N + 1; i++) {
            st = new StringTokenizer(br.readLine());

            for (int j = 1; j < N + 1; j++) {
                sumGrid[i][j] = sumGrid[i][j - 1] + Integer.parseInt(st.nextToken());
            }
        }
/*
        for (int i = 0; i < N + 1; i++) {
            for (int j = 0; j < N + 1; j++) {
                System.out.print(sumGrid[i][j] + " ");
            }
            System.out.println();
        }*/

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < M; i++) {
            int sum = 0;
            st = new StringTokenizer(br.readLine());
            int x1 = Integer.parseInt(st.nextToken());
            int y1 = Integer.parseInt(st.nextToken());
            int x2 = Integer.parseInt(st.nextToken());
            int y2 = Integer.parseInt(st.nextToken());

            for (int x = x1; x <= x2; x++) {
                sum += sumGrid[x][y2] - sumGrid[x][y1 - 1];
            }

            sb.append(sum).append(LINE_BREAK);
        }

        System.out.print(sb);
    }
}
/**
 * 주어지는 정수는 자연수이다. 0이 아니다.
 */