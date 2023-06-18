import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());

        List<String> lines = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            lines.add(br.readLine());
        }

        Collections.sort(lines, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                int result = 0; // 순회하면서 두 문자열이 다르면 음수나 양수 return, 다르지 않으면 0이 반환.
                int i1 = 0, i2 = 0;

                /**
                 * 포인터 두 개로 순회하며 검사
                 */
                while ((i1 < o1.length() && i2 < o2.length())) {
                    char ch1 = o1.charAt(i1);
                    char ch2 = o2.charAt(i2);

                    /**
                     * 숫자 동시에 발견 시 묶음으로 비교
                     */
                    if (isNumber(ch1) && isNumber(ch2)) {
                        int start1 = i1;
                        int start2 = i2;

                        // 숫자의 끝까지 이동, 인덱스는 숫자 다음에 위치한다.
                        while (i1 < o1.length() && isNumber((ch1 = o1.charAt(i1)))) i1++;
                        while (i2 < o2.length() && isNumber((ch2 = o2.charAt(i2)))) i2++;

                        String sub1 = o1.substring(start1, i1);
                        String sub2 = o2.substring(start2, i2);

                        // String 상태에서 비교해야 한다.
                        result = compareStringNumbers(sub1, sub2);
                        if (result != 0) return result;
                    }

                    /**
                     * (두 문자 모두 숫자인 경우 위에서 이미 제외) 하나는 숫자가 등장하는데 하나는 아직 문자인 경우 -> 문자가 나온 곳이 뒤로 가야함
                     */
                    else if (isNumber(ch1)) {
                        return -1;
                    }
                    else if (isNumber(ch2)) {
                        return 1;
                    }

                    // 마지막이 숫자인 경우 비교를 마친다.
                    if (o1.length() == i1 || o2.length() == i2) break;

                    /**
                     * 대/소문자 순서 지정
                     * AaBbCc...YyZz
                     * 이전 코드에서 숫자 비교가 있었다면 숫자 다음 문자를 이용
                     * 이전 코드에서 숫자 비교가 없었다면 처음에 받은 ch를 이용
                     */
                    char up1 = Character.toUpperCase(ch1);
                    char up2 = Character.toUpperCase(ch2);

                    if (ch1 == ch2) {
                        // do nothing
                    }

                    // upper 값이 같음 -> 서로 같은 알파벳인데 upper/lower 만 다른 경우 -> 대문자 우선 (대문자가 int 값이 더 작다)
                    else if (up1 == up2) {
                        return ch1 - ch2;
                    }

                    // 서로 다른 알파벳인 경우 -> upper인 상태로 비교
                    else {
                        return Character.compare(up1, up2);
                    }

                    i1++;
                    i2++;
                }

                // 다 비교하고 한쪽만 길이가 남는다면? 어떻게 처리해야? -> 길이가 짧은 곳이 앞
                if (!(o1.length() == i1 && o2.length() == i2)) {
                    return (o1.length() - i1) - (o2.length() - i2);
                }

                return result;
            }

            // 값을 비교해야한다...
            // 자릿수가 아닌 값 자체를 비교해야하며, 숫자로 바꾸면 안됨
            //
            private int compareStringNumbers(String s1, String s2) {
                int i1 = 0, i2 = 0;
                // 맨 앞 0들 skip
                while (i1 < s1.length() && s1.charAt(i1) == '0') i1++;
                while (i2 < s2.length() && s2.charAt(i2) == '0') i2++;

                // 남은 자릿수 구하기
                int digit1 = s1.length() - i1;
                int digit2 = s2.length() - i2;

                // 1) 최대자릿수가 같은 경우 -> 문자열처럼 각 자릿수 비교
                if (digit1 == digit2) {
                    while (i1 < s1.length() && i2 < s2.length()) {
                        char ch1 = s1.charAt(i1++);
                        char ch2 = s2.charAt(i2++);

                        if (ch1 > ch2) return 1;
                        else if (ch1 < ch2) return -1;
                        else continue;
                    }
                }

                // 2) 최대자릿수가 다른 경우 -> 자릿수가 작은 수가 앞에 온다.
                else  return digit1 - digit2;

                // 값은 같은데 길이는 다르다 -> 앞에 붙는 0개수가 다르다. 0이 적은게 앞으로 온다.-> 값이 같으면 길이가 길면 더 뒤로간다.
                if (s1.length() != s2.length()) {
                    return s1.length() - s2.length();
                }

                return 0;
            }
        });

        for (String line : lines) {
            System.out.println(line);
        }
    }
    private static boolean isNumber(char ch1) {
        return '0' <= ch1 && ch1 <= '9';
    }
}

// 대소문자 무관하게 비교하되, 대문자가 우선이 되어야한다. AaBbCc
// 숫자는 묶어서 비교, 더 작은 것이 앞에
// 숫자 값이 같으면 앞에 붙은 0이 적은게 앞에 온다
// 2^63 초과 가능하다. -> 문자상태로 숫자를 비교해야한다.
