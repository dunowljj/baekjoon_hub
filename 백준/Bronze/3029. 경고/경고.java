import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static final String SPLITTER = ":";

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String now = br.readLine();
        String target = br.readLine();

        if (now.equals(target)) {
            System.out.print("24:00:00");
            System.exit(0);
        }

        int nowSec = toSecond(now);
        int targetSec = toSecond(target);
        if (nowSec > targetSec) targetSec += 24 * 60 * 60;


        int diffSecond = targetSec - nowSec;
        System.out.print(toTimeFormat(diffSecond));
    }

    public static int toSecond(String time) {
        int totalSecond = 0;
        String[] times = time.split(SPLITTER);
        totalSecond += Integer.parseInt(times[0]) * 60 * 60;
        totalSecond += Integer.parseInt(times[1]) * 60;
        totalSecond += Integer.parseInt(times[2]);

        return totalSecond;
    }

    private static String toTimeFormat(int diffSecond) {
        StringBuilder sb = new StringBuilder();
        int hour = diffSecond / (60 * 60);
        diffSecond -= hour * 60 * 60;

        int minute = diffSecond / 60;
        int second = diffSecond % 60;

        if(hour < 10) sb.append(0);
        sb.append(hour).append(SPLITTER);

        if (minute < 10) sb.append(0);
        sb.append(minute).append(SPLITTER);

        if (second < 10) sb.append(0);
        sb.append(second);

        return sb.toString();
    }
}

/*
(정인이는 적어도 1초를 기다리며, 많아야 24시간을 기다린다.)
case 1을 보니 24시 넘길수도 있다.

한 자리 수인 경우 앞에 0 붙여줘야한다.

 */