import java.util.*;

class Solution {
    
    int[][] dices;
    int[] answer;
    double maxWinRate = 0;
    
    public int[] solution(int[][] dice) {
        dices = dice;
        answer = new int[dice.length / 2];
        
        int numOfDice = dice.length;
        int combLen = numOfDice / 2;
        
        findAnswer(0, numOfDice, 0, new int[combLen]);
        
        Arrays.sort(answer);
        for (int i = 0; i < answer.length; i++) answer[i] ++; // 좌표변환
        return answer;
    }
    
    // 중복없이 주사위를 뽑는 모든 조합 10C5 -> 252
    private void findAnswer(int depth, int numOfDice, int idx, int[] comb) {
        if (depth == numOfDice / 2) {
            Result total = compare(comb);
            double winRate = total.winRate();
            
            if (maxWinRate < winRate) {
                System.arraycopy(comb, 0, answer, 0, comb.length);  
                maxWinRate = winRate;
            }
            return;
        }
        
        for (int i = idx; i < numOfDice; i++) {
            comb[depth] = i;
            findAnswer(depth + 1, numOfDice, i + 1, comb);
        }
    }
    
    private Result compare(int[] comb) {
        List<Integer> aSums = findSums(comb);
        List<Integer> bSums = findSums(opposite(comb));
        
        Collections.sort(aSums);
        Collections.sort(bSums);
        
        // [주사위comb생성 test]
        // System.out.print("comb: ");
        // printArr(comb);
        // System.out.print("opposite: ");
        // printArr(opposite(comb));
        
        Result total = new Result(0,0,0);
        for (int aSum : aSums) {
            Result result = findResultByBS(aSum, bSums);    
            total.add(result);
        }
        
        // [집계 결과 test]
        // System.out.println("win:"+total.win +", draw:"+total.draw+", lose:"+total.lose);
        // System.out.println();
         
        return total;
    }
    
    private Result findResultByBS(int aSum, List<Integer> bSums) {
        // 마지막에 A가 이김 -> A가 모두 이긴 경우
        int size = bSums.size();
        if (aSum > bSums.get(size - 1)) return new Result(bSums.size(), 0, 0);
        
        // 처음부터 A가 짐 -> A가 모두 짐
        if (aSum < bSums.get(0)) return new Result(0, 0, bSums.size());
        
        int lo = 0;
        int hi = size;
        
        // a : W W W D D D L L L
        // 첫D, 첫L 구해서 구간 구하기
        // W W W
        // D D D
        // L L L
        
        while (lo < hi) {
            int mid = (lo + hi) / 2;
            
            if (aSum <= bSums.get(mid)) {
                hi = mid;
            } else {
                lo = mid + 1;
            }
        }
        
        int drawStart = lo;
        
        lo = 0;
        hi = size;
        while (lo < hi) {
            int mid = (lo + hi) / 2;
            
             if (aSum < bSums.get(mid)) {
                hi = mid;
            } else {
                lo = mid + 1;
            }
        }
        
        int loseStart = lo;
        
        return new Result(drawStart, loseStart - drawStart, size - loseStart);
    }
    
    private List<Integer> findSums(int[] comb) {
        List<Integer> sums = new ArrayList<>();
        findSums(0, 0, comb, sums);        
        return sums;
    }
    
    private void findSums(int sum, int combIdx, int[] comb, List<Integer> sums) {
        if (combIdx == comb.length) {
            sums.add(sum);
            return;
        }
        
        for (int i = 0; i < 6; i++) {
            int diceNo = comb[combIdx];
            findSums(sum + dices[diceNo][i], combIdx + 1, comb, sums);    
        }
    }

    private int[] opposite(int[] comb) {
        int[] oppositeComb = new int[comb.length];
        int idx = 0;
        
        for (int i = 0; i < dices.length; i++) {
            boolean contains = false;
            for (int j = 0 ; j < comb.length; j++) {
                if (comb[j]  == i) {
                    contains = true;
                    break;
                }
            }
            
            if (!contains) {
                oppositeComb[idx++] = i;
            }
        }
        
        return oppositeComb;
    }
    
    static class Result {
        int win;
        int draw;
        int lose;

        public Result(int win, int draw, int lose) {
            this.win = win;
            this.draw = draw;
            this.lose = lose;
        }
        
        public void add(Result result) {
            this.win += result.win;
            this.draw += result.draw;
            this.lose += result.lose;
        }
        
        public double winRate() {
            return (double) win / (win + draw + lose);
        }
    }
    
    private void printArr(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]+" ");
        }
        System.out.println();
    }
    
    
}

/**
1~n의 번호

dice 최대 10개, 6면
숫자값 1~10

10개중 5개 뽑기 -> 10C5 = 252
서로 5개의 주사위를 굴리는 모든 경우의 수 6^5 * 6^5 =  7,776 *  7,776 -> 55_000_000 -> 5천5백만
각 결과 집합을 만들고 비교하기로 변경
- 결과집합 생성 n * 2 -> 7,776 * 2
- 각 결과 정렬하기 nlogn -> 7,777 * log7777(12.x?)
- 이분탐색으로 개수 찾기 nlogn
*/