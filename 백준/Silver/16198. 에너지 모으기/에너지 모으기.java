import java.io.*;
import java.util.StringTokenizer;

public class Main {
    static int[] beads;
    static int max = 0;
    static boolean[] visited;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int N = Integer.parseInt(br.readLine());
        beads = new int[N];
        visited = new boolean[N];

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            beads[i] = Integer.parseInt(st.nextToken());
        }


        dfs(0,0);

        bw.write(max+"");
        bw.flush();
        bw.close();
    }
    static void dfs(int depth, int sum) {
        if (depth == beads.length - 2) {
            if (max < sum) {
                max = sum;
            }
            return;
        }

        for (int i = 1; i < beads.length - 1; i++) {
            if (!visited[i]) {
                visited[i] = true;
                int energy = getEnergy(i);
                dfs(depth + 1, sum + energy);
                visited[i] = false;
            }
        }
    }

    private static int getEnergy(int i) {
        int left = i - 1;
        int right = i + 1;

        while (visited[left]) {
            left--;
        }
        while (visited[right]) {
            right++;
        }

        return beads[left] * beads[right];
    }
}
/*
# 범위
## 숫자 범위
구슬들의 합의 최댓값은 (10 - 2) * (1000 * 1000) -> 8_000_000
## 입력 범위
개수 3<=N<=10
무게 1<=W<=1000 

# 시간
최악 : 10개 -> 8개의 구슬을 뽑는 모든 순서 -> 8!

# 풀이
리스트없이 구현한다면 마지막값과 첫 값을 선택 불가하게 갱신해줘야하며, 사용한 구슬에 대해 체크도 해줘야 한다.

## 첫 값, 마지막 값 선택 불가 지정
시작점과 끝점은 영원히 선택되지 않는다. 점수를 계산하기 위한 구슬일 뿐이다.

## 사용한 구슬 처리
사용한 구슬은 boolean 배열로 체크, 문제는 사용한 구슬이 인덱스를 차지한다.

### 문제 : 백준의 첫 예제를 예시로 들면, 2번째 에너지 구슬 선택하고, 다음에 3번째 선택 시, 3번째에서 모이는 에너지가 1*4가 되어야 하는데
2는 방문체크만 되었을뿐, 배열에 남아있기 떄문에 2*4가 되어버린다. 이것까지 반영을 어떻게 할까?
### 해결 : 방문하지 않은 위치가 나올때까지 인덱스 한칸씩 옮겨 조정한다.

 */