class Solution {
    public int[] solution(long[] numbers) {
        int[] answer = new int[numbers.length];
        int idx = 0;
        
        for (long number : numbers) {
            String binaryNum = Long.toBinaryString(number);
            boolean expressable = false;
            
            int len = binaryNum.length();
            int maxLen = 2 * len - 1;
            
            // 주어진 이진수가 짝수길이인 경우
            if (len % 2 == 0) {
                binaryNum = "0" + binaryNum;  
                int mid = binaryNum.length() / 2;
                
                // root가 더미이면 이진트리 불가능 111
                if (binaryNum.charAt(mid) == '0') {
                    // to do nothing   
                }
                // 더미가 아니면 표현가능한지 확인
                else  {
                    expressable |= isExpressable(binaryNum);
                }

                // 0을 루트가 1이 아닌 한도까지 붙이고 표현 가능한지 확인
                while (!expressable && binaryNum.length() < maxLen) {
                    binaryNum = "00" + binaryNum;
                    
                    // 루트가 0인지 체크
                    if (binaryNum.charAt(++mid) == '0') continue;
                    // 루트가 1이면 표현가능한지 확인
                    else expressable |= isExpressable(binaryNum);
                }
                
                answer[idx++] = expressable ? 1 : 0;
            }
            
            // 주어진 이진수가 홀수길이인 경우
            else {
                answer[idx++] = isExpressable(binaryNum) ? 1 : 0;
            }
        }
        
        return answer;
    }
    
    private boolean isExpressable(String binaryNum) {
        int len = binaryNum.length();
        if (len == 1) return true;
        if (len % 2 == 0 || len == 0) return false;
        
        int mid = len / 2;
        if (binaryNum.charAt(mid) == '0') {
            return areChildrenDummy(binaryNum);
        }
        
        return isExpressable(binaryNum.substring(0, mid))
            && isExpressable(binaryNum.substring(mid + 1, len));
    }
    
    private boolean areChildrenDummy(String binaryNum) {
        for (int i = 0; i < binaryNum.length(); i++) {
            if (binaryNum.charAt(i) == '1') return false;
        }
        
        return true;
    }
}
/*
7 -> 111
5 -> 011
42 ->  010 1 010 -> 이미 포화 이진트리여도 추가할 수 있음
63 ->  011 1 111
111-> 1101111

111
중위순회 순서로 검사


주어진 수를 이진수로 변환시 포화트리를 나타낸 것이다. 즉, 길이가 홀수여야 한다.

- 중간을 기준으로 나누면 다시 홀수가 된다.
- 중간값이 0이면 리프노드가 아니므로 이진트리가 될 수 없다.
- 중간이 0이 나오면 실패, split한 길이가 1이 되면 종료한다.
- 0이 나올수도 있다. 대신 더미의 하위노드에 더미만 있어야 한다. 0의 하위 노드에 1이 있으면 안된다.


1) 중간 기점으로 나눈다.
2) 양쪽을 다시 중간 기점으로 나눈다.
3) 중간 기점으로 나누었을때 양쪽의 길이가 1일때까지 반복한다.

예시를 보면 42는 101010인데 앞에 0을 붙여서 더미를 추가할 수 있다.
짝수길이이지만, 0추가로 홀수가 되어 이진트리로 표현이 된다.

000 1 111
00000 1 11111
*/