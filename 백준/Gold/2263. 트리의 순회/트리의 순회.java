import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    public static final String SPACE = " ";
    static int[] inorder, postorder, inIndex;
    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int n = Integer.parseInt(br.readLine());

        st = new StringTokenizer(br.readLine());
        inorder = new int[n];
        inIndex = new int[n + 1];
        postorder = new int[n];
        for (int i = 0; i < n; i++) {
            inorder[i] = Integer.parseInt(st.nextToken());
            inIndex[inorder[i]] = i;
        }

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            postorder[i] = Integer.parseInt(st.nextToken());
        }

        makeTree(0, n-1, 0, n-1);
        System.out.print(sb);
    }

    private static void makeTree(int pStart, int pEnd, int iStart, int iEnd) {
        if (pStart > pEnd) return;

        int root = postorder[pEnd];
        int iRootIdx = inIndex[root];

        int lSize = iRootIdx - iStart;
        int rSize = iEnd - iRootIdx;

        sb.append(root).append(SPACE);
        makeTree(pStart,         pStart + lSize - 1,         iStart,       iRootIdx - 1);
        makeTree(pStart + lSize, pStart + lSize + rSize - 1, iRootIdx + 1, iEnd);
    }
}
