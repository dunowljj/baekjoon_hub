import java.util.Arrays;

class Solution {
    public int solution(int n, int[] lost, int[] reserve) {



        // 여벌을 가져왔으나 도난당한 경우 -> -1로 체크해서 무시
        for (int i = 0; i < reserve.length; i++) {
            for (int j = 0; j < lost.length; j++) {
                if (reserve[i] == lost[j]) {
                    reserve[i] = -1;
                    lost[j] = -1;
                }
            }
        }

        Arrays.sort(lost);
        Arrays.sort(reserve);


        // -1 스킵
        int l = 0;
        int r = 0;
        while (l < lost.length && lost[l] == -1) l++;
        while (r <reserve.length && reserve[r] == -1) r++;

        // l = (스킵된 -1 개수)
        // 총 잃어버린 개수 - l = 체육복 대여가 필요한 인원
        int needRent = lost.length - l;
        int rented = 0;
        while (l < lost.length && r < reserve.length) {

            if (lost[l] == -1) {
                l++;
                continue;
            }

            if (reserve[r] == -1) {
                r++;
                continue;
            }

            // 앞에서부터 탐색하여 인접한 번호인 경우 체육복을 빌려준다.
            if (reserve[r] - 1 == lost[l] || reserve[r] + 1 == lost[l]) {
                l++;
                r++;
                rented ++;

            // 번호가 맞지 않아 빌려줄 수 없는 경우 더 작은 배열의 인덱스를 다음으로 넘긴다.
            } else if (lost[l] < reserve[r] - 1) {
                l++;
            } else if (lost[l] > reserve[r] + 1) {
                r++;
            }
        }

        return n - needRent + rented;
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