import java.io.*;
import java.util.StringTokenizer;

public class Main {
    static int[] curr;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        int N = Integer.parseInt(br.readLine());
        curr = new int[N];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            curr[i] = Integer.parseInt(st.nextToken());
        }

        // 감소하기 시작하는 인덱스 i1 구하기
        int i1 = curr.length - 1;
        while (i1 != 0 && curr[i1 - 1] < curr[i1]) {
            i1--;
        }
        if(isFirstPermutation(i1)) {
            sb.append("-1");
        } else {
            i1--;

            // 감소 시작점보다 작은 수 중에 최댓값인 i2 찾기
            int i2 = curr.length - 1;
            while (curr[i1] < curr[i2]) {
                i2--;
            }

            swap(i1,i2);


            // 시작점 뒷부분 내림차순 정렬
            i2 = curr.length - 1;
            while (i1 + 1 < i2) {
                swap(i1 + 1, i2);
                i1++; i2--;
            }

            for (int num : curr) {
                sb.append(num).append(" ");
            }
        }



        bw.write(sb.toString().trim());
        bw.flush();
        bw.close();
    }

    private static boolean isFirstPermutation(int i1) {
        return i1 == 0;
    }

    private static void swap(int i1, int i2) {
        int temp = curr[i1];
        curr[i1] = curr[i2];
        curr[i2] = temp;
    }
}
/*
모두 오름차순이면, 첫번째 순열이다. -> -1 출력
수열에서 감소하는 부분이 있다면, 첫번째 순열이 아니다.
->  감소 시작점과 그 뒤에 있는 수 중 시작점의 값보다 작은 수중 최댓값과 바꾼다.; 감소 시작점 뒤는 모두 오름차순이므로, 뒤에서부터 탐색했을때 시작점보다 작은 수를 찾으면 됨
-> 감소 시작점이었던 위치 뒤에 숫자들을 내림차순 정렬한다. ; 시작점보다 작은수의 최댓값과 자리를 바꿨기때문에 그대로 오름차순 정렬이 되어있다. 대칭으로 위치를 바꾸어주면된다.
 */