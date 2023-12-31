import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static java.util.Comparator.*;

class Solution {

    private final int[] pattern1 = {1,2,3,4,5};
    private final int[] pattern2 = {2,1,2,3,2,4,2,5};
    private final int[] pattern3 = {3,3,1,1,2,2,4,4,5,5};

    public int[] solution(int[] answers) {
        List<Integer> scores = new ArrayList<>(
                List.of(
                        grade(pattern1, answers),
                        grade(pattern2, answers),
                        grade(pattern3, answers)
                )
        );

        List<Integer> answer = new ArrayList<>();
        int maxScore = findMaxScore(scores);
        for (int i = 0; i < scores.size(); i++) {
            if (maxScore == scores.get(i)) {
                answer.add(i + 1);
            }
        }

        return answer.stream()
                .mapToInt(Integer::intValue)
                .toArray();
    }

    private int grade(int[] pattern, int[] answers) {
        int score = 0;
        int pIdx = 0;
        int pLen = pattern.length;

        for (int answer : answers) {
            if (answer == pattern[pIdx++ % pLen]) {
                score++;
            }
        }
        return score;
    }

    private int findMaxScore(List<Integer> scores) {
        return scores.stream()
                .max(Comparator.naturalOrder())
                .get();
    }
}