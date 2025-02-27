import java.util.*;
import java.util.Comparator;
import java.util.stream.*;

class Solution {
    int[] parent, childrenCount, seq;
    List<Integer>[] adj;
    
    public int[] solution(int[][] edges, int[] target) {
        int[] answer = {};
        int n = edges.length + 1; // 총 노드의 개수
        
        parent = new int[n + 1];
        childrenCount = new int[n + 1];
        seq = new int[n + 1];
        
        // 노드번호 1~n
        adj = new ArrayList[n + 1];
        for (int i = 1; i < adj.length; i++) adj[i] = new ArrayList<>();

        for (int[] edge: edges) {
            int p = edge[0];
            int c = edge[1];
            
            adj[p].add(c);
            childrenCount[p]++;
            parent[c] = p;
        }
        
        // 형제간의 중 순서 갱신
        for (int i = 1; i < adj.length; i++) {
            Collections.sort(adj[i]);
            for (int j = 0; j < adj[i].size(); j++) {
                int childNo = adj[i].get(j);
                seq[childNo] = j + 1;
            }
        }
        
        // 리프 따로 안찾아도 target에 값있는 부분이 리프다 ..
        List<Integer> leafNodes = findLeafNodes();
        
        //[target의 값 / 3 + (target % 3 == 1) ? 1 : 0]번
        int maxEntireCount = 0; // 모든 리프에 target값 이상 넣기 위한 전체 최소 삽입 횟수
        int leafWhenMax = -1;
        int needInsertWhenMax = -1;

        for (int i = 1; i <= n; i++) {
            int t = target[i - 1];
            if (t == 0) continue;            
            // target 값에 도달하기 위한 최소 삽입 회수
            int needInsert = (t / 3) + ((t % 3 == 0) ? 0 : 1);
            

            int totalInsertedCount = findTotalInsert(i, needInsert);
            
            // if (needInsert != 0) {
            //     System.out.println("val:"+i);
            //     System.out.println("par:"+parent[i]);
            //     System.out.println("needInsert:"+needInsert);
            //     System.out.println("totalInsertedCount:"+totalInsertedCount);
            //     System.out.println();
            // }
            
            
            // 최소 삽입 시 필요한 총 회수가 가장 큰 경우 -> 가장 채우는데 오래걸리는 노드 target 달성에 걸리는 총 삽입 회수
            if (maxEntireCount < totalInsertedCount) {
                maxEntireCount = totalInsertedCount;
                leafWhenMax = i;
                needInsertWhenMax = needInsert;
            }
        }
            

        // 불가능 체크
        for (int leafNo : leafNodes) {
            if (leafNo == leafWhenMax) continue;
            
            // 곁다리 노드들을 1씩만 더해서 target을 채운다고 가정할때, 
            // target을 +1만큼 초과달성한 경우 총 순회회수가 max를 넘지 않는다면, 불가능이다.
            int total = findTotalInsert(leafNo, target[leafNo - 1] + 1);
            
            if (total < maxEntireCount) {
                return new int[]{-1};
            }
        }
        
//         System.out.println("maxCount:"+ maxEntireCount);
//         System.out.println("needInsertWhenMax:"+ needInsertWhenMax);

        // 가능하다면 result 찾기
        List<Result> results = new ArrayList<>();
        for (int leafNo : leafNodes) {
            int totalInsertedCount = -1;
            int insertedCount = 0;
            while (totalInsertedCount <= maxEntireCount) {    
                // totalInsertedCount을 같이 저장하면, 정렬해서 삽입 순서를 알 수 있다.
                if (totalInsertedCount != -1) {
                    results.add(new Result(1, leafNo, totalInsertedCount)); // 1로 미리 삽입
                }
                insertedCount++;
                totalInsertedCount = findTotalInsert(leafNo, insertedCount);
            }
            
            insertedCount--;
            
            // 해당 leaf는 insertedCount번 만큼 삽입을 하게 된다.
            // insertedCount번 삽입하면서, target에 도달한다. 1씩만 삽입했다 가정하고, 남은 수는 뒤쪽부터 몰아준다.
            int remain = target[leafNo - 1] - insertedCount;
            int idx = results.size() - 1;
            while (remain >= 2) {
                remain -= 2;
                results.get(idx--).val += 2;
            }
            
            if (remain == 1) {
                results.get(idx).val += 1;
            }
        }
        
        return results.stream()
            .sorted(Comparator.comparingInt(Result::getCount))
            .mapToInt(Result::getVal)
            .toArray();
    }
    
    private List<Integer> findLeafNodes() {
        List<Integer> leafNodes = new ArrayList<>();
        findLeafNodes(1, leafNodes);
        return leafNodes;
    }
    
    private void findLeafNodes(int now, List<Integer> leafNodes) {
        if (adj[now].isEmpty()) {
            // System.out.println("leaf:"+now);
            leafNodes.add(now);
            return;
        }
        
        for (int next : adj[now]) {
            findLeafNodes(next, leafNodes);
        }
    }
    
    // f(no, n) = (a + n * parent.children.size()) * f(parentNo,n);
    // no를 가진 노드에 (insertedCount + 1)번 삽입하기 위해 필요한 총 삽입 횟수 찾기
    private int findTotalInsert(int no, int insertedCount) {
        if (no == 1) return insertedCount;
        if (childrenCount[parent[no]] == 1) return findTotalInsert(parent[no], insertedCount);
        
        int nowInsertCount = seq[no] + ((insertedCount - 1) * childrenCount[parent[no]]);
        return findTotalInsert(parent[no], nowInsertCount);
    }
    
    static class Result {
        int val;
        int leafNo;
        int count;
        
        Result(int val, int leafNo, int count) {
            this.val = val;
            this.leafNo = leafNo;
            this.count = count;
        }
        
        public int getCount() {
            return count;
        }
        
        public int getVal() {
            return val;
        }
    }
}
/**
다떨어진 후에 숫자를 넣음..

0) 그래프 생성
트리! 단방향에 자식이 여러개일 수 있다.
가장 작은 자식 노드가 초기 번호이다.

원하는 곳에 값을 떨어뜨리려면, 
자신의 부모노드가 자신을 가르킬때까지 값을 넣어야한다.

1) 점화식 만들기
문제 예시로 들면, 루트인 (1)에서 (2)에 수가 들여가려면, (3)을 한번들러야한다.
[1]번째 에 (2)에 들어가고, [1 + (n-1)*2(부모의 자식노드 개수)]번째 마다 2에 들르게 된다.
(2)에서 (5)로 가려면 [ + (n-1)*2(부모의 자식노드 개수)] * [1 + (n-1)*2(부모의 자식노드 개수)]번째마다 들른다.

결국 "노드 번호가 no"이고, "형제들 중 a번째"에 있는 노드에 "n번째로 값을 넣기 위해" 값을 넣어야할 총 횟수는
f(no, n) = (a + n * parent.children.size()) * f(parentNo,n); 요런식이 된다.

f(1,n) = 1;
f(2,n) = (1 + (n-1) * 2) * 1;    f(1, (nn = (1 + (n-1) * 2)) f(1,9)
f(3,n) = (2 + (n-1) * 2) * 1;
f(4,n) = f(2, (nn = (1 + (n-1) * 2))
f(4,n) = f(2, (nn = (2 + (n-1) * 2))




2) 탐색하여 점화식 배열(dp) 계산하기
depth 별 노드의 개수를 구하면서 탐색을 하면, 리프노드들의 f(x)를 구할 수 있게 된다. 
숫자는 1,2,3중 하나를 떨어뜨릴 수 있다.
문제는 특정 리프에 값을 넣기위해, 다른 리프에 값이 들어가게 된다.

3) max 갱신으로 총 삽입 회수 구하기
-> target 내용을 바탕으로 특정 위치에 값을 떨어뜨린다고 할때, 그곳에 넣기까지 다른곳에 값을 넣어야할 수 있다.
-> [target의 값 / 3 + (target % 3 == 1) ? 1 : 0]번 해당 리프에 값을 넣어야한다. -> n번째의 n을 구할 수 있음
-> 모든 리프에 대해서 위해서 구한 n을 사용하여 f(no, n) = [각 리프 최소 삽입 횟수]를 구하고, 그중 max를 찾으면 [모든 리프에 정해진 값 이상 넣기 위한 전체 최소 삽입 횟수]가 된다. (리프는 최대 99개)

4) 각 리프들의 삽입회수로 불가능/가능 찾기
--> 즉, [총 삽입 회수]를 구하게 되고, 그를 통해 전체 리프들이 몇 번 값을 넣게되는지 알 수 있다.

4-1) 불가능하다면 -1
--> 불가능은 어떤 경우일까? 무한이 넣을 수 있는데 불가능이 어떻게 존재할까?
--> 제일 많은 횟수를 필요로 하는 리프에 값을 넣을때, 곁다리로 1개씩 이전 다른 리프에 넣어야한다. 그러다가 다른 리프가 초과하는 경우
--> 

4-2) 가능할 경우 target에 기반하여 낮은수부터 삽입하는 경우 구하기
--> 가능한 경우에 result는 어떻게 구하지? --> 가장 작은 수부터 넣는 경우를 구해야한다. 최대한 낮은수부터 넣어야하는데,
각 target의 값에 대해 1만 넣도록하고, 남는 수에 대해 뒤부터 더해주자.


""이때, target 대로 리프 노드에 쌓인 숫자의 합을 맞추기 위해 숫자를 떨어트리는 모든 경우 중 
""가장 적은 숫자를 사용하며"" 
""사전 순으로 가장 빠른 경우""


f(no, n) = a + n * parent.children.size();

5) 이제 직접 넣되, 1씩만 넣고, 넣은 위치를 기억한다. 아니면 넣을때부터 넣을 회수를 예상해서 처리한다.? 넣는 회수는 최대값말고 모른다.


**/