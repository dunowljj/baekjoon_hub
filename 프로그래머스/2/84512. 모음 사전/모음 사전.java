import java.util.HashMap;
import java.util.Map;

class Solution {

    private static int order = 1;
    private static final char[] chars = {'A', 'E', 'I', 'O', 'U'};

    public int solution(String word) {
        Map<String, Integer> dict = new HashMap<>();
        makeDictionary(dict, new StringBuilder(),0);
        return dict.get(word);
    }

    private void makeDictionary(Map<String, Integer> dict, StringBuilder sb, int depth) {
        if (!sb.toString().isEmpty() && !dict.containsKey(sb.toString())) {
            dict.put(sb.toString(), order++);
        }

        if (depth == 5) return;

        for (int i = 0; i < 5; i++) {
            sb.append(chars[i]);
            makeDictionary(dict, sb, depth + 1);
            sb.deleteCharAt(sb.length() - 1);
        }
    }
}