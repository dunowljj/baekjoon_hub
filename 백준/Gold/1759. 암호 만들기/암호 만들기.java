import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static int L;
    static char[] chs;
    static char[] password;
    static boolean[] visited;
    static boolean[] isVowel;
    static StringBuilder sb = new StringBuilder();
    public static void main(String[] args) throws IOException {
        final char[] vowels = new char[]{'a', 'e', 'i', 'o', 'u'};

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        L = Integer.parseInt(st.nextToken());
        int C = Integer.parseInt(st.nextToken());

        chs = new char[C];
        password = new char[L];
        isVowel = new boolean[C];
        visited = new boolean[C];

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < C; i++) {
            chs[i] = st.nextToken().charAt(0);
        }

        Arrays.sort(chs);

        // isVowel에 모음 미리 체크해두기
        for (int i = 0; i < chs.length; i++) {
            for (int j = 0; j < vowels.length; j++) {
                if (chs[i] == vowels[j]) {
                    isVowel[i] = true;
                }
            }
        }


        dfs(0, 0,0 , 0);

        bw.write(sb.toString().trim());
        bw.flush();
        bw.close();
    }
    static void dfs(int depth, int countV, int countC, int start) {
        if (depth == L) {
            if (countC >= 2 && countV >= 1) {
                sb.append(password).append("\n");
            }
            return;
        }

        for (int i = start; i < chs.length; i++) {
            if (!visited[i]) {
                visited[i] = true;
                if (isVowel[i])  {
                    countV++;
                } else {
                    countC++;
                }
                password[depth] = chs[i];
                dfs(depth + 1, countV, countC, i + 1);

                visited[i] = false;
                if (isVowel[i]) {
                    countV--;
                } else {
                    countC--;
                }
            }
        }
    }
}
/*
L 암호 길이, C 주어진 문자
3<=L<=C<=15
증가하는 순서 o
최소 1개 모음, 최소 2개 자음 -> 최소! 1개 이상, 2개 이상 있어야 함
1) 모음,자음 개수를 세고 마지막에 처리 o

미리 오름차순 정렬, 중복 미리 제거 o
 */