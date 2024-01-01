import java.util.HashMap;
import java.util.Map;

class Solution {

    private static boolean finished = false;
    private static int order = 1;
    private static final char[] chars = {'A', 'E', 'I', 'O', 'U'};

    public int solution(String word) {
        for (int i = 0; i < chars.length; i++) {
            makeDictionary(new StringBuilder().append(chars[i]), 1, word);
        }
        return order;
    }

    private void makeDictionary(StringBuilder sb, int depth, String word) {
        if (finished) return;
        
        if (sb.toString().equals(word)) {
            finished = true;
            return;
        }
        
        order++;

        if (depth == 5) return;

        for (int i = 0; i < 5; i++) {
            sb.append(chars[i]);
            makeDictionary(sb, depth + 1, word);
            sb.deleteCharAt(sb.length() - 1);
        }
    }
}