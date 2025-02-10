import java.util.*;

class Solution {
    
    private static final List<char[]> operatorPriorities = List.of(
        new char[]{'+','-','*'},
        new char[]{'+','*','-'},
        new char[]{'*','+','-'},
        new char[]{'*','-','+'},
        new char[]{'-','*','+'},
        new char[]{'-','+','*'}
    );
    
    private static List<Character> allOperators = new ArrayList<>();
    private static List<Long> numbers = new ArrayList<>();
    
    public long solution(String expression) {
        initLists(expression);
        
        
        
        long answer = 0;
        for (char[] priority : operatorPriorities) {
            long result = calculate(priority);
            answer = Math.max(answer, Math.abs(result));
        }
        
        return answer;
    }
    
    private long calculate(char[] priority) {
        List<Character> operators = new LinkedList<>(allOperators);
        List<Long> results = new LinkedList<>(numbers);
    
        for (int i = 0 ; i < priority.length; i++) {
            calculateWhen(operators, results, priority[i]); 
        }
        
        return results.get(0);
    }
    
    private void calculateWhen(List<Character> operators, List<Long> results, char priorOperator) {
        for (int i = 0; i < operators.size(); i++) {
            if (results.size() == 1) return;
            
            if (operators.get(i) == priorOperator) {
                long result = operate(priorOperator, results.get(i), results.get(i + 1));
                
                results.set(i, result);
                results.remove(i + 1);
                operators.remove(i);
                i--;
            }
        }
    }
    
    
    private void initLists(String expression) {
        String num = "";
        for (int j = 0; j < expression.length(); j++) {
            char now = expression.charAt(j);

            if (j == 0 && now == '-') {
                num += '-';
                continue;
            }

            if ('0' <= now && now <= '9') {
                num += now;
                continue;
            }

            // 연산자인 경우 집어넣기. 숫자 완성시마다 연산해야하는지 확인
            else {
                allOperators.add(now);
                numbers.add(Long.parseLong(num));
                num = "";
            }
        }
        
        numbers.add(Long.parseLong(num));
    }
    
//     if (!operator.isEmpty() && operator.peek() == priority[i]) {
//                         long result = operate(operator.peek(), result.pop(), number);
//                         results.push(result);
//                     } else {
                        
//                     }
    
    private long operate(char operator, long n1, long n2) {
        if (operator == '*') {
            return n1 * n2;
        }
        
        if (operator == '+') {
            return n1 + n2;
        }
        
        if (operator == '-') {
            return n1 - n2;
        }
        
        return 0;
    }
}
/**
같은 우선순위의 연산자는 없다.
음수라면 절댓값 변환

시작이 -숫자인 경우 주의하기

Stack을 이용해 순서 보장, 연산자의 우선순위를 정해서, 각 경우에 대해 결과를 구하기.
- 스택에 숫자와 연산 넣으며 탐색하기.
-> 가장 높은 우선순위 연산이라면 계산하고 숫자만 저장
-> stack에 있는 값으로 그 다음 우선순위인 연산자 차례로 계산.



*/