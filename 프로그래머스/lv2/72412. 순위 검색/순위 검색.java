import java.util.*;

/*
비트로 나타내려면 요소당 2자리씩 소모해야한다. 상관없음 항목 + 최대 3개이기 때문이다. 가독성이 좋은 코드를 만들 수 있을까?
00 01 10 11

4 * 3 * 3 * 3 = 108가지 패턴을 모두 만들면 String으로 바로 검색할 수 있다.
*/

class Solution {
    private final Map<String, ArrayList<Integer>> map = new HashMap();
    
    public int[] solution(String[] info, String[] query) {
        int[] result = new int[query.length];
        int idx = 0;
        
        for (String in : info) {
            String[] split = in.split("\\s");
            makePatternMap(0, "", split);
        }
        
        for (String key : map.keySet()) {
            Collections.sort(map.get(key));
        }
        
        for (String q : query) {
            String qs[] = q.replace(" and ", "")
                .split("\\s");
            
            int score = Integer.parseInt(qs[1]);
            int answer = binarySearch(map.getOrDefault(qs[0], new ArrayList<Integer>()), score);
            
            result[idx++] = answer;
        }
        
        
        return result;
    }
    
    private void makePatternMap(int depth, String pattern, String[] split) {
        if (depth == 4) {
            if (!map.containsKey(pattern)) {
                map.put(pattern, new ArrayList<Integer>());
            } 
            map.get(pattern).add(Integer.parseInt(split[depth]));
            return;
        }
        
        makePatternMap(depth + 1, pattern + split[depth], split);
        makePatternMap(depth + 1, pattern + "-", split);
        
    }

    private int binarySearch(List<Integer> list, int score) {
        int start = 0;
        int end = list.size() - 1;
        int mid = 0;
        while (start <= end) {
            mid = (start + end) / 2;
            
            if (list.get(mid) < score) {
                start = mid + 1;
            } else {
                end = mid - 1;   
            }
        }
        return list.size() - start;
    }
        
}
/*
중복 경우가 주어지는가?
점수 중복 있음
*/
