import java.util.Arrays;

class Solution {
    public int[] solution(int[] array, int[][] commands) {
        int[] answer = new int[commands.length];

        int idx = 0;
        for (int[] command : commands) {
            int start = command[0] - 1;
            int end = command[1];
            int k = command[2] - 1;

            int[] sub = getSubArr(array, start, end);
            answer[idx++] = sub[k];
        }

        return answer;
    }

    private static int[] getSubArr(int[] array, int start, int end) {
        int subLen = end - start;
        int[] sub = new int[subLen];
        System.arraycopy(array, start, sub, 0, subLen);
        Arrays.sort(sub);
        return sub;
    }
}