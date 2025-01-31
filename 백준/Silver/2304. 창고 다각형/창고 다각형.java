import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

import static java.util.Comparator.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int N = Integer.parseInt(br.readLine());
        Column[] columns = new Column[N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            int L = Integer.parseInt(st.nextToken());
            int H = Integer.parseInt(st.nextToken());

            columns[i] = new Column(L, H);
        }

        // N==2일때 고려해보기
        if (N == 1) {
            System.out.print(columns[0].height);
            return;
        }

        Arrays.sort(columns, comparingInt(c -> c.loc));

        int width = 0;

        int left = 0;
        int right = N - 1;
        int leftMaxHeight = columns[left].height;
        int rightMaxHeight = columns[right].height;

        while (left < right) {

            Column leftStart = columns[left];
            while (left < right && leftMaxHeight <= rightMaxHeight) {
                left++;

                if (leftMaxHeight <= columns[left].height) {
                    int leftWidth = leftStart.height * (columns[left].loc - leftStart.loc);
                    width += leftWidth;

                    leftMaxHeight = columns[left].height;
                    leftStart = columns[left];

//                    System.out.println("leftWidth = " + leftWidth);
                }
            }

            Column rightStart = columns[right];
            while (left < right && leftMaxHeight >= rightMaxHeight) {
                right--;

                if (columns[right].height >= rightMaxHeight) {
                    int rightWidth = rightStart.height * (rightStart.loc - columns[right].loc);
                    width += rightWidth;

                    rightMaxHeight = columns[right].height;
                    rightStart = columns[right];

//                    System.out.println("rightWidth = " + rightWidth);
                }
            }
        }

        // 2 중앙에 가장 높은 곳에서 만난 경우 -> 기둥을 제외한 값이 모두 더해져 있음
        width += columns[left].height;

        System.out.print(width);
    }

    static class Column {
        int loc;
        int height;

        public Column(int loc, int height) {
            this.loc = loc;
            this.height = height;
        }
    }
}

/**
 * 수평/수직 부분은 기둥과 맞닿아야 한다.
 * 오목하면 안됨
 * 가장자리 땅에 닿기-> 기둥타고 내려감
 *
 * 가장 작은 창고다각형 면적을 찾아라..? 첫 예시에선 가장 큰건데?
 * 모든 기둥을 사용한 가장 작은 다각형의 면적을 찾는것
 *
 *
 * - 맨 앞에서부터 연결 -> 더 큰 높이가 나오면 타고 올라간다.
 * - 뒤에서도 동시 탐색을 해야하나?
 *
 * 사실상 빗물과 똑같다. 막대의 면적만 더해지는것이다.
 *
 * 왼쪽면 기준 좌표임을 기억하자.
 */