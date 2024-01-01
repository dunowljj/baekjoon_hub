import java.util.HashMap;
import java.util.Map;

class Solution {

    private static boolean finished = false;
    private static int order = 0;
    private static int answer = 0;
    private static final char[] chars = {'A', 'E', 'I', 'O', 'U'};

    public int solution(String word) {
        findOrder(new StringBuilder(), 0, word);
        return answer;
    }

    private void findOrder(StringBuilder sb, int depth, String word) {
        if (finished) return;

        if (sb.toString().equals(word)) {
            answer = order;
            finished = true;
            return;
        }

        if (depth == 5) return;

        for (int i = 0; i < 5; i++) {
            sb.append(chars[i]);
            order++;
            findOrder(sb, depth + 1, word);
            sb.deleteCharAt(sb.length() - 1);
        }
    }
}