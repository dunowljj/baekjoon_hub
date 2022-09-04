import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int N = Integer.parseInt(br.readLine());
        TreeSet<Integer> set = new TreeSet<>();
        
        StringTokenizer st = new StringTokenizer(br.readLine());

        int first = Integer.parseInt(st.nextToken());
        set.add(first);

        for (int i = 1; i < N; i++) {
            int num = Integer.parseInt(st.nextToken());

            if (set.last() < num) {
                set.add(num);
                continue;
            }

            if (!set.contains(num) && set.higher(num) != null) {
                set.remove(set.higher(num));
                set.add(num);
            }
        }

        bw.write(set.size()+"");
        bw.flush();
        bw.close();
    }
}
/**
수열의 크기가 1~100만 -> 모두 탐색하면 시간 초과

길이를 탐색하므로 한정적으로 이분탐색을 사용 가능하다.

## 참고 :
@see <a href="https://st-lab.tistory.com/285">st-lab님 블로그</a>

TreeSet을 사용했다. 특정 수보다 큰 값이 있다면, 큰 값을 해당 값으로 대체해준다.
 */