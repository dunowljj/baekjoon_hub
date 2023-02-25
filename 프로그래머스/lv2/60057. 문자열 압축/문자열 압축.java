class Solution {
    public int solution(String s) {
        int sLen = s.length();
        if (sLen == 1) return sLen;
        
        int minLength = Integer.MAX_VALUE;
        
        // 첫 인덱스부터 일정 길이 단위만큼 문자열 감지
        for (int size = 1; size <= sLen / 2; size++) {
            int len = sLen;
            int boundCount = 1;
            boolean binded = false;

            for (int i = 0; i <= sLen - (2 * size); i += size) {
                // 해당 문자열 다음 인덱스부터 단위만큼 같은지 확인
                String sub1 = s.substring(i, i + size);
                String sub2 = s.substring(i + size, i + size + size);
                
                // 같다면 묶을 수 있다.
                if (sub1.equals(sub2)) {
                    len -= size; // 묶은 만큼 제외
                    binded = true;
                    boundCount++;
                }

                // 압축도중 다른 문자가 나온 경우 : 문자열에 숫자 1개가 추가되므로 +1해주기
                else if (binded) {
                    len ++;
                    if (boundCount >= 10) len++;
                    binded = false;
                    boundCount = 1;
                }
            }
            
            // 마지막 숫자 추가 안된 부분
            if (binded) {
                len ++;
                if (boundCount >= 10) len ++;
            }
            
            minLength = Math.min(minLength, len);
        }
        return minLength;
    }
}
/*
정한 단위로 압축한다.
1개 이상 단위로 압축한 것 중 가장 짧은 것의 길이를 return 하기

s의 길이 1000

숫자를 압축한 문자열 앞에다 적어야한다.
예 5처럼 맨 앞에 다른 문자가 있으면 불가능하다.
맨 앞부터 size로 잘랐을때 단위가 맞아떨어져야한다.
*/