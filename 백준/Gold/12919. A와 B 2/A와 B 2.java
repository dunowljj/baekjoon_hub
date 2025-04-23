import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class Main {
    private static int sLen;
    private static String reverseS;
    private static Set<String> visit = new HashSet<>();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String S = br.readLine();
        String T = br.readLine();
        sLen = S.length();

        reverseS = new StringBuilder(S).reverse().toString();

        check(S, T, false);

        System.out.print(0);
    }

    private static void check(String S, String T, boolean reversed) {
        if (visit.contains(T)) {
            return;
        }
        visit.add(T);

        if (sLen == T.length()) {
            if (
                    (!reversed && S.equals(T)) ||
                    (reversed && reverseS.equals(T))
            ) {
                System.out.print(1);
                System.exit(0);
            }

            return;
        }

        if (reversed) {
            if (T.charAt(0) == 'A') {
                check(S, T.substring(1), reversed);
            }

            if (T.charAt(T.length() - 1) == 'B') {
                check(S, T.substring(0, T.length() - 1), !reversed);
            }

        } else {
            if (T.charAt(T.length() - 1) == 'A') {
                check(S, T.substring(0, T.length() - 1), reversed);
            }

            if (T.charAt(0) == 'B') {
                check(S, T.substring(1), !reversed);
            }
        }
    }
}

/**
 * S -> T
 * 1) 뒤에 A추가
 * 2) 뒤에 B추가 후 뒤집기, 뒤집고 앞에 B추가
 *
 * T -> S
 * 1) 앞에서 B를 빼고 뒤집기 (빼는 방향 전환)
 * 2) 뒤에서 A를 빼기
 *
 * 앞이 B이고 뒤가 A일때 2가지 케이스로 나뉘어지기떄문에, 최악의 경우 2^50이런식으로 나오지 않을까?
 * Set으로 중복체크를 하면서 완전탐색을 해보자.
 */