import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Main {

    public static final String SORRY_HANSOO = "I'm Sorry Hansoo";

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int answer = 0;

        String name = br.readLine();
        int[] counts = new int[26];
        for (int i = 0; i < name.length(); i++) {
            counts[name.charAt(i) - 'A']++;
        }

        if (oddMoreThanTwo(counts)) {
            System.out.print(SORRY_HANSOO);
        } else {
            System.out.print(findAnswer(counts));
        }

    }

    private static boolean oddMoreThanTwo(int[] counts) {
        int oddCount = 0;
        for (int count : counts) {
            if (count % 2 == 1) oddCount++;
            if (oddCount >= 2) return true;
        }

        return false;
    }

    private static String findAnswer(int[] counts) {
        StringBuilder front = new StringBuilder();
        StringBuilder back = new StringBuilder();

        int i = 0;
        String mid = "";
        while (i < counts.length) {

            // 0인 경우 모두 스킵
            if (counts[i] == 0) {
                i++;
            }

            // 0아닌 짝수이면 양끝에 값 추가
            else if (counts[i] % 2 == 0) {
                counts[i] -= 2;
                front.append((char) (i + 'A'));
                back.append((char) (i + 'A'));
            }

            // 홀수인 경우 가운뎃 값으로 지정
            else if (counts[i] % 2 == 1) {
                counts[i] -= 1;
                mid = ((char) (i + 'A')) + "";
            }
        }


        return new StringBuilder()
                .append(front).append(mid).append(back.reverse())
                .toString();
    }

}

/**
 * 알파벳 대문자만 주어짐
 *
 * 홀수개인 알파벳이 2개 이상이면 펠린드롬을 만들 수 없음
 */