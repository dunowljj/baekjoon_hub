import java.util.Arrays;

class Solution {
    public String solution(int[] numbers) {
        // 6, 62, 67, 600, 0
        // 06 60
        StringBuilder sb = new StringBuilder();

        // 67 6 --> 676 667
        Arrays.stream(numbers)
                .mapToObj(String::valueOf)
                .sorted((o1, o2) -> (o2+o1).compareTo(o1+o2))
                .map(String::valueOf)
                .forEach((s) -> sb.append(s));

        int idx = 0;
        while (idx < sb.length() && sb.charAt(idx) == '0') {
            idx++;
        }

        if (idx == sb.length()) return "0";
        else return sb.substring(idx);
    }
}
