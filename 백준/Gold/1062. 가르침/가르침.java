import java.io.*;
import java.util.StringTokenizer;

public class Main {
    static int K;
    static int answer = 0;
    static int[][] before;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());


        int N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        // K가 5보다 작다면, 어짜피 아무것도 못 만든다. 기본값 0 출력
        if (K < 5) {
            bw.write(answer +"");
            bw.flush();
            bw.close();
            return;
        } else if (K == 26) {
            bw.write(N+"");
            bw.flush();
            bw.close();
            return;
        }

        int entire = 0;
        int others = 0;
        // 숫자값으로 변환, 다시 비트로 변환해서 bits[]에 저장
        int[] bits = new int[N];
        before = new int[26][N];
        for (int i = 0; i < N; i++) {
            String word = br.readLine();

            //anta, tica을 걸러내고 알파벳 숫자값으로 변환 a~z -> 0~25
            String subWord = word.substring(4, word.length() - 4);
            for (int j = 0; j < subWord.length(); j++) {
                int digit = subWord.charAt(j) - 'a';
                // 기본 5개 알파벳 아닌 경우만 저장
                if (!isEssentialAlphabet(digit)) {
                    if ((entire & 1 << digit) == 0) {
                        others++;
                    }

                    bits[i] |= (1 << digit);
                    entire |= (1 << digit);

                }
            }
        }

        dfs(5, 0, others, entire, bits);

        bw.write(answer+"");
        bw.flush();
        bw.close();
    }


    private static boolean isEssentialAlphabet(int digit) {
        // 'a','c','i','n','t' - 'a' 값
        return digit == 0 || digit == 2 || digit == 8 || digit == 13 || digit == 19;
    }

    static void dfs(int depth, int index, int others, int entire, int[] bits) {
        if (depth == K) {
            // 비트값이 없다면, 만들 수 있는 단어
            int count = 0;
            for (int bit : bits) {
                if (bit == 0) {
                    count++;
                }
            }

            if (answer < count) {
                answer = count;
            }
            return;
        }

        for (int i = index; i < 26; i++) {
            if ((entire & 1 << i) != 0) {
                for (int j = 0; j < bits.length; j++) {
                    before[i][j] = bits[j];

                    // 해당 비트만 지우기
                    bits[j] &= ~(1 << i);
                }

                dfs(depth + 1, i + 1,others, entire, bits);

                for (int j = 0; j < bits.length; j++) {
                    bits[j] = before[i][j];
                }
            }
        }

        if (others + 5 < K) {
            dfs(K, others, index, entire, bits);
        }
        // 자체를 잘못짰다. actin이 기본적으로 체크가 된 후, 탐색을 해야한다.

    }
}