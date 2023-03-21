import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

class Solution {

    class FileName {
        String head;
        String number;
        String tail;
        int numberVal;
        int originOrder;

        public FileName(String fileName, int index) {
            Matcher matcher = pattern.matcher(fileName);
            matcher.find();
            this.head = matcher.group(1);
            this.number = matcher.group(2);
            this.tail = matcher.group(3);
            this.numberVal = Integer.parseInt(number);
            this.originOrder = index;
        }

        public String getName() {
            return head + number + tail;
        }
    }

    static final Pattern pattern = Pattern.compile("([a-zA-Z-.\\s]+)([0-9]{1,5})(.*)");

    public String[] solution(String[] files) {
            return IntStream.range(0, files.length)
                .mapToObj((index) -> new FileName(files[index], index))
                .sorted(new Comparator<FileName>() {
                    @Override
                    public int compare(FileName o1, FileName o2) {
                        if ((o1.head).equalsIgnoreCase(o2.head)) {
                            if (o1.numberVal == o2.numberVal) {
                                return Integer.compare(o1.originOrder, o2.originOrder);
                            }
                            return Integer.compare(o1.numberVal, o2.numberVal);
                        }
                        return (o1.head).compareToIgnoreCase(o2.head);
                    }
                })
                .map(FileName::getName)
                .toArray(String[]::new);
    }
}



