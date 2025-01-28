import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.Stack;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());
        int answer = 0;

        if (N <= K) {
            System.out.print(answer);
            System.exit(0);
        }

        Stack<Integer> stack = new Stack<>();
        int buy = 0;
        int totalWater = N;
        int bottleCount = 0;
        while (totalWater != 0) {
            int maxPow = (int) (Math.log(totalWater) / Math.log(2));
            int maxMergedAmount = (int) Math.pow(2, maxPow);
            totalWater -= maxMergedAmount;

            stack.push(maxMergedAmount);
            bottleCount++;
//            System.out.println("maxMergedAmount = " + maxMergedAmount);
        }


        while (bottleCount > K && stack.size() > 1) {
            int smallest = stack.pop();

            // 합칠 수 있는 만큼 구매
            int nowBuy = stack.peek() - smallest;

            // 추가로 구매해서 물병 두개를 합친다.
            buy += nowBuy;
            int nextSmall = stack.pop();
            stack.push(nextSmall * 2);

            bottleCount--;
//            System.out.println("merged! = " + (nextSmall * 2));
        }

        System.out.print(buy);
    }
}
/**
 * K = 1~1000
 * N = 1~10^7
 *
 * ### 요약
 * - 초기 물병마다 1리터 들었다.
 * - K개의 물병을 옮길 수 있는데 한번만 이동할거다.
 * - 같은 양의 물병끼리 합칠 수 있다.
 * - 모자라면 1리터씩 물병 구매 가능
 *
 * 적절히 합쳐서 물이 들어있는 물병의 수를 K이하로 만들어야한다. 얼마나 물병을 사야하는가?
 *
 *
 * 초기 모두 1리터이므로, 합쳐질때 2의 제곱수단위로 합쳐진다.
 * 1+1개 -> 2L 1개, 2+2개 -> 4L 1개
 *
 *
 * 1) N에서 밑이 2인 log를 씌워서 최대한 합친 물병을 찾고, N에서 int(log)를 뺀다.
 * 2) 반복해서 합친 후 모든 물병 수 구하기
 * 3) 물병 수는 K 이하인가?
 * 4) 아니라면 물병을 구매해야 함.
 * - 가장 작은 양의 물을 가진 물병 찾기
 * - 찾은 물병 만큼 구매해서 합치기
 * - 합친 물병을 다른 물병과 합칠 수 있는지 연속적으로 확인
 * 5) 물병 수는 K 이하인가?


 정답이 없는 경우는 뭐지?
 물병을 사면 결국 무한히 합칠 수 있다.
 */