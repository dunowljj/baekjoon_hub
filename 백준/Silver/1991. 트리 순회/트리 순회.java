import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

class Node {
    int left;
    int right;

    public Node(int left, int right) {
        this.left = left;
        this.right = right;
    }
}

public class Main {
    static List<Node>[] adjList;
    static StringBuilder answer = new StringBuilder();
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;
        int N = Integer.parseInt(br.readLine());

        adjList = new ArrayList[N + 1];

        for (int i = 1; i <= N; i++) {
            adjList[i] = new ArrayList<>();
        }

        for (int i = 1; i <= N; i++) {
            st = new StringTokenizer(br.readLine());
            int now = st.nextToken().charAt(0) - 'A' + 1;
            int left = st.nextToken().charAt(0) - 'A' + 1;
            int right = st.nextToken().charAt(0) - 'A' + 1;

            adjList[now].add(new Node(left, right));
        }

        // '.'의 경우는 어떻게 처리? -> '.' 에서 -'A' + 1 시 -18 나옴

        preorder(1);
        answer.append("\n");
        inorder(1);
        answer.append("\n");
        postorder(1);

        bw.write(answer.toString());
        bw.flush();
        bw.close();
    }

    static void preorder(int start) {
        answer.append((char) (start + 'A' - 1));

        //루트 - 왼쪽 - 오른쪽
        for (Node node : adjList[start]) {
            if (isNode(node.left)) {
                preorder(node.left);
            }

            if (isNode(node.right)) {
                preorder(node.right);
            }
        }
    }

    static void inorder(int start) {

        //왼쪽 - 루트 - 오른쪽
        for (Node node : adjList[start]) {
            if (isNode(node.left)) {
                inorder(node.left);
            }
            answer.append((char) (start + 'A' - 1));

            if (isNode(node.right)) {
                inorder(node.right);
            }
        }
    }

    static void postorder(int start) {
        //왼쪽 - 오른쪽 - 루트
        //계속 타고 내려가서 자식 노드가 없는 것 부터 체크
        for (Node node : adjList[start]) {

            if (isNode(node.left)) {
                postorder(node.left);
            }

            if (isNode(node.right)) {
                postorder(node.right);
            }
            answer.append((char) (start + 'A' - 1));
        }
    }


    private static boolean isNode(int node) {
        return node != -18;
    }
}
