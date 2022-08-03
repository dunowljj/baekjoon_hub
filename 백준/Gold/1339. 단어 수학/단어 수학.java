import java.io.*;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int N = Integer.parseInt(br.readLine());
        int[] alphabetDigit = new int[26];

        for (int i = 0; i < N; i++) {
            String input = br.readLine();
            int digit = (int)Math.pow(10, input.length() - 1);
            for (int j = 0; j < input.length(); j++) {
                int alphabet = input.charAt(j) - 'A';

                alphabetDigit[alphabet] += digit;
                digit /= 10;
            }
        }

        Arrays.sort(alphabetDigit);

        int lastIdx = alphabetDigit.length - 1;
        int sum = 0;
        for (int i = 9; i >= 0; i--, lastIdx--) {
            if (alphabetDigit[lastIdx] == 0) break;
            sum += alphabetDigit[lastIdx] * i;
        }

        bw.write(sum+"");
        bw.flush();
        bw.close();
    }

}
/*
각 알파벳이 나올때마다 해당 자릿수를 배열에 저장해서, 한꺼번에 곱한다.
결국 총합을 구하는 문제이므로, 해당 자릿수를 미리 분리해도 상관이 없는 것이다.
 */