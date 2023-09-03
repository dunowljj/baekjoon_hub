import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());

        Map<Long, Integer> map = new HashMap<>();
        int[] arr = new int[N];

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            int num = Integer.parseInt(st.nextToken());
            arr[i] = num;
            map.put(Long.valueOf(num), map.getOrDefault(Long.valueOf(num), 0) + 1);
        }

        int likeCount = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (i == j) continue;

                long key = (long) arr[i] - arr[j];
                if (map.containsKey(key)) {
                    int value = map.get(key);

                    // 0 두 개를 더해서 0이 나오는 경우 -> 0이 3개 이상이면 좋은 수이다.
                    if (arr[i] == 0 && arr[j] == 0) {
                        if (value > 2) {
                            likeCount++;
                            break;
                        }

                        continue;
                    }

                    // 같은 값 2개를 더해서 arr[i]를 만드는 경우 -> 해당 값이 2개 이상이면 좋은 수이다.
                    if (key == arr[j]) {
                        if (value > 1) {
                            likeCount++;
                            break;
                        }

                        continue;
                    }


                    // 특정 num과 0을 더해서 num이 나오는 경우 -> 해당 값이 2개 이상이면 좋은 수이다.
                    if (arr[j] == 0) {
                        if (value > 1) {
                            likeCount++;
                            break;
                        }

                        continue;
                    }


                    // 다른 값 2개를 더한 경우 -> 해당 키가 존재한다는 것 자체가 좋은 수를 의미한다.
                    if (key != arr[j]) {
                        likeCount++;
                        break;
                    }
                }
            }
        }

        System.out.print(likeCount);
    }
}
/**
 * 수의 위치가 다르면 값이 같아도 다른 수이다.
 * 0이 2개 있으면 모두 좋은 수가 아닌가? 1개 있으면 0을 제외한 모두 좋은 수?
 *
 */