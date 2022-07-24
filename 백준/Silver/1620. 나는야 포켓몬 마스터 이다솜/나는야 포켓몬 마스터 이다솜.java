import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        String[] pokeBook = new String[N + 1];
        Map<String, Integer> map = new HashMap<>();

        for (int i = 1; i <= N; i++) {
            pokeBook[i] = br.readLine();
            map.put(pokeBook[i], i);
        }

        StringBuilder answer = new StringBuilder();
        for (int i = 0; i < M; i++) {
            String input = br.readLine();
            if (48 <= input.charAt(0) && input.charAt(0) <= 57) {
                answer.append(pokeBook[Integer.parseInt(input)]).append("\n");
            } else {
                answer.append(map.get(input)).append("\n");
            }
        }

        bw.write(answer.toString().trim());
        bw.flush();
        bw.close();
    }
}
/*
방법1 문제의 맨 앞글자 숫자인지 확인 
- 숫자이면 배열에서 이름 찾아서 반환
- 문자이면 map에서 이름으로 검색해서 번호 반환
 */