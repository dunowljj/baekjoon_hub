import java.io.*;
import java.util.*;

class Image {
    int num;
    StringBuilder operations;

    public Image(int num, StringBuilder operations) {
        this.num = num;
        this.operations = operations;
    }
}

public class Main {
    static Set<Integer> visited = new HashSet<>();
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int start = Integer.parseInt(st.nextToken());
        int end = Integer.parseInt(st.nextToken());

        if (end == 0) {
            bw.write("-");
        } else if (start == end) {
            bw.write("0");
        } else {
            bw.write(bfs(start, end));
        }

        bw.flush();
        bw.close();
    }

    static String bfs(int start, int end) {
        Queue<Image> queue = new LinkedList<>();
        queue.offer(new Image(start, new StringBuilder()));

        boolean divided = false;
        while (!queue.isEmpty()) {
            Image image = queue.poll();

            int curr = image.num;
            StringBuilder operations = image.operations;

            if (curr == end) {
                return operations.toString();
            }

            if (curr <= Math.sqrt(Integer.MAX_VALUE) && !visited.contains(curr * curr)) {
                visited.add(curr);
                queue.offer(new Image(curr * curr, new StringBuilder(operations).append("*")));
            }

            if (curr <= Integer.MAX_VALUE / 2 && !visited.contains(curr + curr)) {
                visited.add(curr);
                queue.offer(new Image(curr + curr, new StringBuilder(operations).append("+")));
            }

            // 나누기는 한 번만 해야함. 또 하면 다시 1에서 시작할 뿐이다.
            if (!divided) {
                divided = true;
                queue.offer(new Image(1, new StringBuilder(operations).append("/")));
            }
        }
        return "-1";
    }
}
/*
같은 연산 사전 순 -> *, +, -, /

범위가 1~10^9 이다. -> 연산 중에 int 범위를 벗어날 수 있다? -> 제곱하면 무조건 벗어남



### 방문체크?
특정 숫자를 방문했을때, 해답까지 남은 횟수는 같다 -> 그냥 방문 체크

### 메모리 초과
20억 * 2 = 40억 -> 4gb...
1MB = 1_000_000
512MB = 512_000_000

## 풀이
4가지 연산하는 경우 bfs
4연산
1. *2
2. 0
3. 제곱
4. 1

2번 연산 시 무슨짓을 하든간에 0이다. 0일때만 사용하는 연산
4번 연산시 1부터 시작 가능

### 문제 1 방문체크 메모리 초과
우선 방문체크 없이 구현? -> 찾지 못하는 경우 종료 조건이 없어서 무한루프가 걸린다.
-> Set을 활용해서 범위에 상관없는 방문체크를 한다.

### 문제 2 int 범위 초과
Max.Integer의 sqrt보다 혹은 /2보다 크면 제곱연산을 하지 못하게 제한하자.

### 문제 3 연산의 저장?
StringBuilder나 String으로 저장하여 큐에 넣어보자

# 다른 풀이
## 백준 1등 코드
1) HashSet으로 방문체크
2) HashMap에 <키 - 결과, 값 - 연산자와 노드값을 가진 객체> 형태로 값을 집어넣는다.
3) 정답이 구해지면, HashMap에서 역순으로 검색한다. 검색된 숫자 부분은 검색에 재사용하고, 연산자 부분을 Stack에 넣는다.
4) Stack에서 뽑아내서 정순으로 출력한다.

-> StringBuilder를 잔뜩 생성하는 것에 비해 효율이 상당히 좋아보인다. 
 */
