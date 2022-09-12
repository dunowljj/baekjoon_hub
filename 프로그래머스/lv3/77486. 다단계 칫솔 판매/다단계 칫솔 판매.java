import java.util.*;

class Node {
    Node parent;
    int sum = 0;
    List<Integer> salesList = new ArrayList();
    
}

class Solution {
    
    public int[] solution(String[] enroll, String[] referral, String[] seller, int[] amount) {
        int[] result = new int[enroll.length];
        
        Map<String, Node> nodes = new HashMap();
        
        // 이름으로 노드 검색할 수 있도록 초기화
        for (int i = 0; i < enroll.length; i++) {
            nodes.put(enroll[i], new Node());
        }
        nodes.put("-", new Node());
        
        // 부모노드 연결
        for (int i = 0; i < enroll.length; i++) {
            Node node = nodes.get(enroll[i]);
            node.parent = nodes.get(referral[i]);
        }

        // 수익 저장
        for (int i = 0; i < seller.length; i++) {
            Node node = nodes.get(seller[i]);
            node.salesList.add(amount[i] * 100);
        }
        
        // 역순 순회하면서 상납처리
        for (int i = enroll.length -1; i >= 0; i--) {
            
            // 해당 이름의 노드 찾아오기
            Node node = nodes.get(enroll[i]);
            
            //수익 목록 금액들 10%씩 상납 -> 상위 노드 모두 상납처리
            for (Integer sales : node.salesList) {
                offer(node, sales);
            }

        }
        
        for (int i = 0; i < result.length; i++) {
            Node node = nodes.get(enroll[i]);
            result[i] = node.sum;
        }
        
        return result; 
    }
    
    private void offer(Node node, int sales) {
        if (node.parent == null) {
            node.sum += sales;
            return;
        }

        int tax = sales / 10;
        
        // 상납금이 1이상일때만 상납. 아니면 자신이 가지고 상납(재귀)멈춤
        if (tax >= 1) {
            node.sum += sales - tax;
            offer(node.parent, tax);
        } else {
            node.sum += sales;
        }
        
             
    }
    
    // 상납금을 그때그때 계산해서 1미만인지를 체크해야하는데, 한번에 합쳐서 계산했더니 게산이 부정확하다. 최하위노드부터 루트노드까지 발생한 이자를 바로바로 처리해야한다.
}
/*
가장 깊은 노드부터 계산해서 수익금을 올려주기
올려준 수익금 + 자신의 수익금의 10%를 위로 올린다.
추천인은 바로 상위노드를 말한다.

enroll 이름에 대해 리스트를 만들어서 referral과 비교하여 넣는다. 
자식에 부모 저장

1) 자식 노드부터 계산한 금액 부모노드로 올리기
아래부터 순회해야 -> enroll 역순으로 탐색

amount 길이 10만 -> amount값 100 * 100 = 10000 -> 최대 10억의 합

seller 중복가능? -> 중복은 동명이인과 다르다. 애초에 이름이 같은 seller가 다른 사람이면 추천인 하나를 특정할 방법이 없다.
여러 이름이 주어진다면, 각 수익금마다 상납급을 계산해야하는가?
*/