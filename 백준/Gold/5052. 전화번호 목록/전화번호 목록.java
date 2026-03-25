import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class Main {

    public static final String YES = "YES";
    public static final String NO = "NO";

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int t = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        while (t-- > 0) {
            Trie phoneBook = new Trie();
            int n = Integer.parseInt(br.readLine());

            boolean enable = true;
            for (int i = 0; i < n; i++) {
                enable &= phoneBook.saveAndCheck(br.readLine());
            }

            if (enable) sb.append(YES);
            else sb.append(NO);

            sb.append(System.lineSeparator());
        }

        System.out.print(sb);
    }

    static class Trie {
        Node trie;

        public Trie() {
            trie = new Node(-1, false);
        }

        public boolean saveAndCheck(String phoneNumber) {
            Node pointer = trie;
            for (int i = 0; i < phoneNumber.length(); i++) {
                int num = phoneNumber.charAt(i) - '0';

                if (!pointer.nexts.containsKey(num)) pointer.nexts.put(num, new Node(num, false));
                pointer = pointer.nexts.get(num);

                // save 대상의 prefix 존재시 바로 중단을 위해 false 처리
                if (pointer.isEnd) return false;
            }

            // 마지막 위치 끝처리, 같은 번호는 없음
            pointer.isEnd = true;

            // save 대상이 prefix인 경우
            if (pointer.nexts.size() != 0) return false;
            else return true;
        }
    }

    static class Node {
        Map<Integer, Node> nexts;
        int num;
        boolean isEnd;

        public Node(int num, boolean isEnd) {
            this.num = num;
            this.isEnd = isEnd;
            this.nexts = new HashMap<>();
        }
    }
}
/**
 * - 트라이 대신 정렬 후, 인접요소 비교로도 풀 수 있다.
 * - 정렬을 하지 않고, 양쪽을 검사할수도 있다.
 */
