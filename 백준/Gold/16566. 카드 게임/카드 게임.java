import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class Main {

    static int[] parent; // 특정 index 보다 큰 index중 사용하지 않은 값을 찾기 위한 배열
    static int[] nums;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());

        parent = new int[M + 1]; // 민수가 카드를 못내는 경우가 없다. 하지만 M-1번째 인덱스를 union할때 오류 방지를 위해 사이즈 +1

        for (int i = 0; i < parent.length; i++) {
            parent[i] = i;
        }

        nums = new int[M];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < M; i++) {
            nums[i] = Integer.parseInt(st.nextToken());
        }

        Arrays.sort(nums);

        StringBuilder sb = new StringBuilder();
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < K; i++) {
            int num = Integer.parseInt(st.nextToken());

            sb.append(findBiggerOne(num))
                    .append(System.lineSeparator());
        }

        System.out.print(sb.toString().trim());
    }

    private static int findBiggerOne(int num) {
        int index = binarySearch(num);
        int findIndex = find(index);
        union(findIndex, findIndex + 1);

        return nums[findIndex];
    }

    private static int find(int index) {
        if (parent[index] == index) return index;
        else return parent[index] = find(parent[index]);
    }

    private static void union(int i, int ni) {
        int p = find(i);
        int np = find(ni);

        if (p != np) {
            parent[p] = np;
        }
    }

    private static int binarySearch(int num) {
        int lo = 0;
        int hi = nums.length - 1;

        // F F F T T T
        // F F F F
        // T T T T
        while (lo < hi) {
            int mid = (lo + hi) / 2;

            if (num < nums[mid]) {
                hi = mid;
            } else {
                lo = mid + 1;
            }
        }

        return lo;
    }
}
