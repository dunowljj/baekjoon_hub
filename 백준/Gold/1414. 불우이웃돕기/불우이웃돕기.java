import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static java.util.Comparator.*;

public class Main {
    static int totalLength = 0;
    static int[] parent;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());

        parent = new int[N];
        for (int i = 0; i < parent.length; i++) parent[i] = i;

        List<LanLine> lanLines = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            String line = br.readLine();

            for (int j = 0; j < N; j++) {
                char ch = line.charAt(j);

                if (ch == '0') continue;
                int length = getLength(ch);
                totalLength += length;
                lanLines.add(new LanLine(i, j, length));
            }
        }

        Collections.sort(lanLines, comparingInt(LanLine::getLen));
        for (LanLine line : lanLines) {
            if (find(line.start) != find(line.end)) {
                union(line.start, line.end);
                totalLength -= line.getLen();
            }
        }

        // 모두 포함했는지 체크하기
        int picked = find(0);
        for (int i = 0; i < N; i++) {
            if (find(i) != picked) {
                System.out.print(-1);
                return;
            }
        }

        System.out.print(totalLength);
    }

    private static void union(int n1, int n2) {
        int p1 = find(n1);
        int p2 = find(n2);

        if (p1 != p2) {
            parent[p1] = p2;
        }
    }

    private static int find(int no) {
        if (parent[no] == no) return no;
        else return parent[no] = find(parent[no]);
    }

    private static int getLength(char ch) {
        if ('a' <= ch && ch <= 'z') return ch - 'a' + 1;
        if ('A' <= ch && ch <= 'Z') return ch - 'A' + 27;
        if ('0' <= ch && ch <= '9') return ch - '0';
        else throw new InvalidParameterException();
    }

    static class LanLine {
        int start;
        int end;
        int len;

        public LanLine(int start, int end, int len) {
            this.start = start;
            this.end = end;
            this.len = len;
        }

        public int getLen() {
            return len;
        }
    }
}
/**
 * MST 구한 후, 남는 랜선을 찾기
 *
 */