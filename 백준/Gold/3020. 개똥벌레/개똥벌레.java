import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int H = Integer.parseInt(st.nextToken());

        int[] widthSum = new int[H + 1]; // 1 ~ H 범위
        List<Integer> toUp = new ArrayList<>();
        List<Integer> toDown = new ArrayList<>();

        for (int i = 0; i < N; i++) {
            int size = Integer.parseInt(br.readLine());

            // 석순 : 아래 -> 위
            if (i % 2 == 0) {
                toUp.add(size);

            // 종유석 : 위 -> 아래
            } else {
                toDown.add(size);
            }
        }

        // 석순이 작은 순서대로 정렬
        Collections.sort(toUp);
        int lastH = H;

        // N / 2 개
        for (int i = 0; i < toUp.size(); i++) {
            int len = toUp.get(i);

            for (int j = lastH; j > H - len; j--, lastH--) {
                widthSum[j] += toUp.size() - i;
            }
        }

//        printWidthSum(widthSum);

        // 종유석이 작은 순서대로 정렬
        Collections.sort(toDown);
        lastH = 1;
        for (int i = 0; i < toDown.size(); i++) {
            int len = toDown.get(i);

            for (int j = lastH; j <= len; j++, lastH++) {
                widthSum[j] += toDown.size() - i;
            }
        }

//        printWidthSum(widthSum);

        int min = Integer.MAX_VALUE;
        for (int i = 1; i < widthSum.length; i++) {
            min = Math.min(min, widthSum[i]);
        }

        int count = 0;
        for (int i = 1; i < widthSum.length; i++) {
            if (min == widthSum[i]) {
                count++;
            }
        }

        System.out.print(min + " " + count);
    }

    private static void printWidthSum(int[] widthSum) {
        for (int i = 1; i < widthSum.length; i++) {
            System.out.print(widthSum[i]+" ");
        }
        System.out.println();
    }
}
