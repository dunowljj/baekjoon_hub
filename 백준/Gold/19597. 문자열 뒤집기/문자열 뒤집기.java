import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    private static String[] S,R;
    private static int N;
    private static boolean flag;
    private static StringBuilder answer = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());
        while (T-- > 0) {
            N = Integer.parseInt(br.readLine());
            S = new String[N];
            R = new String[N];

            S[0] = "A";
            R[0] = "A";
            for (int i = 0; i < N; i++) {
                S[i] = br.readLine();
                R[i] = new StringBuilder(S[i]).reverse().toString();
            }

            flag = false;
            findReverseString(0, new StringBuilder(), "A");
        }

        System.out.print(answer.toString().trim());
    }

    private static void findReverseString(int depth, StringBuilder sb, String before) {
        if (depth == N) {
            answer.append(sb).append(System.lineSeparator());
            flag = true;
            return;
        }

        if (before.compareTo(S[depth]) < 0) {
            sb.append('0');
            findReverseString(depth + 1, sb, S[depth]);
            if (flag) return;
            sb.deleteCharAt(sb.length() - 1);
        }

        if (before.compareTo(R[depth]) < 0) {
            sb.append('1');
            findReverseString(depth + 1, sb, R[depth]);
            if (flag) return;
            sb.deleteCharAt(sb.length() - 1);
        }
    }
}
/**
 * - 리버스 문자열은 숫자로 최종 형성된 010, 000 따위를 말함
 * - 리버스 문자열이 빠른 순서로 배치해야한다. 즉, 최대한 늦게 뒤집어야한다.
 *
 * 알파벳을 사전순 빠르게 배열하는게 아니다. 필요하다면 최대한 뒤집지 않고 진행해야한다.
 * 뒤집지 않아도 되는가?는 다음 문자열을 봐야 알 수 있으며, 결국 최종 문자열까지 확인해야 알 수 있다.
 * 결국 모든 경우를 적절히 검수해야한다.
 * 백트래킹으로 해결하자.
 * */
