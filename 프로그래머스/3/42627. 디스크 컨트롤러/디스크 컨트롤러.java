import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;

import static java.util.Comparator.*;

class Solution {


    public int solution(int[][] jobs) {
        int totalTakenTime = 0;
        int idx = 0;

        // 요청된 시간 기준 정렬. 요청된 시간이 같다면 같다면 소요 시간이 짧은 순
        Arrays.sort(
                jobs,comparingInt((int[] j) -> j[0])
                .thenComparingInt((int[] j) -> j[1])
        );

        PriorityQueue<Job> readyQueue = new PriorityQueue<>(
                comparingInt(Job::getTakenTime)
        ); // 소요 시간이 짧은 것부터 대기시키는 큐 생성

        int currTime = jobs[0][0]; // 첫 작업 소요 시간

        while (idx < jobs.length) {

            //[[0, 6],
            // [2, 8], [3, 7],
            // [7, 1], [11, 11], [19, 25],
            // [30, 15], [32, 6], [40, 3]]

            // 작업이 요청된 시간이 현재 시간이하인 모든 경우를 ready queue에 넣는다.
            while (idx < jobs.length && jobs[idx][0] <= currTime) {
                readyQueue.offer(new Job(jobs[idx][0], jobs[idx][1]));
                idx++; // 대기큐에 넣었으므로, 다음 작업으로 넘어감
            }

            // 대기중인 작업들 중에 소요 시간이 가장 짧은 작업을 처리한다.
            // 하나의 작업만 처리한 후, 갱신된 현재 시간 기준으로 readyQueue를 갱신해야 한다.
            if (!readyQueue.isEmpty()) {
                Job job = readyQueue.poll();

                currTime += job.takenTime;
                totalTakenTime += currTime - job.requestAt;

            // 대기중인 작업이 없다면 다음 요청이 들어오는 시간으로 이동한다.
            } else {
                currTime = jobs[idx][0];
            }
        }

        // 큐에 남은 작업들 처리
        while (!readyQueue.isEmpty()) {
            Job job = readyQueue.poll();

            currTime += job.takenTime;
            totalTakenTime += currTime - job.requestAt;
        }

        return totalTakenTime / jobs.length;
    }

    static class Job {
        int requestAt;
        int takenTime;

        public Job(int requestAt, int takenTime) {
            this.requestAt = requestAt;
            this.takenTime = takenTime;
        }

        public int getRequestAt() {
            return requestAt;
        }

        public int getTakenTime() {
            return takenTime;
        }
    }
}

/**
 * 하드디스크가 작업을 수행하고 있지 않을 때에는 먼저 요청이 들어온 작업부터 처리합니다.
 * --> 100 걸리는 요청이 먼저 들어오고, 그다음 바로 2 걸리는게 들어와도 100을 실행한다.
 *
 * 1) 요청된 시간순으로 정렬하는 readyQueue 생성
 * 2) jobs를 미리 정렬해놓기.
 * 3) 현재 시간을 변수에 저장. jobs를 순회하면서 현재 시간까지 요청해온 모든 작업을 readyQueue에 넣는다.
 * 4) readyQueue는 가장 짧은 시간의 작업 처리한다. (총 소요시간 갱신, 요청-종료 시간 갱신)
 * 5) 갱신된 시간 기준으로 새로온 요청 체크, 다시 처림 반복.
 * 6) 갱신된 시간 기준으로 작업이 아예 없다면 다음 작업의 시간으로 점프한다.
 */