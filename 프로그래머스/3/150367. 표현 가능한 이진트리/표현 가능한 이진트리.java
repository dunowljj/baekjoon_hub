class Solution {
    
    public static final int[] LEN = {1,3,7,15,31,63};
    
    public int[] solution(long[] numbers) {
        
        System.out.println(Long.toBinaryString((long)Math.pow(10,15)).length());
        
        int[] answer = new int[numbers.length];
        
        for (int i = 0; i < numbers.length; i++) {
            String bs = Long.toBinaryString(numbers[i]);
            
            int maxLen = bs.length() + (bs.length() - 1);

            // 앞에 0으로 패딩을 넣는다.
            // 포화이진트리의 길이 후보인 LEN과 똑같이 길이를 맞춘다.
            for (int j = 0; j < LEN.length; j++) {
                if (maxLen < LEN[j]) break;
                
                if (LEN[j] >= bs.length()) {
                    bs = "0".repeat(LEN[j] - bs.length()) + bs;    
                
                    if (bs.charAt(bs.length() / 2) == '1' && representable(bs)) {
                        answer[i] = 1;
                        break;
                    }
                }
            }
        }
        
        return answer;
    }
    
    public boolean representable(String bs) {
        if (bs.length() == 1) return true;
        
        int mid = bs.length() / 2;
        if (bs.charAt(mid) == '0') return hasOnlyDummy(bs);
        
        return representable(bs.substring(0, mid)) &&
            representable(bs.substring(mid + 1, bs.length()));
    }
    
    public boolean hasOnlyDummy(String bs) {
        for (char val : bs.toCharArray()) {
            if (val == '1') return false;
        }
        return true;
    }
}

/**
이진수로 나타냈을때 길이는, 포화이진트리이므로 (1 + 2의 제곱수들의 합) 형태

이진트리가 될 수 있는지 여부?
- 루트가 1이어야함.
- 1인 노드의 부모 중 0이 있으면 안됨.

ex)
10 = 1010(2)
--> 000 1 010
최대 n-1개의 0이 추가 가능하다. (그 이상 추가하면, 새로 추가한 0이 루트가 되어 이진트리가 될 수 없다.)

1~10^15 -> 이진수의 최대길이는? -> log2(10^15) -> 50자리수 -> 최대 50 + 49 = 109
가능한 길이 -> [1, 3, 7, 15, 31, 63], 127
*/