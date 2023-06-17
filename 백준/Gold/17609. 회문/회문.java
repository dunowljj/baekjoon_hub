import java.io.*;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int T = Integer.parseInt(br.readLine());

        for (int i = 0; i < T; i++) {
            String input = br.readLine();
            int len = input.length();

            int left = 0;
            int right = len - 1;

            int val = check(input, left, right);
            System.out.println(val);
        }

    }

    private static int check(String input, int left, int right) {
        boolean isPalindrome = true; // 같은 대칭만 나오면 회문이다.
        boolean isSimilar = false; // 한 문자를 스킵한 경우에 대칭이면 유사회문이다.

        while (left < right) {

            if (input.charAt(left) != input.charAt(right)) {
                isPalindrome = false;
                isSimilar |= isSymmetry(input, left, right - 1);
                isSimilar |= isSymmetry(input, left + 1, right);
                break;
            }

            left++;
            right--;
        }

        if (isPalindrome) return 0;
        else if (isSimilar) return 1;
        else return 2;
    }

    private static boolean isSymmetry(String input, int left, int right) {
        while (left < right) {
            if (input.charAt(left++) != input.charAt(right--)) return false;
        }

//        System.out.println("isSymmetry = " + true);
        return true;
    }
}
/**
 * 0 : 회문
 * 1 : 유사회문
 * 2 : 일반
 */
