import java.util.*;

class Solution {
    
    private static final String UNCERTAIN = "?";
    
    private boolean[] isImpossible = new boolean[10];
    
    private List<Expression> answer = new ArrayList<>(); 
    
    public String[] solution(String[] expressions) {
        List<Expression> expressionsOfX = new ArrayList<>();
        boolean isSystemAlreadyFound = false;
        int numericSystem = 0;
        
        // 1. 초기화, 진법 추론
        for (String expression : expressions) {
            String[] splited = expression.split(" ");
            String a = splited[0];  
            String sign = splited[1];  
            String b = splited[2];
            String result = splited[4];
            
            // 각 자릿수 값보다 작거나 같은 진법은 사용 불가능
            for (int i = 2; i <= 9; i++) {
                if (!checkDigits(a, i) || !checkDigits(b, i)) isImpossible[i] = true;    
            }
                
            if (result.charAt(0) == 'X') {
                expressionsOfX.add(new Expression(a, b, sign, result));
                continue;
            }  
            
            // X없는 수식으로 가능한 진법 확인
            checkEnableNumericSystem(a, b, sign, result);
        }
        
        for (Expression e : expressionsOfX) {
            Set<String> set = new HashSet<>();
            for (int i = 2; i <= 9; i++) {
                if (!isImpossible[i]) {
                    String result = calculate(e.operand1, e.operand2, i, e.sign);
                    set.add(result);
                }
            }
            
            if (set.size() == 1) {
                for (String result : set) {
                    answer.add(new Expression(e.operand1, e.operand2, e.sign, result));
                }
            } else {
                answer.add(new Expression(e.operand1, e.operand2, e.sign, UNCERTAIN));
            }
        }
        
        return answer.stream().map(Expression::toString).toArray(String[]::new);
    }
    
    private void checkEnableNumericSystem(String o1, String o2, String sign, String result) {
        for (int i = 2; i <= 9; i++) {
            if (isImpossible[i]) continue;
            
            if (!(calculate(o1,o2,i,sign)).equals(result)) {
                isImpossible[i] = true;
            }
        }
    }
    
    private String calculate(String o1, String o2, int i, String sign) {
        int a = Integer.parseInt(o1, i);
        int b = Integer.parseInt(o2, i);
        
        if (sign.charAt(0) == '+') {
            return Integer.toString((a + b), i);
        } else {
            return Integer.toString((a - b), i);
        }
    }
    
    private boolean checkDigits(String numStr, int i) {
        int num = Integer.parseInt(numStr);
        while (num > 0) {
            if (num % 10 >= i) return false; 
            num /= 10;
        }
        
        return true;
    }
    
    static class Expression {
        private static final String SPACE = " ";
        private static final String EQUAL = "=";
        
        String operand1;
        String operand2;
        String sign;
        String result;
        
        public Expression(String a, String b, String sign, String result) {
            this.operand1 = a;
            this.operand2 = b;
            this.sign = sign;
            this.result = result;
        }
        
        public String toString() {
            return operand1 + SPACE + sign + SPACE + operand2 + SPACE + EQUAL + SPACE + result;
        }
    }
}
/**
맨 끝자릿수의 차가 내림을 필요로 할때, 해당 자리에서 몇진수인지 추론이 가능. 외에도 윗 자리수에서 추론 가능할수도
여러 진법이 가능한 경우, 각각 결과값을 모두 넣어봐서 같으면 해당 값으로 결론 도출. 불확실한 수식은 ?를 사용한다.
결과값이 음수 또는 모순되는 수식은 주어지지 않음.
+ 혹은 - 형태,


1) X가 없는 수식들을 활용해서 진법을 추론한다.
- X가 있는 수식은 따로 분류
- 그냥 모든 진법 대입해보기. 2~9진법밖에 없다.
2) 범위가 좁혀진 진법에 대해서, 각 X수식에 대해 X의 결과를 구하기.
- 수식에 대한 결과가 모두 같으면, 정답처리, 아니면 ?처리

자릿수를 이용해 추론하는 방법이 있지만, 굳이 사용하지 않아도 되긴하다.
자릿수로 추론이 가능한 경우는 애초에 진법으로 변환해 계산해서 검증하는 것과 같은 역할을 한다.

### 자릿수 추론
[+ 일때]
1의 자릿값이 더한 후 더 작아지면, 진법 추론 가능
[- 일때]
1의 자릿값이 뺀 후 더 커지면, 진법 추론 가능

### 쉬운 변환
parseInt로 파싱할때, 진법을 기준으로 가능하다. String에서 int로 파싱할때도 마찬가지.
Integer.parseInt(value, radix);
Integer.toString(value,radix);

단, NumberFormatException을 주의해야한다. 진법보다 자릿수 값이 크면 발생한다.
*/