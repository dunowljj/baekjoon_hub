import java.io.*;

public class Main {
    static int N;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        N = Integer.parseInt(br.readLine());
        int[] given = new int[N];
        int[] dest = new int[N];




        // 시작점 0에 저장
        String start = br.readLine();
        for (int i = 0; i < N; i++) {
            if (start.charAt(i) == '1') {
                given[i] = 1;
            }
        }

        int[] copy = new int[N];
        System.arraycopy(given, 0, copy, 0, given.length);


        // 목적지 1에 저장
        String end = br.readLine();
        for (int i = 0; i < N; i++) {
            if (end.charAt(i) == '1') {
                dest[i] = 1;
            }
        }

        // 탐색 : 스위치 안킨 경우
        int count1 = greedy(given, dest, false);

        // 첫번째 스위치 켜기 : 복사본 사용
        pressFirstSwitch(copy);

        // 재탐색 : 스위치 킨 경우
        int count2 = greedy(copy, dest, true);

        // 최솟값 도출
        int count = Math.min(count1, count2);

        // 불가능한 경우
        String answer = count == Integer.MAX_VALUE ? "-1" : count+"";

        bw.write(answer);
        bw.flush();
        bw.close();
    }

    private static int greedy(int[] given, int[] dest, boolean firstSwitchOn) {
        int count = 0;

        // 첫번째 스위치 킨 값
        if (firstSwitchOn) count++;

        // 여기서 한턴밖에 못들어감 왜?
        for (int i = 1; i < N - 1; i++) {
            if (given[i - 1] != dest[i - 1]) {
                pressSwitch(i, given);
                count++;
            }
        }

        if (given[N - 2] != dest[N - 2]) {
            pressLastSwitch(given);
            count++;
        }

        // 스위치 킨 경우 왜 for문 순회가 안되는지?
        /*
        맨 앞 스위치 킨 경우
        N -1 = 2 이므로 인덱스 1에서 for문 순회 -> 인덱스 0이 목적지와 같은지 비교 -> 다름 -> 001로 전환
        -> N-2 = 1이므로 1번째 인덱스 비교해서 다르면 마지막 스위치 누름 -> 010
        000 -> (맨앞)110 -> 001 -> 010
         */

        // 마지막이 같은 경우 -> 목적지 도달
        if (given[N - 1] == dest[N - 1]) {
            return count;
        } else {
            return Integer.MAX_VALUE;
        }

    }

    private static void pressSwitch(int i, int[] start) {
        start[i - 1] ^= 1;
        start[i] ^= 1;
        start[i + 1] ^= 1;
    }

    private static void pressLastSwitch(int[] given) {
        given[N - 1] ^= 1;
        given[N - 2] ^= 1;
    }

    private static void pressFirstSwitch(int[] given) {
        given[0] ^= 1;
        given[1] ^= 1;
    }
}
/*
양끝 전구를 누르면 바로 옆에것만 바뀐다.

전구 10만개

1) 맨 앞 전구를 키는 경우, 키지 않는 경우로 나눈다.
2) 두번째 전구의 스위치부터 컨트롤을 시작 -> 이전 전구를 기준으로 스위치를 계속 조정하면, 맨 뒤에 2개빼고 원하는 상태로 모두 조정 가능하다.
3)

맨 뒤 두개?
-> 앞선 전구들을 모두 처리하고 맨 뒤에서 2번째 전구가 0인지 1인지, 목표의 상태가 00, 11, 01, 10중 어떤 상태인지에 따라 정답 도출이 가능한지 정해진다.


N이 2인경우? 두개를 한꺼번에 키고 끄고 밖에 못한다. greedy안에 for문은 범위상 실행이 안되기 때문에 그대로 제출해도 된다.
 */