import java.io.*;
import java.util.*;


class Node {
    int left;
    int right;
    int x;

    public Node(int left, int right) {
        this.left = left;
        this.right = right;
    }

    public void setX(int x) {
        this.x = x;
    }

}

public class Main {
    static List<Node>[] adjList;
    static int column = 1, row = 0;

    static int max = 0;
    static int maxLevel = 0;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;
        int N = Integer.parseInt(br.readLine());




        // 초기화
        adjList = new ArrayList[N + 1];
        for (int i = 1; i <= N; i++) {
            adjList[i] = new ArrayList<>();
        }

        int[] parentCount = new int[N + 1];


        for (int i = 1; i <= N; i++) {
            st = new StringTokenizer(br.readLine());
            int node = Integer.parseInt(st.nextToken());
            int left = Integer.parseInt(st.nextToken());
            int right = Integer.parseInt(st.nextToken());

            adjList[node].add(new Node(left, right));
            if (left != -1) parentCount[left]++;
            if (right != -1) parentCount[right]++;
        }

        int root = 0;
        for (int i = 1; i <= N; i++) {
            if (parentCount[i] == 0) {
                root = i;
                break;
            }
        }


        int[] coordinate = new int[N + 1];
        checkByInOrder(root);
        bfs(root, 1, coordinate);

        findMaxWidth(N, coordinate);

        bw.write(maxLevel + " " + max);
        bw.flush();
        bw.close();
    }


    // 1~N x좌표 설정
    private static void checkByInOrder(int start) {
        for (Node node : adjList[start]) {

            if (node.left != -1) {
                column++;
                checkByInOrder(node.left);
           }
            node.setX(++row);
            column--;

            if (node.right != -1) {
                column++;
                checkByInOrder(node.right);
            }
        }
    }

    private static void bfs(int start, int level, int[] coordinate) {
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{start, level});

        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            start = curr[0];
            level = curr[1];

            for (Node node : adjList[start]) {
                coordinate[node.x] = level;
                if (node.left != -1)  {
                    queue.add(new int[]{node.left, level + 1});
                }
                if (node.right != -1) {
                    queue.add(new int[]{node.right, level + 1});
                }
            }
        }
    }

    private static void findMaxWidth(int N, int[] coordinate) {

        int width = 0;
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                //레벨 같은 경우만 비교
                if (coordinate[i] == coordinate[j]) {
                    // 최대값 및 최대값 보유한 레벨 갱신
                    width = Math.abs(i - j) + 1;

                    if (max == width) {
                        maxLevel = Math.min(maxLevel, coordinate[i]);
                    } else if (max < width) {
                        maxLevel = coordinate[i];
                        max = width;
                    }
                }
            }
        }
    }
}
/*
전위순회 방식을 통해 트리에서 각 노드의 x 축 좌표를 얻을 수 있다.
깊이를 구해서 y축 좌표를 얻을 수 있다.

1)중위순회를 통해 x 축 좌표를 얻어서 저장한다. 재귀하면서 인수를 넘겨받고, 해당 노드에 저장
- 인수로 증가시키면
2)bfs를 하면서 각 깊이의 너비를 탐색한다. 어떻게 탐색할지?
 */