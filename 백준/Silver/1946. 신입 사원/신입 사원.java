import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder answer = new StringBuilder();

        int T = Integer.parseInt(br.readLine());
        while (T-- > 0) {
            int N = Integer.parseInt(br.readLine());
            Grade[] grades = new Grade[N];

            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());

                int dRank = Integer.parseInt(st.nextToken());
                int iRank = Integer.parseInt(st.nextToken());
                grades[i] = new Grade(dRank, iRank);
            }

            Arrays.sort(grades, Comparator.comparingInt(g -> g.dRank));

            int minIRank = 100_001;
            for (int i = 0; i < grades.length; i++) {
                if (grades[i].iRank > minIRank) N--;
                else minIRank = grades[i].iRank;
            }

            answer.append(N).append(System.lineSeparator());
        }

        System.out.print(answer.toString().trim());
    }

    static class Grade {
        int dRank;
        int iRank;

        public Grade(int dRank, int iRank) {
            this.dRank = dRank;
            this.iRank = iRank;
        }
    }
}

/**
 * 각 과의 수석을 추출하는 것보다 모든 경우를 검사해야함.
 * 10만명이 있기때문에, 제곱시 100억으로 너무 많다.
 * 정렬해서 브루트포스로 비교해도 n^2이 나올수도 있다.
 * 
 * 등수가 겹치지 않으므로, 한 기준으로 정렬 후, 나머지 한 기준으로 가장 높은 등수를 기억하면서 비교한다.
 */
