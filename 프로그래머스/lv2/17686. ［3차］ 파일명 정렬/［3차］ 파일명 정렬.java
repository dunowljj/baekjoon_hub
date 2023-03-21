import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Solution {

    class FileName implements Comparable<FileName> {
        String head;
        String number;
        int numberVal;
        String tail;
        int originOrder;

        public FileName(String head, String number, String tail, int originOrder) {
            this.head = head;
            this.number = number;
            this.numberVal = Integer.parseInt(number);
            this.tail = tail;
            this.originOrder = originOrder;
        }

        public String getName() {
            return head + number + tail;
        }
        
        @Override
        public int compareTo(FileName fileName) {
            if (this.head.equalsIgnoreCase(fileName.head)) {
                if (this.numberVal == fileName.numberVal) {
                    return Integer.compare(this.originOrder, fileName.originOrder);
                }
                return Integer.compare(this.numberVal, fileName.numberVal);
            }
            return (this.head).compareToIgnoreCase(fileName.head);
        }
    }
    final Pattern pattern = Pattern.compile("([a-zA-Z.\\-\\s]+)([0-9]{1,5})(.*)");

    public String[] solution(String[] files) {
        List<FileName> fileNames = new ArrayList<>();
        int order = 0;
        for (String fileName : files) {
            Matcher matcher = pattern.matcher(fileName);

            matcher.find();
            String head = matcher.group(1);
            String number = matcher.group(2);
            String tail = matcher.group(3);

            fileNames.add(new FileName(head, number, tail, order));
        }

        return fileNames.stream()
                .sorted()
                .map(FileName::getName)
                .toArray(String[]::new);
    }
}




/*
대소문자, "", ".", "-"

HEAD 사전순 정렬 : 대소문자 구분 X
HEAD가 같을 경우 NUMBER의 숫자로 정렬 -> 앞자리 0 포함 주의
HEAD,NUMBER 모두 같으면 입력 주어진 순서 유지. .zip / .png 순서가 바뀌면 안된다.

NUMBER 부분은 없어도 되는가? -> 1-5글자라 했으니 있어야 한다. 5자 제한을 해야한다.

정규표현식 사용해서 재사용하는게 날듯

10 1
1 10
101 110

*/