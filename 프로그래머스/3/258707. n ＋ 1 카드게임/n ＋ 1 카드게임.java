import java.util.*;

class Solution {
    public int solution(int coin, int[] cards) {
        int answer = 0;
        int n = cards.length;
        int goal = n + 1;
        int pairCount = 0;
        int round = 1;
        boolean[] isMyCard = new boolean[n + 1]; // 소유한 카드인지 확인하는 배열
        
        Set<Integer> passedCards = new HashSet<>(); // 페어가 되지 못한 지나친 카드들을 저장
        int pairCandidateCount = 0; // pair가 가능하지만 코인 2개가 필요한 pair개수
        
        // 초기 카드 뽑기
        int initialSize = n / 3;
        for (int i = 0; i < initialSize; i++) {
            int cardNo = cards[i];
            
            // 초기 카드끼리 페어인 경우 -> 페어인 카드는 저장하지 않고, 페어카운트를 올려준다.
            if (passedCards.contains(goal - cardNo)) {
                pairCount ++;
                passedCards.remove(goal - cardNo);
            
            // 페어가 아니라면 저장
            } else {
                passedCards.add(cardNo);
                isMyCard[cardNo] = true;
            }
        }
        
        // n은 6의 배수, round는 카드 2개마다 
        boolean isEndOfRound = false;
        for (int i = initialSize; i < n; i++) {
                
            isEndOfRound = (i % 2 == 1);
            int cardNo = cards[i];
            
            // #1 페어 발생 체크
            // 페어가 발생하는 경우
            if (passedCards.contains(goal - cardNo)) {
                // 동전으로 페어를 만들 수 있는지 확인
                int needCardNo = goal - cardNo;
                int needCoin = isMyCard[needCardNo] ? 1 : 2;
                 
                // 1 coin만 필요하다면 미리 구매
                if (needCoin == 1) {
                    if (needCoin <= coin) {
                        coin -= needCoin;
                        pairCount++;
                        passedCards.remove(goal - cardNo);
                    }
                } 
                // 2 coin 필요하다면, 페어 후보에 올린다. 개수만 카운팅.
                else if (needCoin == 2) {
                    pairCandidateCount++;
                    passedCards.remove(goal - cardNo);
                }
                // 구매 불가능
                else {
                    passedCards.add(cardNo);
                }
                
            // 페어가 아니라면 저장
            } else {
                passedCards.add(cardNo);
            }    
            
            // #2 라운드 종료 
            if (isEndOfRound) {
                if (pairCount > 0) {
                    pairCount--;
                    round++;
                    continue;
                } 

                // 2coin 후보중 가능한지 확인
                if (coin >= 2 && pairCandidateCount > 0) {
                    coin -= 2;
                    pairCandidateCount--;
                    round++;
                    continue;
                }

                return round;
            }
        }
        return round;
    }
}
/**
1. n/3장 뽑아 가지기. coin개 동전 소지
2. 각 라운드 2장 뽑기
- 남은 카드 없으면 종료
- 동전을 써서 가지거나, 안쓰고 벌기ㅣ (버리면 카드가 없어지나?)
3. 합이 n+1되도록 2장내고 다음 라운드

### 구현
- 추가로 뽑을 카드들끼리 페어인 경우도 있다. 
- 또한 안뽑고 보류한 카드끼리, 보류한 카드와 뽑을 카드와도 페어일수도 있다. 이를 구현해야한다.

그렇다면, 모든 카드들을 검색 가능한 자료구조에 넣되, 내가 가진 카드인지를 표시하자.
Set하나에 모든 카드값을 넣자.
1. n/3 뽑은 카드를 넣는다. (내 카드 표시)
- 한장씩 Set에 넣으며, 기존 카드와 페어가 되는지 체크한다.
- 현재 페어 수를 기록한다.

2. 2장을 뽑는다. 한장씩 뽑으며 기존 카드뭉치와 페어가 되는지 확인한다. 
- 페어가 된다면 동전을 사용한다. 동전이 없다면 못한다.
- 안된다면 Set에 넣는다.

3. 라운드마다 현재 페어 수와 라운드 갱신
- 페어가 1이상이면 페어 수를 감소시키고, 라운드를 센다.
- 페어가 0이라면 종료한다.

!!! 완성된 페어는 어디에도 저장되지 않는다. 코인을 소모하고 페어의 개수만 체크해준다.
!!! 동전을 그때그때 소모하지 않고, 진행하면서 이전의 요소중에 필요한 카드가 있으면 뽑아버리자.

도달 가능한 라운드는 뭘 말하는걸까?
실패한 라운드도 도달 가능이다.

### 문제
동전을 맨 앞부터 그때그때 소모한다면, 오히려 먼저 2개를 쓰게되어서, 후에 1개짜리 2개를 사용 못할수도 있다.
동전이 1개인 경우는 무조건 소모하고, 2개가 필요하다면 보류하는 로직이 필요하다.
- pair 발견 시, coin이 2개 필요하다면 "pair후보개수"를 +1을 한다.
- 라운드 종료 시에 pairCount가 0이라면, "pair후보개수"와 "coin수"를 확인해 페어 추가가 가능한지 확인
*/
