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

        startLink = new int[N][N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                startLink[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        for (int i = 2; i <= N - 2; i++) {
            start = new int[i];
            link = new int[N-i];
            dfs(0, 0, i);
        }
        
        bw.write(min+"");
        bw.flush();
        bw.close();
    }
    static void dfs(int depth, int idx, int len) {
        if (len == depth) {
            link = getAnotherTeam(start, len);
            min = Math.min(min, getDifference(start, link));
            return;
        }

        for (int i = idx; i < N; i++) {
            start[depth] = i;
            dfs(depth + 1, i + 1, len);
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

    private static int[] getAnotherTeam(int[] team, int len) {
        int[] anotherTeam = new int[N - len];
        int count = 0;
        for (int i = 0; i < N; i++) {
            boolean isMyTeam = false;

            for (int j = 0; j < len; j++) {
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
1) 동시에 구하기
2) 2 <= x <= len - 2 크기의 모든 수열 구하고 나머지 구하기. ->
 */