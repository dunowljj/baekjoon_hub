import javax.xml.crypto.Data;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.stream.IntStream;

public class Main {

    private static final String ANSWER_EMPTY = "FRULA";
    public static final int BOMB_NO_INDEX = -1;
    private static final int BOMB_FIRST_INDEX = 0;
    private static char BOMB_FIRST_CHAR;
    private static int BOMB_LAST_INDEX;
    private static int BOMB_BEFORE_LAST_INDEX;

    public static void main(String[] args) throws IOException {
        final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        final String given = br.readLine();

        final char[] bomb = br.readLine().toCharArray(); // 폭탄문자를 저장한 배열. 저장한 인덱스로 현재 문자와 비교할때 사용
        final Stack<Data> sequence = new Stack<>(); // 폭발문자열 체크를 위한 인덱스를 저장하는 스택

        BOMB_LAST_INDEX = bomb.length - 1;
        BOMB_BEFORE_LAST_INDEX = bomb.length - 2;
        BOMB_FIRST_CHAR = bomb[BOMB_FIRST_INDEX];

        /**
         * 1) 스택이 비어있는 경우
         * -> 폭탄 길이가 1이면 넣자마자 폭발시켜야한다.
         * -> bomb[0] 이라면 0을 push
         * -> 아니라면 해당 문자그냥 push -> 이때 문자별로 구분하려면 Stack에 객체를 넣어야 할듯?
         *
         * 2) 스택에 값이 존재하는 경우
         */
        if (bomb.length > given.length()) {
            System.out.println(given);
            System.exit(0);
        }

        if (bomb.length == 1) {
            StringBuilder sb = new StringBuilder();
            given.chars()
                    .filter(ch -> ch != BOMB_FIRST_CHAR)
                    .forEach(ch -> sb.append((char) ch));

            if (sb.length() == 0) {
                System.out.print(ANSWER_EMPTY);
            } else {
                System.out.print(sb);
            }
            System.exit(0);
        }

        for (char now : given.toCharArray()) {
            if (sequence.isEmpty()) {
               checkIsFirstAndPush(sequence, now, bomb);
               continue;
            }

            checkStackSequenceAndPush(sequence, bomb, now);
        }

        printAnswer(sequence);
    }

    /**
     * 그냥 스택에 넣기. 첫번째값 여부에 따라 isSeq 설정
     */
    private static void checkIsFirstAndPush(Stack<Data> sequence, char now, char[] bomb) {
        sequence.push(new Data(findIndex(now, bomb), now, now == BOMB_FIRST_CHAR));
    }

    /**
     * 폭발 문자열의 연속이면 해당 값을 연결상태로 스택에 넣고,
     * 폭발문자열이 완성되면 폭발문자열 길이 만큼 pop() 시킨다.
     */
    private static void checkStackSequenceAndPush(Stack<Data> sequence, char[] bomb, char now) {
        Data peeked = sequence.peek();

        /**
         * [폭발 문자열이 완성되는 경우]
         * 조건1 : 스택에서 peek 값이 연결되어 있으며 폭발 직전
         * 조건2 : 현재 문자가 마지막 폭발 문자열
         */
        if (peeked.isBeforeBombSignal() && now == bomb[BOMB_LAST_INDEX]) {
            explode(sequence);
            return;
        }

        /**
         * [문자열이 연결되는 경우]
         * - 폭발을 위한 마지막 문자는 폭발했기때문에 여기 오는 경우가 없다.
         */
        if (peeked.isSeq && now == bomb[peeked.index + 1]) {
            sequence.push(new Data(peeked.index + 1, now, true));
            return;
        }

        /**
         * [peek과 연관이 없음]
         * 상위 메서드에 continue를 사용해서 로직들을 분리하기 위해 메서드 중복사용
         */
        checkIsFirstAndPush(sequence, now, bomb);
    }

    private static void explode(Stack<Data> stack) {
        IntStream.range(BOMB_FIRST_INDEX, BOMB_LAST_INDEX)
                .forEach(i -> stack.pop());
    }

    private static int findIndex(char now, char[] bomb) {
        return IntStream.range(BOMB_FIRST_INDEX, BOMB_LAST_INDEX + 1)
                .filter((i) -> bomb[i] == now)
                .findFirst()
                .orElse(BOMB_NO_INDEX);
    }

    private static void printAnswer(Stack<Data> sequence) {
        if (sequence.isEmpty()) {
            System.out.print(ANSWER_EMPTY);
            return;
        }

        StringBuilder sb = new StringBuilder();
        sequence.stream()
                .map(data -> data.ch)
                .forEach(ch -> sb.append(ch));

        System.out.print(sb);
    }

    static class Data {

        int index;
        char ch;
        boolean isSeq;

        public Data(int index, char ch, boolean isSeq) {
            this.index = index;
            this.ch = ch;
            this.isSeq = isSeq;
        }

        public boolean isBeforeBombSignal() {
            return isSeq && index == BOMB_BEFORE_LAST_INDEX;
        }
    }
}
/**
 * 폭발 문자열 1~36길이
 * Character 100만개가 Stack에 쌓인다면? ->  64bit 15MB
 *
 * 1) Stack에 bomb의 인덱스만 집어넣는다. 인덱스 + 1로 접근한 문자와 현재 문자가 같은지 검사.
 * 2) 스택에 넣으려는 값이 bomb의 (마지막 인덱스-1)번째 요소라면 폭발시킨다.
 * 3) 폭발은 스택에서 해당 값을 모두 빼버리는 것을 말한다.
 * 4) 마지막에 역순으로 pop하기. 그냥 deque를 쓰는게 나을까? 성능상 어떤 차이가 있을지
 * 5) 스택이 비어있는 경우, 길이가 짧은 경우, 마지막 요소인 경우
 *
 * ### 시간 초과
 * Stream<Character>와 함께 sout를 바로 써서 시간초과 발생
 *
 * ### 문제 잘못 읽음
 * 폭발 문자열은 같은 문자를 두 개 이상 포함하지 않는다. 라는 조건이 있었는데 중복까지 고려해버렸다.
 * 즉, 복잡하게 인덱스를 포함한 검사를 할 필요가 없다.
 */