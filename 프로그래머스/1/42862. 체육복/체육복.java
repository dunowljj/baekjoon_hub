import java.util.Arrays;

class Solution {
    public int solution(int n, int[] lost, int[] reserve) {
        int rented = 0;
        int[] people = new int[n + 1];
        for (int i = 0; i < reserve.length; i++) {
            people[reserve[i]] ++;

        }

        for (int i = 0; i < lost.length; i++) {
            people[lost[i]] --;
            if (people[lost[i]] == 0) rented++;
        }

        for (int i = 0; i < people.length - 1; i++) {
            if ((people[i] ^ -2) == people[i + 1]) {
                people[i] = 0;
                people[i + 1] = 0;
                i++;
                rented++;
            }
        }

        return n - lost.length + rented;
    }
}
/**
 * 1명 이상 n명 이하
 * 여벌 체육복을 가져왔으나 도난당했다면 못빌려준다.
 *
 *
 * 1, 3, 5
 * 2, 4
 */