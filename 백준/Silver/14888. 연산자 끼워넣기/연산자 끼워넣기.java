import java.io.*;
import java.util.StringTokenizer;

public class Main {
    static int[] inputNums;
    static boolean[] visited;
    static int max = Integer.MIN_VALUE;
    static int min = Integer.MAX_VALUE;
    static int[] operators;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        int N = Integer.parseInt(br.readLine());

        // 숫자 입력받기
        inputNums = new int[N];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            inputNums[i] = Integer.parseInt(st.nextToken());
        }

        // 연산자 List에 입력받기
        operators = new int[N - 1];
        st = new StringTokenizer(br.readLine());

        int token = 0;
        int count = 0;
        for (int i = 0; i < 4; i++) {
            token = Integer.parseInt(st.nextToken());
            while(token-- > 0) {
                operators[count++] = i;
            }
        }
        visited = new boolean[N - 1];

        dfs(0, N - 1, inputNums[0]);

        bw.write(max+"\n"+min);
        bw.flush();
        bw.close();
    }

    static void dfs(int depth, int operatorNum, int result) {
        if (depth == operatorNum) {
            max = Math.max(max, result);
            min = Math.min(min, result);
            return;
        }

        int before = -1;
        for (int i = 0; i < operatorNum; i++) {
            int operatorValue = operators[i]; // 연산자 고유 숫자 가져오기

            // 같은 자리 같은 연산자 중복 방지
            if (!visited[i] && before != operatorValue) {
                visited[i] = true;
                before = operatorValue;

                dfs(depth + 1, operatorNum, operate(operatorValue, depth, result));
                visited[i] = false;
            }
        }
    }
    private static int operate(int operatorValue, int idx, int result) {
        int nextNum = inputNums[idx + 1];
        switch (operatorValue) {
            case 0 :
                return result + nextNum;
            case 1 :
                return result - nextNum;
            case 2 :
                return result * nextNum;
            case 3 :
                return result / nextNum;
        }
        return result;
    }
}
/*
나눗셈 : 정수 나눗셈으로 몫만 취함 -> 몫 연산으로 처리
음수 나눗셈 : 양수로 바꾸어 몫만 취하고 음수로 전달해야함 -> 삼항연산자로 처리

연산의 개수 4개 -> 수의 개수 2~11개 -> 2의 22제곱 / 중간 연산 결과의 절댓값이 모두 10억 이내 조건 -> int 사용
숫자 수 N개 -> 연산자 N-1개

 0   1   2   3
덧셈 뺄셈 곱셈 나눗셈
다음과 같이 고유 숫자를 매칭시킨다. List에 해당 연산의 개수만큼 고유 숫자를 add한다.

0123 각 수가 여러번 사용 가능할때, 중복을 배제한 수열을 뽑기 -> 연산자로 식의 결과 구하기
before을 이용해서 똑같은 연산자가 여러개일때, 중복을 제거한다.

 */