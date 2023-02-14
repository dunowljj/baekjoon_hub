import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        BigDecimal b1 = new BigDecimal(st.nextToken());
        BigDecimal b2 = new BigDecimal(st.nextToken());

        System.out.println(b1.subtract(b2).abs());
    }
}