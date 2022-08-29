import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int N = Integer.parseInt(br.readLine());
        int[] times = new int[N];

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            times[i] = Integer.parseInt(st.nextToken());
        }

        Arrays.sort(times);

        int answer = 0;

        for (int i = 0; i < N; i++) {
            answer += times[i] * (N - i);
        }

        bw.write(answer+"");
        bw.flush();
        bw.close();
    }
}
/*
cpu SJF 생각하기

1) 작업 시간이 짧은 기준으로 정렬한다.
2) 각 해당 인덱스까지의 합을 구한다

첫 예시
p1 = 3 / p2 = 1 / p3 = 4/ p4 = 3 / p5 = 2

정순으로 더하기
3 + 4 + 8 + 11 + 13 = 39

각 요소 반복횟수만큼 곱하기
3*5 + 1*4 + 4*3 + 3+2 + 2*1
15 + 4 + 12 + 6 + 2 = 39

 */