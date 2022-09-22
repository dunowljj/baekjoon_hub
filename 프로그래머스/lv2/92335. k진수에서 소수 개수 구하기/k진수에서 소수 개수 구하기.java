import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

class Solution {
    
    public int solution(int n, int k) {
        String[] nums = convert(n,k);
       
        return countPrime(nums);
    }

    public String[] convert(int n, int k) {
        StringBuilder result = new StringBuilder();
        
        while (n != 0) {
            result.append(n % k);
            n /= k;
        }
        return result.reverse().toString().split("0");
    }

    
    public int countPrime(String[] numbers) {
        return (int) Arrays.stream(numbers)
            .filter((number) -> !number.equals(""))
            .map((number) -> Long.parseLong(number))
            .filter((number) -> isPrime(number))
            .count();
    }
    
    private boolean isPrime(Long number) {
        if (number < 2) return false;
        
        for (int i = 2; i <= (int) Math.sqrt(number); i++) {
            if ((number % i) == 0) return false;
        }
        
        return true;
    }
}

/*
0P0, P0, 0P, P -> 숫자옆에 0이 있거나 아무것도 없어야 한다.
예시로 2110이라는 3진수가 나온다면, 211만 소수인지 확인하면 된다. 2, 1, 1, 21, 11은 검증할 필요가 없다.

결국 k진수로 변환하고 split('0') -> 소수 검증
-> 문제는 소수의 자릿수가 너무 클때이다. int값으로 나누기 할 수가 없다. -> (숫자 자체를 십진법을 보았을때의 소수를 구하는 문제이다.) long 타입 사용해야한다.

1<= n <= 1_000_000
2^20정도로 2진수로 나타내면 20자리이다. -> int 자릿수를 초과할 것이다. String으로 처리하기.

*/