import java.io.*;
import java.util.StringTokenizer;

public class Main {
    static int N;
    static int min = 10_000;// 99 * 10 = 990
    static int[][] startLink;
    static int[] start;
    static int[] link;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;
        N = Integer.parseInt(br.readLine());
        start = new int[N/2];
        link = new int[N/2];

        startLink = new int[N][N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                startLink[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        dfs(0, 0);
        bw.write(min+"");
        bw.flush();
        bw.close();
    }

    static void dfs(int depth, int idx) {
        if (depth == N / 2) {
            link = getAnotherTeam(start);
            min = Math.min(min, getDifference(start, link));
            return;
        }

        for (int i = idx; i < N; i++) {
                start[depth] = i;
                dfs(depth + 1, i + 1);
            }
        }

    static int getDifference(int[] team1, int[] team2) {
        return Math.abs(getTeamStat(team1) - getTeamStat(team2));
    }

    static int getTeamStat(int[] team) {
        int stat = 0;
        for (int i = 0; i < team.length; i++) {
            for (int j = i + 1; j < team.length; j++) {
                stat += startLink[team[i]][team[j]] + startLink[team[j]][team[i]];
            }
        }
        return stat;
    }

    private static int[] getAnotherTeam(int[] team) {
        int[] anotherTeam = new int[N / 2];
        int count = 0;
        for (int i = 0; i < N; i++) {
            boolean isMyTeam = false;

            for (int j = 0; j < N / 2; j++) {
                if (i == team[j]) {
                    isMyTeam = true;
                    break;
                }
            }
            
            if (!isMyTeam) {
                anotherTeam[count++] = i;
            }
        }
        return anotherTeam;
    }
}

/*
S[i][j] + S[j][i]
-> 인원수가 많으면 모두 대조해야함. S[i][팀원들 모두] + S[팀원들 모두][i]

start 순열, link 순열 각각 능력치 구하는 메서드 만들기

1~N에서 N/2 개를 뽑는 순열을 만들고, 미리 입력받은 능력치 배열에서 값을 구한다.
1) 1~N개에서 N/2개를 뽑은 수열, 뽑히지 않은 수열을 동시에 만든다.
    - ?1 수열을 먼저 다 뽑고, 나머지를 찾는다.
    - ?2 뽑으면서 같이 저장한다.
        - ?1 N까지 모두 순회하면서 해당 depth에 저장 -> 각 팀의 인원수를 N으로 설정해놔야함 -> 0 이생기는 것은 중복제거 혹은 Integer사용
        - ?2 depth를 두 개 사용, 어느 시점에 다른팀에 인원을 저장할 것인가?
2) 대조해서 구한 각 팀의 능력치 차이를 구하는 메서드에 넣는다. o
3) 탐색이 종료 시 두 팀의 능력치 차이를 구하고, 최솟값을 구한다.

 */