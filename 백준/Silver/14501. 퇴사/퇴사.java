import java.io.*;
import java.util.StringTokenizer;

public class Main {
    static int N;
    static int[][] counsel;
    static int max = 0;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;
        N = Integer.parseInt(br.readLine());
        counsel = new int[N + 1][2];

        //counsel[i] : [0] -> T / [1] -> P
        for (int i = 1; i <= N; i++) {
            st = new StringTokenizer(br.readLine());
            counsel[i][0] = Integer.parseInt(st.nextToken());
            counsel[i][1] = Integer.parseInt(st.nextToken());
        }

        dfs(1, 0);

        bw.write(max+"");
        bw.flush();
        bw.close();
    }
    static void dfs(int day, int profit) {
        //날짜 8일 이상이면 총 수익 계산
        if (day >= N + 1) {
            max = Math.max(max, profit);
            return;
        }

        // N + 1 까지는 수익 더함 , 아닌 경우 수익말고 날짜만 더함
        for (int i = day; i <= N; i++) {
            if (i + counsel[i][0] > N + 1) {
                dfs(i + counsel[i][0], profit);
            } else {
                dfs(i + counsel[i][0], profit + counsel[i][1]);
            }
        }
    }
}
/*
매일 서로 다른 상담 1개씩 잡아놈

Ti 상담 완료 걸리는 기간
Pi 완료 시 받을 수 있는 금액

N + 1 일에 퇴사 -> 넘어서는 상담 불가능 -> n(일차) + Ti >= 배열 길이 - 1 상담 불가
최대 상담 이익 구하기

종료조건이 애매함. 합친 수가 N을 넘지 않는다면 모두 값을 계산해야함
[day = 1 부터 시작해서 N = 7일때]
- 마지막날까지 풀로 일한경우 : 7일에서 1일 일한 경우 >> day == 8(N+1)까지 허용 /  1일에서 7일 일함 => day == 8
- 남은 날 중 가능한 일이 없어서 마지막날전에 일이 끝난 경우 : 6일까지만 일한 경우 >> day == 7(N)인데 종료를 어떻게 잡아주나?
-> 날짜를 더했을때, 7일을 넘어가면 수익을 그대로 넘겨서 재귀한다.
 */