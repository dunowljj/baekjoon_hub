import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    public static final String FLOOR = "--";
    private static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        Space entry = new Space("entry", -1);

        int N = Integer.parseInt(br.readLine());
        for (int i = 0; i < N; i++) {
            Space now = entry;
            st = new StringTokenizer(br.readLine());
            int count = Integer.parseInt(st.nextToken());

            for (int depth = 0; depth < count; depth++) {
                String info = st.nextToken();

                if (!now.nexts.containsKey(info)) {
                    now.connectSpace(new Space(info, depth));
                }

                now = now.nexts.get(info);
            }
        }

        write(entry);

        System.out.print(sb.toString().trim());
    }

    private static void write(Space entry) {
        Collections.sort(entry.sortedNexts);

        for (Space next : entry.sortedNexts) {
            sb.append(FLOOR.repeat(next.depth))
                    .append(next.info)
                    .append(System.lineSeparator());

            write(next);
        }
    }

    static class Space implements Comparable<Space> {
        String info;
        int depth;
        Map<String, Space> nexts;
        List<Space> sortedNexts;

        public Space(String info, int depth) {
            this.nexts = new HashMap<>();
            this.sortedNexts = new ArrayList<>();
            this.info = info;
            this.depth = depth;
        }

        public void connectSpace(Space space) {
            nexts.put(space.info, space);
            sortedNexts.add(space);
        }

        @Override
        public int compareTo(Space o) {
            return this.info.compareTo(o.info);
        }
    }
}
/**
 * 최상위 굴을 포함하여 하나의 굴에서 개미굴이 여러개로 나뉠 때 먹이 종류별로 최대 한 번만 나올 수 있다.
 * -> 한 굴에서 퍼져나갈때, 하위 굴끼리는 겹치는 먹이 종류가 없다.
 * -> 이를 통해 중복 정보를 제거 가능하다.
 * -> 맨 처음 depth에 KIWI가 2개지만 같은 굴임을 알 수 있다.
 *
 */