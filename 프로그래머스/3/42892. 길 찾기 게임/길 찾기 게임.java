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
        
        Node root = nodes.get(0);    
        createGraph(nodes, root);
        findOrder(root);
        
        int[][] answer = new int[2][n];
        answer[0] = pre.stream().mapToInt(Integer::intValue).toArray();
        answer[1] = post.stream().mapToInt(Integer::intValue).toArray();
        return answer;
    }
    
    private void createGraph(List<Node> nodes, Node root) {
        for (int i = 1; i < nodes.size(); i++) {
            root.add(nodes.get(i));
            // add(root, nodes.get(i));
        }
    }
    
    private void add(Node head, Node inserted) {    
        if (inserted.x < head.x) {
            if (head.left == null) {
                head.left = inserted;
            } else {
                head = head.left;
                add(head.left, inserted);
            }
            return;
        }

        if (head.x < inserted.x) {
            if (head.right == null) {
                head.right = inserted;
            } else {
                add(head.right, inserted);
            }
            return;
        }
    }
    
    private void findOrder(Node root) {
        if (root == null) {
            return;
        }
        
        pre.add(root.no);
        
        findOrder(root.left);
        findOrder(root.right);
        
        post.add(root.no);
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
        
        public void add(Node node) {
            Node head = this;
            
            while (true) {
                if (node.x < head.x) {
                    if (head.left == null) {
                        head.left = node;
                        return;
                    } else {
                        head = head.left;
                        continue;
                    }
                }

                if (head.x < node.x) {
                    if (head.right == null) {
                        head.right = node;
                        return;
                    } else {
                        head = head.right;
                        continue;
                    }
                }
            }
        }
    }
}
/*
(y오름,x내림)정렬 -> bfs처럼 트리 모양을 탐색하게 된다.
순회를 하면서, x크기에 따라 해당 방향으로 이동하여 하나씩 삽입한다.

*/