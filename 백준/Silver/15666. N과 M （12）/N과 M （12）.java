/**
 * 방법 1 : 탐색 시에 depth 간에는 값을 저장하지 않는 지역변수 before를 이용해서 배열의 중복을 제거
 */
import java.io.*;
import java.util.*;

public class Main {
    static int[] inputNums;
    static int[] arr;
    static int M;
    static int N;
    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        inputNums = new int[N];
        arr = new int[M];

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            inputNums[i] = Integer.parseInt(st.nextToken());
        }

        Arrays.sort(inputNums);

        dfs(0, 0);

        bw.write(sb.toString().trim());
        bw.flush();
        bw.close();
    }

    static void dfs(int depth, int idx) {
        if (depth == M) {
            for (int num : arr) {
                sb.append(num).append(" ");
            }
            sb.append("\n");
            return;
        }

        int before = 0;
        for (int i = idx; i < N; i++) {

            if (before != inputNums[i]) {
                arr[depth] = inputNums[i];
                before = inputNums[i];
                dfs(depth + 1, i);
            }
        }
    }
}

/**
 * 방법 2 : 미리 입력받는 수들의 중복을 제거하고, 다음 depth 탐색 시에 현재 인덱스부터 탐색하도록 구현
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class Main {

    private static final String SPACE = " ";
    private static StringBuilder result = new StringBuilder();
    private static int[] nums;
    private static int M;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        // 어짜피 dfs 탐색시에 중복해서 사용할 것이므로 미리 중복을 제거한다.
        st = new StringTokenizer(br.readLine());
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < N; i++) {
            set.add(Integer.parseInt(st.nextToken()));
        }

        nums = set.stream()
                .mapToInt(Integer::intValue)
                .sorted()
                .toArray();

        dfs(new int[M], 0, 0);

        System.out.print(result.toString().trim());
    }

    private static void dfs(int[] seq, int depth, int idx) {
        if (depth == M) {
            add(seq, result);
            return;
        }

        for (int i = idx; i < nums.length; i++) {
            seq[depth] = nums[i];
            dfs(seq, depth + 1, i);
        }
    }

    private static void add(int[] seq, StringBuilder result) {
        for (int num : seq) {
            result.append(num).append(SPACE);
        }
        result.append(System.lineSeparator());
    }
}
