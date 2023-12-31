import java.util.HashSet;
import java.util.Set;

class Solution {

    private Set<Integer> set;
    private boolean[] visited;
    private int count = 0;
    

    public int solution(String numbers) {
        set = new HashSet<>();
        visited = new boolean[numbers.length()];

        searchPrimeNumbers(numbers, new StringBuilder());

        return (int) set.stream()
                // .peek(System.out::println)
                .filter(this::isPrime)
                .count();
    }

    private void searchPrimeNumbers(String numbers, StringBuilder sb) {
        if (sb.length() > 0) {
            set.add(Integer.parseInt(sb.toString()));
        }

        for (int i = 0; i < numbers.length(); i++) {
            if (!visited[i]) {

                visited[i] = true;
                sb.append(numbers.charAt(i));
                searchPrimeNumbers(numbers, sb);
                visited[i] = false;
                sb.deleteCharAt(sb.length() - 1);
            }
        }
    }

    private boolean isPrime(int number) {
        if (number <= 1) return false;
        
        for (int i = 2; i*i <= number; i++) {
            if (number % i == 0) return false;
        }
        return true;
    }
}