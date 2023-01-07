import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        String first = st.nextToken();
        double a = Double.parseDouble(first);
        int b = Integer.parseInt(st.nextToken());
        BigDecimal result = BigDecimal.valueOf(a).pow(b);
        System.out.println(result.toPlainString());
    }
}
