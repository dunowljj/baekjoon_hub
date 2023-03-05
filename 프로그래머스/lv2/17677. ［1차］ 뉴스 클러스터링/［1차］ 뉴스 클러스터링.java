import java.util.*;

class Solution {

    final Set<String> uniqueKeys = new HashSet<>();
    final static int CONSTANT_NUMBER = 65536;

    public int solution(String str1, String str2) {
        Map<String, Integer> countMap1 = makeCountMap(str1);
        Map<String, Integer> countMap2 = makeCountMap(str2);

//        test_printMaps(countMap1, countMap2);

        int[] counts = count(countMap1, countMap2); // [inter, union]
        int interCount = counts[0];
        int unionCount = counts[1];

        if (unionCount == 0) return CONSTANT_NUMBER;
        else return CONSTANT_NUMBER * interCount / unionCount;
    }

    private Map<String, Integer> makeCountMap(String str) {
        Map<String, Integer> countMap = new HashMap<>();
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < str.length(); i++) {
            char now = Character.toLowerCase(str.charAt(i)); // lowerCase로 통일

            if (!isAlphabet(now)) {
                if (builder.length() == 0) continue; // 첫 문자가 특수문자인 경우 처리

                builder.delete(0, builder.length()); // builder 비우기
                continue;
            }

            builder.append(now);

            if (builder.length() == 2) {
                String key = builder.toString();
                uniqueKeys.add(key);
                countMap.put(key, countMap.getOrDefault(key, 0) + 1);

                builder.deleteCharAt(0); // 앞 문자 삭제
            }
        }

        return countMap;
    }

    private int[] count(Map<String, Integer> countMap1, Map<String, Integer> countMap2) {
        int interCount = 0;
        int unionCount = 0;
        for (String key : uniqueKeys) {
            // 둘다 있음
            if (countMap1.containsKey(key) && countMap2.containsKey(key)) {
                interCount += Math.min(countMap1.get(key), countMap2.get(key));
                unionCount += Math.max(countMap1.get(key), countMap2.get(key));
                continue;
            }

            // 한쪽만 있는 경우
            if (countMap1.containsKey(key) && !countMap2.containsKey(key)) {
                unionCount += countMap1.get(key);
                continue;
            }

            if (!countMap1.containsKey(key) && countMap2.containsKey(key)) {
                unionCount += countMap2.get(key);
                continue;
            }

        }

        return new int[]{interCount, unionCount};
    }

    private boolean isAlphabet(char now) {
        return 'a' <= now && now <= 'z';
    }

    private static void test_printMaps(Map<String, Integer> countMap1, Map<String, Integer> countMap2) {
        for (Map.Entry<String, Integer> entry : countMap1.entrySet()) {
            System.out.print("key : " + entry.getKey() + " , val : " + entry.getValue());
            System.out.println();
        }
        System.out.println("------");
        for (Map.Entry<String, Integer> entry : countMap2.entrySet()) {
            System.out.print("key : " + entry.getKey() + " , val : " + entry.getValue());
            System.out.println();
        }
    }
}
/*
J(A, B)는 두 집합의 교집합 크기를 두 집합의 합집합 크기로 나눈 값으로 정의된다.
A [1,1,1]
B [1,1,1,1,1]
ANB 3개  / AUB 5개

집합 A와 집합 B가 모두 공집합일 경우 J(A, B) = 1
--> J(A, B)가 1인 것이므로 상수를 곱해야한다.

### 원소
- 순서가 다른 문자원소는 다른 것으로 판단하는듯 하다.
- 대소문자 구분 x
- 특수문자 제거

"ab+"가 입력으로 들어오면, "ab"만 다중집합의 원소로 삼고, "b+"는 버린다.
--> ab+cd 가 있다면 [ab, b+, +c, cd] -> [ab, cd]만 남는다.

*/