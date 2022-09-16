import java.util.*;

class Node implements Comparable<Node> {
    String name;
    boolean visit;
    
    Node (String name) {
        this.name = name;
        this.visit = false;
    }
    
    public boolean isVisited() {
        return visit;
    }
   
    @Override
    public int compareTo(Node node) {
        return this.name.compareTo(node.name);
    }
}

class Solution {
    Map<String, ArrayList<Node>> map = new HashMap();
    String[] answer;
    int count = 1;
    boolean finished = false;
    public String[] solution(String[][] tickets) {
        final String START_AIRPORT = "ICN";
        answer = new String[tickets.length + 1];
       
        // HashMap에 <출발지, 도착지List> 형식으로 값 입력
        for (int i = 0; i < tickets.length; i++) {
            String start = tickets[i][0];
            String end = tickets[i][1];
            ArrayList<Node> list = map.getOrDefault(start, new ArrayList());
            list.add(new Node(end));
            map.put(start, list);
            
        }
        
        // 내부 리스트 오름차순 정렬
        for (int i = 0; i < tickets.length; i++) {
            ArrayList<Node> list = map.getOrDefault(tickets[i][0], new ArrayList());
            Collections.sort(list);
        }
        
        answer[0] = START_AIRPORT;
        
        search(START_AIRPORT, 1); // 첫값 미리 지정 -> depth 1부터
        
        return answer;
    }
    private void search(String start, int depth) {
        
        if (depth == answer.length) {
            finished = true;
            return;
        }
        ArrayList<Node> nodes = map.getOrDefault(start, new ArrayList());
        
        if (nodes.size() == 0) return;
        
        for (Node next : nodes) {
            if (!next.isVisited()) {
                next.visit = true;
                answer[depth] = next.name;
                search(next.name, depth + 1);
                if (finished) return;
                next.visit = false;
            }
        }
        
    }
}
/*
출발은 ICN, 알파벳 순서? -> 리스트 정렬
경로 2개 이상 -> 알파벳 순서가 앞서는 경로를 return
모든 도시를 방문할 수 없는 경우는 주어지지 않습니다 -> 정답 배열의 길이는 tickets의 길이 + 1

인접리스트 사용 -> 노드를 String으로 찾아야 해서 애매하다.
Map<String, Node> 사용 -> 검색하여 다음 이름 탐색
Node[] 만들기 -> 다음 이름을 어떻게 찾을 것인가? LinkedList처럼 Node를 변수로 두면 다음으로 바로 찾아갈 수 있긴하다. -> 중복의 경우 생성하기가 복잡하다.



*/