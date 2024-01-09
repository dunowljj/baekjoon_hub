import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String N = br.readLine().trim();

        if (N.length() <= 2) {
            System.out.print(N);
        } else {
            int count = 0;
            int num = Integer.parseInt(N);
            for (int i = 1; i <= num; i++) {
                if (isHanNumber(i)) {
                    count++;
                }
            }

            System.out.print(count);
        }

    }

    private static boolean isHanNumber(int num) {
        if (String.valueOf(num).length() <= 2) return true;

        List<Integer> number = new ArrayList<>();
        int divider = 10;
        while (num > 0) {
            int quotient = num % divider;
            num /= 10;

            number.add(quotient);
        }

        int firstDiff = number.get(1) - number.get(0);
        for (int i = 1; i < number.size() - 1; i++) {
            int diff = number.get(i + 1) - number.get(i);
            if (firstDiff != diff) return false;
        }

        return true;
    }
}
