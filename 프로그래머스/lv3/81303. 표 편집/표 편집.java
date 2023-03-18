import java.util.*;
import java.util.stream.*;

class Solution {
    
    class RowInfo {
        int originIndex;
        int lastIndex;
        
        RowInfo(int originIndex) {
            this.originIndex = originIndex;
        }
    }
    
    public String solution(int rowCount, int nowIndex, String[] cmds) {
        StringBuilder answer = new StringBuilder();
        
        // LinkedList<RowInfo> rows = new LinkedList<>();
        // for (int i = 0; i < n; i++) rows.add(new RowInfo(i));
        
        Stack<Integer> removeStack = new Stack<>();

        // Stack<RowInfo> removeStack = new Stack<>();
        
        for (String cmd : cmds) {
            String command = cmd.split(" ")[0];
            
            // Up하면 위로 올라가서 인덱스가 작아진다.
            if (command.equals("U")) { 
                nowIndex -= Integer.parseInt(cmd.split(" ")[1]);
            }
            
            else if (command.equals("D")) {
                nowIndex += Integer.parseInt(cmd.split(" ")[1]);
            }
                
            else if (command.equals("C")) {
                removeStack.push(nowIndex);
                rowCount--;
                if (nowIndex == rowCount) nowIndex--;
            }
                
            else if (command.equals("Z")) {
                rowCount++;
                if (nowIndex >= removeStack.pop()) nowIndex++;
            } 
        }
        
        final char X = 'X';
        final char O = 'O';
        for (int i = 0; i < rowCount; i++) {
            answer.append(O);
        }
        
        while (!removeStack.isEmpty()) {
            int idx = removeStack.pop();
            answer.insert(idx, X);
        }
        
        return answer.toString();
    }
}
/*
LinkedList와 Stack

마지막 행인 경우 하나 위의 행을 선택해야 한다.

행 개수가 100만개이다.
이름 열에는 중복 없는 데이터가 주어진다고 가정한다.


### 풀이 
데이터가 100만개인데 배열을 사용하고 지운값을 일일히 검사한다면? -> 만약 U, D에 주어지는 X가 크다면 시간이 오래걸린다.

LinkedList를 사용하면 제거, 삽입에 시간이 얼마 안걸린다. 그리고 U,D명령어 실행시에 O(1)접근이 가능하다.
C하면 Stack에 최근에 제거한 값들과 인덱스를 담는다. -> Z하면 되돌리기

문제는 맨처음 원본을 기준으로 OX표시를 해야한다. LinkedList로 제거를 한다면 원본일때에 인덱스를 알 수가 없다.
LinkedList에 행의 맨 처음 index를 같이 담자.


### 실패
효율성이 왜 실패할까?

>> LinkedList에서 삽입, 삭제시에 결국 해당 값까지 타고가서 찾아야하기떄문에 O(N)의 시간이 걸릴 수 있다.

방법 1. Node를 직접 구현하고, HashMap 직접 접근?
방법 2. 애초에 필요한 부분만 골라서 풀기
2채택
*/