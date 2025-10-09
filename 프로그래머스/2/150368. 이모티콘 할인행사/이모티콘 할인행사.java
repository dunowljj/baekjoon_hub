class Solution {
    
    public final int[] DISCOUNT_CONSTANTS = {10,20,30,40};
    
    int[] answer = {0,0};
    
    public int[] solution(int[][] users, int[] emoticons) {
        int eCount = emoticons.length;
        makeComb(0, eCount, new int[eCount], users, emoticons);
        return answer;
    }
    
    public void makeComb(int depth, int eCount, int[] discount, int[][] users, int[] emoticons) {
        if (depth == eCount) {
            Result result = calculate(discount, users, emoticons);
            
            // answer과 result 비교
            if (result.join > answer[0]) {
                answer = result.toIntArray();
            } else if (result.join == answer[0]) {
                answer[1] = Math.max(answer[1], result.income);
            }
            return;
        }
        
        for (int i = 0; i < 4; i++) {
            discount[depth] = DISCOUNT_CONSTANTS[i];
            makeComb(depth + 1, eCount, discount, users, emoticons);
        }
    }
    
    public Result calculate(int[] discount, int[][] users, int[] emoticons) {
        int totalIncome = 0;
        int totalJoin = 0;
        
        for (int[] user : users) {
            int minDiscount = user[0];
            int maxPrice = user[1];
            
            int income = 0;
            for (int i = 0; i < discount.length; i++) {
                
                if (discount[i] >= minDiscount) {
                    income += emoticons[i] * (100 - discount[i]) / 100;
                }
            }
            
            if (income >= maxPrice) {
                totalJoin++;
            } else {
                totalIncome += income;
            }
        }
        
        return new Result(totalJoin, totalIncome);
    }
    
    class Result {
        int join;
        int income;
        
        public Result(int join, int income) {
            this.join = join;
            this.income = income;
        }
        
        public int[] toIntArray() {
            return new int[]{join, income};
        }
    }
}

/**
서비스 가입자 늘리기가 우선
그 다음 판매액 늘리는 것

사용자의 구매 규칙
- 사용자는 일정 비율 이상 할인하는 이모티콘을 모두 구매한다.
- 이때 구매 비용이 일정 금액을 초과하면 구매를 하지 않고 서비스에 가입한다. 

m개의 이모티콘이 있고, 각 이모티콘은 4가지 할인방식을 채택 가능하다. 조합시 4^7 -> 2^14

+ 할인을 최대한 적게해줘야 가입자가 많아지는 듯
+ 각각의 유저가 특정 조합일때에 가지게 되는 합계를 구하고, 비교해야함



*/