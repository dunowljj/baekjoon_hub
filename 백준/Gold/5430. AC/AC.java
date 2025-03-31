import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());
        for (int i = 0; i < T; i++) {

            String command = br.readLine();
            int n = Integer.parseInt(br.readLine());
            String array = br.readLine();

            String result = getResult(command, array, n);
            System.out.println(result);
        }
    }

    private static String getResult(String command, String array, int size) {
        String[] nums = array.substring(1, array.length() - 1).split(",");

//        for (int i = 0; i < nums.length; i++) {
//            System.out.print(nums[i]+ " ");
//        }
//        System.out.println();

        int reverseCount = 0;
        int front = 0;
        int back = nums.length - 1;

        for (char c : command.toCharArray()) {
            if (c == 'D') {
                if (size == 0) return "error";

                size--;

                // 정방향 -> 첫 수 버리기
                if (reverseCount % 2 == 0) {
                    front++;

                // 역방향 -> 뒤에 수 버리기
                } else {
                    back--;
                }

                continue;
            }

            if (c == 'R') {
                reverseCount++;
            }
        }


        StringBuilder sb = new StringBuilder();
        sb.append("[");

        if (reverseCount % 2 == 0) {
            for (int i = front; i <= back; i++) {
                sb.append(nums[i]).append(",");
            }
        }

        if (reverseCount % 2 == 1) {
            for (int i = back; i >= front; i--) {
                sb.append(nums[i]).append(",");
            }
        }

        int index = sb.lastIndexOf(",");
        if (index != -1) sb.deleteCharAt(index);
        sb.append("]");

        return sb.toString().trim();
    }
}
