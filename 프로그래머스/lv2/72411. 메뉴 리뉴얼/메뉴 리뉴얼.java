import java.util.*;
import java.util.stream.*;

class Solution {
    public String[] solution(String[] orders, int[] course) {
        List<String> courseMenus = new ArrayList<>();
        Map<Integer, Map<String, Integer>> lenMap = new HashMap<>(); // Map<길이, Map<메뉴, 주문 횟수>>
        
        int maxLength = 0;        
        for (int len : course) {
            if (maxLength < len) maxLength = len;
            lenMap.put(len, new HashMap<>());
        }

        for (String order : orders) {
            char[] orderArr = order.toCharArray();
            Arrays.sort(orderArr);
            findCombs(0, maxLength, orderArr.length, orderArr, new StringBuilder(), lenMap);
        }
        
        for (int len : course) {
            Map<String,Integer> resultMap = lenMap.get(len);
            int max = findPopular(resultMap);
            if (max < 2) continue;
            
            // 가장 높은 빈도의 메뉴 추가
            for (String menu : resultMap.keySet()) {
                if (resultMap.get(menu) == max) {
                    courseMenus.add(menu);
                }
            }
        }
        
        return courseMenus.stream()
            .sorted()
            .toArray(String[]::new);
    }
    
    private void findCombs(int start, int maxLen, int len, char[] order, StringBuilder builder, Map<Integer, Map<String, Integer>> lenMap) {
        int nowLen = builder.length();
        if (lenMap.containsKey(nowLen)) {
            Map<String, Integer> resultMap = lenMap.get(nowLen);
            
            String now = builder.toString();
            resultMap.put(now, resultMap.getOrDefault(now, 0) + 1);
            
            if (nowLen == maxLen) return;
        }
        
        for (int i = start; i < order.length; i++) {
            builder.append(order[i]);
            findCombs(i + 1, maxLen,len, order, builder, lenMap);
            builder.deleteCharAt(builder.length() - 1);
        }
    }
    
    private void findAllCombs(int start, int maxLen, Set<Integer> lens, List<Character> alphabets, List<String>[] combs, StringBuilder builder) {
        int nowLen = builder.length();
        if (lens.contains(nowLen)) {
            combs[nowLen].add(builder.toString());  
            
            if (nowLen == maxLen) return;
        }
        
        for (int i = start; i < alphabets.size(); i++) {
            builder.append(alphabets.get(i));
            findAllCombs(i + 1, maxLen, lens, alphabets, combs, builder);
            builder.deleteCharAt(builder.length() - 1);
        }
    }
    
    private int findPopular(Map<String, Integer> resultMap) {
        int max = 0;
        for (Integer count : resultMap.values()) {
            if (max < count) max = count;
        }
        return max;
    }
}
/*
풀이 1 (14번 시간초과)
- 처음에 order에 존재하는 모든 알파벳을 set에 넣는다.
- dfs를 활용해 course에 존재하는 길이 별로 해당하는 모든 조합을 만든다.

풀이 2 (14번 시간초과)
- 1번과 대부분 동일
- course의 길이를 set 하나에 저장해놓고, dfs시에 모든 조합을 한꺼번에 만든다.

풀이 3
- 각 order를 기준으로 조합을 만든다. -> HashMap을 사용해서 조합을 만들면서 동시에 카운팅을 한다.
- 마찬가지로 course에서 maxLength를 도출하여 넘지 않도록 탐색한다.

조합을 만들때 탐색 횟수를 줄이기 위해 조합을 한꺼번에 만들어야 한다.
*/