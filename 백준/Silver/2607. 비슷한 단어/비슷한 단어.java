import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int numOfWords = Integer.parseInt(br.readLine());

        String fWord = br.readLine();
        int[] fCount = new int[26];
        int fLen = fWord.length();
        countCharacters(fWord, fCount);


        int answer = 0;
        for (int i = 1; i < numOfWords; i++) {
            String word = br.readLine();
            int[] count = new int[26];
            int len = word.length();
            countCharacters(word, count);

            if (isSimilar(fCount, fLen, count, len)) answer++;
        }

        System.out.print(answer);
    }

    private static void countCharacters(String word, int[] count) {
        for (int i = 0; i < word.length(); i++) {
            count[word.charAt(i) - 'A']++;
        }
    }

    private static boolean isSimilar(int[] firstCount, int fLen, int[] count, int len) {
        int lenDiff = Math.abs(fLen - len);
        if (lenDiff >= 2) return false;

        int chDiff = 0;
        for (int i = 0; i < 26; i++) {
            chDiff += Math.abs(firstCount[i] - count[i]);
        }

        if (lenDiff == 0 && (chDiff == 0 || chDiff == 2)) return true;
        return chDiff == 1;
    }

}

/**
 * 최대 단어 길이 10
 * 단어 수 100
 *
 * (10*100)개 문자 * 26개 알파벳 비교
 * 모두 대문자만 주어짐
 */