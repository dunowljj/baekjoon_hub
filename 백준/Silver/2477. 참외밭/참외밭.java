import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        List<Side> sides = new ArrayList<>();

        int K = Integer.parseInt(br.readLine());

        int[] counts = new int[5];

        for (int i = 0; i < 6; i++) {
            st = new StringTokenizer(br.readLine());
            int dir = Integer.parseInt(st.nextToken());
            int len = Integer.parseInt(st.nextToken());

            counts[dir]++;
            sides.add(new Side(dir, len));
        }

        List<Integer> dirs = new ArrayList<>();
        List<Integer> splitDirs = new ArrayList<>();
        for (int i = 1; i < counts.length; i++) {
            if (counts[i] == 1) {
                dirs.add(i);
            }

            if (counts[i] == 2) {
                splitDirs.add(i);
            }
        }

        int len1 = findLen(sides, dirs.get(0));
        int len2 = findLen(sides, dirs.get(1));
        int width = len1 * len2;

        /**
         * 동 1, 서 2, 남 3, 북 4
         * - 동1->남3
         * - 북4->동1
         * - 서2->북4
         * - 남3->서2
         */
        int emptyWidth = 0;
        int splitDir1 = splitDirs.get(0);
        int splitDir2 = splitDirs.get(1);
        if (splitDir1 == 1 && splitDir2 == 3 || splitDir1 == 3 && splitDir2 == 1) {
            emptyWidth = findEmptyWidth(sides, 1, 3);
        } else if (splitDir1 == 4 && splitDir2 == 1 || splitDir1 == 1 && splitDir2 == 4) {
            emptyWidth = findEmptyWidth(sides, 4, 1);

        } else if (splitDir1 == 2 && splitDir2 == 4 || splitDir1 == 4 && splitDir2 == 2) {
            emptyWidth = findEmptyWidth(sides, 2, 4);

        } else if (splitDir1 == 3 && splitDir2 == 2 || splitDir1 == 2 && splitDir2 == 3) {
            emptyWidth = findEmptyWidth(sides, 3, 2);
        }

        System.out.print(K * (width - emptyWidth));
    }

    private static int findLen(List<Side> sides, int dir) {
        for (Side side : sides) {
            if (side.dir == dir) {
                return side.len;
            }
        }

        return 0;
    }

    private static int findEmptyWidth(List<Side> sides, int dir1, int dir2) {
        for (int i = 0; i < sides.size() - 1; i++) {
            Side side1 = sides.get(i);
            Side side2 = sides.get(i + 1);
            if (side1.dir == dir1 && side2.dir == dir2) {
                return side1.len * side2.len;
            }
        }

        // 못찾으면 움푹 패인부분이 시작점이고, 맨 처음 변과 마지막 변이 찾는 빈 부분이다.
        return sides.get(0).len * sides.get(sides.size() - 1).len;
    }

    static class Side {
        int dir;
        int len;

        public Side(int dir, int len) {
            this.dir = dir;
            this.len = len;
        }
    }
}

/**
 * 두 개인 면이 있는 방향을 찾으면 밭의 모양을 알 수 있다.
 * 동 1, 서 2, 남 3, 북 4
 *
 * 1) 면이 두개인 방향을 찾는다.
 * 2) 반시계방향 진행을 고려하여, 사각형 기준으로 밭의 빈 부분을 찾는다.
 * 반시계방향 진행이기때문에,
 * - 동1->남2
 * - 북4->동1
 * - 서2->북4
 * - 남3->서2 (예시에서 60,20)
 * 해당 방향으로 연속되는 가장 작은 수를 찾으면 된다.
 *
 * 3) 시작 꼭지점이 움푹 패인부분인 경우를 고려한다.
 */