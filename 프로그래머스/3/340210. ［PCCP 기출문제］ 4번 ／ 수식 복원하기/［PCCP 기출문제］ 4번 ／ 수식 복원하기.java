import java.util.*;

class Solution {
    
    private static final String SPACE = " ";
    private static final String EQUAL = "=";
    private static final String UNCERTAIN = "?";
    private static final int NOT_FOUND = -1;
    
    private boolean[] isImpossible = new boolean[10];
    
    private List<Expression> answer = new ArrayList<>(); 
    
    public String[] solution(String[] expressions) {
        List<Expression> expressionsOfX = new ArrayList<>();
        boolean isSystemAlreadyFound = false;
        int numericSystem = 0;
        
        // 1. 초기화, 진법 추론
        for (String expression : expressions) {
            String[] splited = expression.split(" ");
            int a = Integer.parseInt(splited[0]);    
            String sign = splited[1];    
            int b = Integer.parseInt(splited[2]);    
            String result = splited[4];
            
            // 각 자릿수 값보다 작거나 같은 진법은 사용 불가능
            for (int i = 2; i <= 9; i++) {
                if (!checkDigits(a, i) || !checkDigits(b, i)) isImpossible[i] = true;    
            }
                
            if (result.charAt(0) == 'X') {
                expressionsOfX.add(new Expression(a, b, sign, result));
                continue;
            }  
            
            if (isSystemAlreadyFound) continue;
            
            // 1의 자릿수로 진법 추론하기    
            int system = findNumericSystem(a, b, Integer.parseInt(result), sign);
            if (system != NOT_FOUND) {
                isSystemAlreadyFound = true;
                numericSystem = system;
                continue;
            }
            
            // X없는 수식으로 가능한 진법 확인
            checkEnableNumericSystem(new Expression(a, b, sign, result));
        }
        
        // 2.1. 진법을 알아냈다면, X만 종합해서 결과에 도출하면 됨.
        if (isSystemAlreadyFound) {
            for (Expression expression : expressionsOfX) {
                expression.setResultBy(numericSystem);
                answer.add(expression);
            }
            
            return answer.stream().map(Expression::toString).toArray(String[]::new);
        }
        
        // 2.2. 진법을 모른다면, 이전 정보로 최대한 추론한다.
        for (Expression e : expressionsOfX) {
            Set<Integer> set = new HashSet<>();
            for (int i = 2; i <= 9; i++) {
                if (!isImpossible[i]) {
                    int result = e.calculateBy(i);
                    set.add(result);
                }
            }
            
            if (set.size() == 1) {
                for (int result : set) {
                    answer.add(new Expression(e.operand1, e.operand2, e.sign, result+""));
                }
            } else {
                answer.add(new Expression(e.operand1, e.operand2, e.sign, UNCERTAIN));
            }
        }
        
        return answer.stream().map(Expression::toString).toArray(String[]::new);
    }
                    
    private int findNumericSystem(int a, int b, int result, String sign) {
        // 각 수의 1의 자리
        int a1 = (a % 10);
        int b1 = (b % 10);
        int res1 = (result % 10);
        
        if (sign.charAt(0) == '+') {
            // [+의 경우] 어느쪽도 0이 아닐때에 한해서, "앞 두 수의 1의 자리합"이 자릿수를 넘긴 경우. 즉, "결과의 1의자리"보다 "각 앞 두수의 1의 자리" 중 하나라도 큰 경우가 있다면 진법 추론 가능.
            if ((a1 != 0 && b1 != 0) && a1 >= res1 || b1 >= res1) {
                int numericSystem = a1 + b1 - res1;
            } else {
                return -1; //불가능
            }
                
        } else {
            // [-의 경우] 앞 두 수의 1의 자리 차가 음수인 경우 판별 가능
            if ((a1 - b1) < 0) {
                // 4 - 5 = -1
                // res = 7  -> 7 - (-1) -> 8진법
                return res1 - (a1 - b1);
            } else {
                return -1;
            }
        }
            
        return -1;
    }
    
    private void checkEnableNumericSystem(Expression expression) {
        for (int i = 2; i <= 9; i++) {
            if (!isImpossible[i] && !expression.isEnableNumericSystem(i)) {
                isImpossible[i] = true;
            }
        }
    }
    
    private boolean checkDigits(int num, int i) {
        while (num > 0) {
            if (num % 10 >= i) return false; 
            num /= 10;
        }
        
        return true;
    }
    
    static class Expression {
        int operand1;
        int operand2;
        String sign;
        String result;
        
        public Expression(int a, int b, String sign, String result) {
            this.operand1 = a;
            this.operand2 = b;
            this.sign = sign;
            this.result = result;
        }
        
        public String toString() {
            return operand1 + SPACE + sign + SPACE + operand2 + SPACE + EQUAL + SPACE + result;
        }
        
        // i진법이라고 가정하고 계산했을때 일치하는지 확인한다.
        public boolean isEnableNumericSystem(int numericSystem) {
            return calculateBy(numericSystem) == Integer.parseInt(result);
        }
        
        public int calculateBy(int numericSystem) {
            int o1 = convertToDecimal(operand1, numericSystem);
            int o2 = convertToDecimal(operand2, numericSystem);

            int sum = 0;

            int decimalResult = 0;
            if (sign.charAt(0) == '+') {
                decimalResult = o1 + o2;
            } else {
                decimalResult = o1 - o2;
            }

            int result = convertToNumericSystem(decimalResult, numericSystem);
            return convertToNumericSystem(decimalResult, numericSystem);
        }
        
        private int convertToDecimal(int num, int i) {
            int sum = 0;
            int digit = 1;

            while (num > 0) {
                sum += (num % 10) * Math.pow(i, digit - 1);
                num /= 10;

                digit++;
            }

            return sum;
        }
        
        private int convertToNumericSystem(int num, int numericSystem) {
            int sum = 0;
            int maxPow = (int)(Math.log10(num) / Math.log10(numericSystem));

            while (num > 0) {
                int digitVal = num / (int) Math.pow(numericSystem, maxPow);
                sum += digitVal * Math.pow(10, maxPow);
                
                num %= Math.pow(numericSystem, maxPow);
                maxPow--;
            }
            
            return sum;
        }
        
        public void setResultBy(int numericSystem) {
            this.result = calculateBy(numericSystem) +"";
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

자릿수를 이용해 확실히 확인하는 방법도 필요할 것 같다. 이것만으로 충분한가??

[+ 일때]
1의 자릿값이 더한 후 더 작아지면, 진법 추론 가능
[- 일때]
1의 자릿값이 뺀 후 더 커지면, 진법 추론 가능

*/