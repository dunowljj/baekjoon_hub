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

        dfs(0, 0);

        bw.write(sb.toString().trim());
        bw.flush();
        bw.close();
    }
    private static void dfs(int depth, int start) {
        if (depth == L) {
            if (isValid()) {
                sb.append(password).append("\n");
            }
            return;
        }

        for (int i = start; i < chs.length; i++) {
            if (!visited[i]) {
                visited[i] = true;

                password[depth] = chs[i];
                dfs(depth + 1, i + 1);

                visited[i] = false;
            }
        }
    }

    private static boolean isValid() {
        int vowel = 0;
        int consonant = 0;
        for (char ch : password) {
            if (ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u') {
                vowel++;
            } else {
                consonant++;
            }
        }
        return vowel >= 1 && consonant >= 2;
    }
}
/*
L 암호 길이, C 주어진 문자
3<=L<=C<=15
증가하는 순서 o

미리 오름차순 정렬, 중복 미리 제거 o

최소 1개 모음, 최소 2개 자음 -> 최소! 1개 이상, 2개 이상 있어야 함
->모음,자음 개수를 마지막에 검증 o   


 */