import java.util.*;

class FailRate implements Comparable<FailRate>{
    Double rate;
    int index;
    
    FailRate(int index, double rate) {
        this.index = index;
        this.rate = rate;
    }
    
    @Override
    public int compareTo(FailRate failRate) {
        if (this.rate == failRate.rate) {
            return this.index - failRate.index;
        }
        // 내림차순 정렬
        return Double.compare(failRate.rate, this.rate);
    }
}

class Solution {
    public int[] solution(int N, int[] stages) {
        int[] answer = null;

        // 해당 스테이지에 머무르는 유저의 수를 세기,  :: 값이 N+1까지 존재 -> N+1 == 모두 클리어한 사람
        // userCount -> 1 ~ N+1 (인덱스로 사용되는 스테이지가 1부터 주어진다.)
        int[] userCount = new int[N + 2];
        for (int i = 0; i < stages.length; i++) {
            userCount[stages[i]]++;
        }
        
        // userCount(i) -> 총 도전자 수, userCount(i+1) -> 성공한 유저 수
        for (int i = N + 1; i >= 2; i--) {
            userCount[i - 1] += userCount[i];
        }
        
        /*
        ex. {1, 3, 3 ,5} -> {12, 11, 8, 5}
        카운트 배열의 맨뒤부터 한칸씩 이동하면서 더하면, 해당 스테이지의 총 도전자 수와 통과한 사람의 수를 구할 수 있다.
        i+1 번째 스테이지를 도전한 사람은 [i]명, i+1번째 스테이지를 통과한 사람은 [i+1]명이다.
        */
        
        List<FailRate> failRates = new ArrayList();
        for (int i = 1; i < userCount.length - 1; i++) {
            int index = i;
            
            if (userCount[i] == 0 && userCount[i + 1] == 0) {
                failRates.add(new FailRate(i, 0));
                continue;
            }
            Double rate =  userCount[i] / (double) userCount[i + 1];
            
            failRates.add(new FailRate(i, rate));
        }
        
        answer = failRates.stream().sorted()
            .mapToInt((failRate) -> failRate.index).toArray();
        
        return answer;
    }
}
/*
만약 실패율이 같은 스테이지가 있다면 작은 번호의 스테이지가 먼저 오도록 하면 된다.
스테이지에 도달한 유저가 없는 경우 해당 스테이지의 실패율은 0 으로 정의한다.
*/