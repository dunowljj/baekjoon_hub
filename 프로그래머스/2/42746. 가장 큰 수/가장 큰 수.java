import java.util.Arrays;

class Solution {
    public String solution(int[] numbers) {
        
        StringBuilder sb = new StringBuilder();
        Arrays.stream(numbers)
                .mapToObj(String::valueOf)
                .sorted((o1, o2) -> (o2+o1).compareTo(o1+o2))
                .map(String::valueOf)
                .forEach((s) -> sb.append(s));

       if (sb.charAt(0) == '0') return "0";
       else return sb.toString();
    }
}

// 6, 62, 67, 600, 60
// 60 06
// 67 6 --> 676 667

/**
 * 두 문자열을 합친 것을 사전순으로 비교해서 내림차순으로 정렬
 * 
 * 내림차순 정렬이므로 0으로 시작하는 값이 있다면 뒤에 오는 값들도 모두 0이다.
 * 0은 연결시켜도 1개의 0을 반환한다.
 */