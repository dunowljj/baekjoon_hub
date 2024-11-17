import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input = br.readLine();
        TreeNode root = new TreeNode(Integer.parseInt(input));

        while ((input = br.readLine()) != null && !input.isBlank()) {
            int value = Integer.parseInt(input);
            TreeNode node = new TreeNode(value);

            insertNode(root, node);
        }

        postorder(root);
    }

    private static void postorder(TreeNode root) {
        if (root == null) return;

        postorder(root.left);
        postorder(root.right);

        System.out.println(root.key);
    }

    private static TreeNode insertNode(TreeNode root, TreeNode node) {
        if (root == null) return node;

        if (root.key > node.key) {
            root.left = insertNode(root.left, node);
        } else {
            root.right = insertNode(root.right, node);
        }

        return root;
    }

    static class TreeNode {
        int key;
        TreeNode left;
        TreeNode right;

        public TreeNode(int key) {
            this.key = key;
        }
    }
}

/**
 * 같은 키를 가지는 노드는 없다
 */