import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    private static boolean[][] isWall;
    private static int R,C;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());

        isWall = new boolean[R][C];
        for (int i = 0; i < R; i++) {
            String line = br.readLine();
            for (int j = 0; j < C; j++) {
                if (line.charAt(j) == 'x') isWall[i][j] = true;
            }
        }


//        printMap();

        int count = 0;
        for (int r = 0; r < R; r++) {
            if (findPipeLine(r, 0)) count++;
//            System.out.println("r:"+r);
//            printMap();
//            System.out.println();
        }

        System.out.print(count);
    }

    private static void printMap() {
        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                if (isWall[i][j]) System.out.print("x");
                else System.out.print(".");
            }
            System.out.println();
        }
    }

    private static boolean findPipeLine(int r, int c) {
        // 인덱스 초과, 벽이라면 파이프 설치 불가 (재방문 포함)
        if (outOfBound(r,c) || isWall[r][c]) return false;

        isWall[r][c] = true; // 방문한 곳은 벽으로 처리

        // 원웅이의 빵집에 도달하면 파이프 연결 성공
        if (c == C - 1) return true;

        // 특정 경로에서 파이프라인을 발견 시, 더 이상 탐색하지 않고 종료한다.
        if (findPipeLine(r - 1, c + 1)) return true;
        if (findPipeLine(r, c + 1)) return true;
        if (findPipeLine(r + 1, c + 1)) return true;


        return false;
    }

    private static boolean outOfBound(int r, int c) {
        return r >= R || r < 0 || c >= C || c < 0;
    }
}



/**
 * 1번씩만 탐색한다면, 500만개의 탐색
 *
 * 1) 현 위치에서 C+1의 위>중간>아래 순으로 탐색
 * 2) 1)을 하는 가정하에 이미 지난 경로를 다시 갈 필요가 없음. 어짜피 다시와도 파이프 설치 불가
 * 3) 방문안한 빵집 발견 시 카운트
 */