import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.function.Function;

public class Main {

    static int N;
    static int[] nums;
    public static final int EMPTY_VALUE = 100_000_000;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());
        st = new StringTokenizer(br.readLine());

        nums = new int[N];
        for (int i = 0; i < N; i++) {
            int num = Integer.parseInt(st.nextToken());
            nums[i] = num;
        }

        if (N == 1 || (N == 2 && nums[0] != nums[1])) {
            System.out.print("A");
            return;
        }
        else if (N == 2 && nums[0] == nums[1])  {
            System.out.print(nums[0]);
            return;
        }

        int a,b;

        // divide by zero 방지
        if (nums[0] == nums[1]) {
            a = 1;
            b = 0;
        } else {
            a = (nums[2] - nums[1]) / (nums[1] - nums[0]);
            b = nums[1] - (a * nums[0]);
        }

        Rule rule = Rule.of(a,b);
        if (checkAllBy(rule)) {
            System.out.print(rule.apply(nums[N - 1]));
        } else {
            System.out.print("B");
        }
    }

    private static boolean checkAllBy(Rule rule) {
        for (int i = 0; i < N - 1; i++) {
            if (rule.apply(nums[i]) != nums[i + 1]) {
                return false;
            }
        }
        return true;
    }

    static class Rule implements Function<Integer, Integer> {
        int a;
        int b;

        public Rule(int a, int b) {
            this.a = a;
            this.b = b;
        }

        public static Rule of(int a, int b) {
            return new Rule(a, b);
        }

        @Override
        public Integer apply(Integer num) {
            return (num * a) + b;
        }
    }
}

/**
수학으로 접근안하면 범위처리가 까다로움
 */