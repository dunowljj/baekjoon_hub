import java.util.*;

class Solution {
    List<List<Integer>> patterns = new ArrayList<>();
    final int[] DISCOUNT_RATES = {10, 20, 30, 40};
    
    public int[] solution(int[][] users, int[] emoticons) {
        List<int[]> results = new ArrayList<>();
        int[] answer = {};
            
        // 이모티콘들에 대한 모든 할인 경우의 수 만들기.
        makeAllDiscountPattern(new ArrayList<Integer>(), emoticons.length);
        
        for (List<Integer> pattern : patterns) {
            int[] result = getResult(pattern, users, emoticons);
            results.add(result);
        }
        
        Collections.sort(results, new Comparator<int[]>(){
            @Override
            public int compare(int[] r1, int[] r2) {
                if (r1[0] == r2[0]) {
                    return r2[1] - r1[1];
                }
                
                return r2[0] - r1[0];
            }
        });
            
        return results.get(0);
    }
    
    private int[] getResult(List<Integer> pattern, int[][] users, int[] emoticons) {
        int[] result = new int[2];
        
        for (int[] user : users) {
            int targetRate = user[0];
            int targetPrice = user[1];
            int sum = 0;
            
            for (int i = 0; i < pattern.size(); i++) {
                int price = emoticons[i];
                int rate = pattern.get(i);
                
                // 이모티콘 가격 계산 후 합계에 추가
                if (rate >= targetRate) sum += price - (price * rate / 100);
            }
            
            if (sum >= targetPrice) {
                // 서비스 가입
                result[0] ++;
            } else {
                // 구매
                result[1] += sum;
            }
            
            sum = 0;
        }
        
        return result;
    }
    
    private void makeAllDiscountPattern(List<Integer> pattern, int n) {
        if (pattern.size() == n) {
            patterns.add(new ArrayList(pattern));
            return;
        }
        
        for (int i = 0; i < 4; i++) {
            int rate = DISCOUNT_RATES[i];

            pattern.add(rate);
            makeAllDiscountPattern(pattern, n);
            pattern.remove(pattern.size() - 1);
        }
    }
    
}
/*
비율 이상 할인하는 이모티콘 모두 구매
-> 할인 적용한 총 금액이 가격을 넘는지 확인
-> 넘으면 구매하지 않고 플러스 가입 / 넘지 않으면 구매

플러스 가입이 우선이다.

각 이모티콘의 할인율을 어떻게 적용시켜야 최대한 많은 가입자를 만들 수 있는가? -> 이모티콘마다 할인율이 다를 수 있다..
비율은 1~40%이다.
가격은 100~ 1_000_000이다.

이모티콘 가격은 100의 배수로 떨어진다. -> 1%단위를 계산할때 내림등을 생각할 필요 없이 깔끔한 계산이 가능하다.

- 이모티콘의 개수는 1-7개이다.
- 할인 정책의 가짓수는 4개이다.
- user의 수는 1-100개이다.

이모티콘마다 각각 다른 할인률의 경우의 수를 모두 구하고, 검증해보면 된다. 4의7제곱은 16384이다. 
7개의 이모티콘, 100개의 유저 정보가 있는데, 7개의 이모티콘을 굳이 TreeMap으로 만들 이유가 있을까? 그냥 순회하자.

int[] percentages = new int[]{10, 20, 40, 40, 30, 20, 10}

할인율 패턴 16384개에 대해 100개의 유저를 모두 검증해야한다.
패턴 한 개에 대해 100개의 유저를 탐색한 결괏값을 저장해야한다. 100개의 유저 탐색에 대한 result를 만들어서 비교해야한다.



*/