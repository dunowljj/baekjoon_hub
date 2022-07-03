import java.io.*;
import java.util.StringTokenizer;

public class Main {
    static int[] perm; //입력받는 배열, 타겟의 다음 순열 구해야함
    static int N;
    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        N = Integer.parseInt(br.readLine());

        perm = new int[N];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            perm[i] = Integer.parseInt(st.nextToken());
        }

        int t1 = getT1();

        if (hasNextPerm(t1)) {
            makeNextPerm(t1);

            for (int p : perm) {
                sb.append(p).append(" ");
            }
        } else {
            sb.append("-1");
        }


        bw.write(sb.toString().trim());
        bw.flush();
        bw.close();
    }

    private static void makeNextPerm(int t1) {
        t1--; //시작점
        int t2 = getT2(t1);

        swap(t1, t2);
        sortAfter(t1);
    }

    private static int getT1() {
        //증가하는 부분의 시작점 t1 구하기
        int t1 = perm.length - 1;
        while (t1 > 0 && perm[t1 - 1] > perm[t1]) {
            t1--;
        }
        return t1;
    }

    private static boolean hasNextPerm(int t1) {
        return t1 > 0;
    }

    private static int getT2(int t1) {
        //시작점과 바꿀 위치 구하기
        //맨뒤가 제일 작으므로, 작은 수부터 perm[t1]보다 큰지만 확인
        int t2 = perm.length - 1;
        while (perm[t1] > perm[t2]) {
            t2--;
        }
        return t2;
    }

    private static void sortAfter(int t1) {
        int t2;
        //t1번째 이후 값들 오름차순 정렬
        //t1다음 값과 맨뒤 값부터 대칭해서 서로 바꿔주면 된다.
        t2 = perm.length - 1;
        while (t1 + 1 < t2) {
            swap(t1 + 1,t2);
            t1++;
            t2--;
        }
    }

    private static void swap(int t1, int t2) {
        int temp = perm[t1];
        perm[t1] = perm[t2];
        perm[t2] = temp;
    }

}

/*
주어진 입력의 다음에 오는 순열 찾기

입력받은 순열에서 각각 자리마다 숫자가 증가하는지 감소하는지 분석하면, 다음 순열을 추측할 수 있다.
주어진 순열이 내림차순으로 정렬되어있다면, 그 순열은 마지막 수이다.
마찬가지로 뒤에서부터 2개의 숫자씩 끊어서 읽을 때, 숫자가 앞에서 뒤로갈수록 감소한다는 것은 해당 자릿수가 후순위임을 알려준다.
뒤에서부터 읽다가 순열이 증가하는 부분이 나타나면, 해당 부분의 시작점보다 큰 수중에 가장 낮은 수(뒤에는 어짜피 내림차순, 맨 뒤가 젤 작다)를 교환하고, 시작점보다 낮은 수들은 오름차순으로 정렬해야한다.
*/