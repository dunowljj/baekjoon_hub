import java.io.*;
import java.util.StringTokenizer;

public class Main {
    static char[][] start;
    static char[][] dest;
    static int count;
    static int N;
    static int M;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());


        start = new char[N][M];
        for (int i = 0; i < N; i++) {
            String input = br.readLine();
            for (int j = 0; j < M; j++) {
                start[i][j] = input.charAt(j);
            }
        }

        dest = new char[N][M];
        for (int i = 0; i < N; i++) {
            String input = br.readLine();
            for (int j = 0; j < M; j++) {
                dest[i][j] = input.charAt(j);
            }
        }

        changeMatrix();
        String answer = isSame(start, dest) ? count + "" : "-1";


        bw.write(answer);
        bw.flush();
        bw.close();
    }

    private static boolean isSame(char[][] start, char[][] dest) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (start[i][j] != dest[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    private static void changeMatrix() {
        for (int i = 0; i < N - 2; i++) {
            for (int j = 0; j < M - 2; j++) {
                if (start[i][j] != dest[i][j]) {
                    changeSubMatrix(i,j);
                    count++;
                }
            }
        }
    }
    private static void changeSubMatrix(int i, int j) {
        for (int k = i; k < i + 3; k++) {
            for (int l = j; l < j + 3; l++) {
                if (start[k][l] == '0') {
                    start[k][l] = '1';
                } else {
                    start[k][l] = '0';
                }
            }
        }
    }
}
/*
N,M <= 50 자연수

3*3의 부분 행렬을 통째로 뒤집어야함. 탐색이 아닌 그리디?
맨 왼쪽위의 요소를 기준으로 값이 다르면 부분행렬을 뒤집는다. 중복되지 않게 뒤집는게 관건
 */