import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String s = br.readLine();

        int[] counts = new int[10];
        for (int i = 0; i < s.length(); i++) {
            int num = s.charAt(i) - '0';
            counts[num] ++;
        }

        // 9,6을 공평하게 나누었을때 필요량
        int need = (counts[9] + counts[6]) / 2 + (counts[9] + counts[6]) % 2;
        counts[9] = need;
        counts[6] = need;

        int max = 0;
        for (int count : counts) {
            max = Math.max(count, max);
        }

        System.out.print(max);
    }
}
/**
 * 9하고 6은 뒤집어서 사용 가능하다.
 * -> 9,6은 같이 센 후, 나누기 2한다. 대신 나머지를 버리면 안됨.
 */
