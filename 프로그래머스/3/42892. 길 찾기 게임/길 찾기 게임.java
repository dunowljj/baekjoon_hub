import java.util.*;
import java.util.stream.*;

class Solution {
    
    int n;
    List<Integer> pre = new ArrayList<>(); // 중 -> 전 -> 후
    List<Integer> post = new ArrayList<>(); // 전 -> 후 -> 중
    
    public int[][] solution(int[][] nodeinfo) {
        n = nodeinfo.length;
        List<Node> nodes = IntStream.range(0, n)
            .mapToObj((i) -> new Node(nodeinfo[i], i + 1))
            .sorted()  // y내림차순, x오름차순
            .collect(Collectors.toList());
        
        // for (Node node: nodes) {
        //     System.out.print(node.no+": "+ node.y +","+node.x+"  ");
        // }
        
        Node root = nodes.get(0);    
        createGraph(1, nodes, root, -1, 100_001);
        
        order(root);
        
        int[][] answer = new int[2][n];
        answer[0] = pre.stream().mapToInt(Integer::intValue).toArray();
        answer[1] = post.stream().mapToInt(Integer::intValue).toArray();
        return answer;
    }
    
    private void order(Node root) {
        if (root == null) {
            return;
        }
        
        pre.add(root.no);
        
        order(root.left);
        order(root.right);
        
        post.add(root.no);
    }
    
    private void createGraph(int idx, List<Node> nodes, Node parent, int start, int end) {
        if (idx >= nodes.size()) return;
        
        int nlIdx = findNextLevelIdx(idx, parent.y, nodes);
        if (nlIdx == -1) return; // 하위 노드가 없음
        int nly = nodes.get(nlIdx).y;
        
        for (int i = nlIdx; i < nodes.size(); i++) {
            Node now = nodes.get(i);
            int y = now.y;
            int x = now.x;
            
            if (nly == y) {
                // left
                if (start < x && x < parent.x) {
                   parent.left = nodes.get(i);
                   createGraph(idx + 1, nodes, now, start, parent.x); 
                }
                
                // right
                if (parent.x < x && x < end) {
                   parent.right = nodes.get(i);
                   createGraph(idx + 1, nodes, now, parent.x, end); 
                }
                
            } else {
                return;
            }
        }
    }
    
    private int findNextLevelIdx(int idx, int py, List<Node> nodes) {
        for (int i = idx; i < nodes.size(); i++) {
            if (nodes.get(i).y < py) {
                return i;
            }
        }
        
        return -1;
    }
    
    static class Node implements Comparable<Node> {
        int no;
        int y;
        int x;
        
        Node left;
        Node right;
        
        Node(int[] node, int no) {
            this.y = node[1];
            this.x = node[0];
            this.no = no;
        }
        
        // y내림차순, x오름차순
        @Override
        public int compareTo(Node node) {
            if (this.y == node.y) {
                return this.x - node.x;
            }
            return node.y - this.y;
        }
    }
}
/*
y값이 클수록 위에 있는 그래프 형태
가장 큰 y값이 루트노드. y기준 내림차순 정렬
y좌표가 같다 == level이 같다
x값의 위치에 따라 왼,오른쪽 서브

문제는 예시에서 처럼 한 레벨에 3개 노드가 있다면, 가운데노드가 누구 자식인지 어떻게 아는가?
부모 노드들 기준으로 갈린다. 7,4의 사이에 있으므로...
*/