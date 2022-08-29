import java.io.*;
import java.util.StringTokenizer;

public class Main {
    static int[][] start;
    static int[][] dest;
    static int count;
    static int N;
    static int M;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());


        start = new int[N][M];
        for (int i = 0; i < N; i++) {
            String input = br.readLine();
            for (int j = 0; j < M; j++) {
                start[i][j] = input.charAt(j) - '0';
            }
        }

        dest = new int[N][M];
        for (int i = 0; i < N; i++) {
            String input = br.readLine();
            for (int j = 0; j < M; j++) {
                dest[i][j] = input.charAt(j) - '0';
            }
        }

        changeMatrix();
        String answer = isSame(start, dest) ? count + "" : "-1";


        bw.write(answer);
        bw.flush();
        bw.close();
    }

    private static boolean isSame(int[][] start, int[][] dest) {
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
                start[k][l] ^= 1;
            }
        }
    }
}
/*
N,M <= 50 자연수

3*3의 부분 행렬을 통째로 뒤집어야함. 탐색이 아닌 그리디?

다른코드 : 모든 인덱스를 탐색하면서, 부분수열을 바꿀수 없는 부분까지 탐색한다. 인덱스가 넘어감을 따로 처리해준다.
바꾸려는 해당요소가 다른데 바꿀수 없는 경우 바로 종료하는 조건을 추가한다.

int가 빠른가? 싶어서 토글연산을 넣어서 만들어봄
 */