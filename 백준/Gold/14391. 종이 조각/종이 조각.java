import java.io.*;
import java.util.StringTokenizer;

public class Main {
    static int N;
    static int M;
    static int[][] numbers;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        numbers = new int[N][M];
        for (int i = 0; i < N; i++) {
            String input = br.readLine();
            for (int j = 0; j < M; j++) {
                numbers[i][j] = input.charAt(j) - '0';
            }
        }
        // 0 가로 1 세로
        int max = 0;
        // 0000 ~ 1111 까지 전체를 하나씩 확인
        for (int i = 0; i < (1 << N * M); i++) {
            int sum = 0;

            // 행 확인
            int x = 0;
            for (int j = 0; j < N; j++) {
                int curr = 0;
                for (int k = 0; k < M; k++) {
                    x = j*M+k;
                    if ((i & 1 << x) == 0) {
                        curr *= 10;
                        curr += numbers[j][k];
                    } else {
                        sum += curr;
                        curr = 0;
                    }
                }
                sum += curr;
            }
            
            // 열 확인
            for (int j = 0; j < M; j++) {
                int curr = 0;
                for (int k = 0; k < N; k++) {
                    x = k*M+j;
                    if ((i & 1 << x) != 0) {
                        curr *= 10;
                        curr += numbers[k][j];
                    } else {
                        sum += curr;
                        curr = 0;
                    }
                }
                sum += curr;
            }
            max = Math.max(max, sum);
        }

        bw.write(max+"");
        bw.flush();
        bw.close();
    }

}
/*
직사각형 종이
1*1 크기 정사각형 칸 당 숫자 하나
세로 혹은 가로가 1인 직사각형
자르면 이어붙인 수로 침
적절히 잘라서 조각의 합을 최대로

길이 1~4

먼저 비트마스크 혹은 배열로 사용 여부 체크
사용하지 않았다면 해당범위까지 자르는 모든 경우의 수 탐색 -> 행 방향, 열 방향 모두 탐색해야함

 */