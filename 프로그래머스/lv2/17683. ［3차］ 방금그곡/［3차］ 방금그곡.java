import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

class Solution {

    class Music {
        String name;
        int playTime;
        String[] melody;
        String playedMelody;

        public Music(String name, int playTime, String melody) {
            this.name = name;
            this.playTime = playTime;
            this.melody = parseMelody(melody);
            this.playedMelody = updateMelody(playTime, this.melody);

//            System.out.println("name = " + name + ", playedMelody = " + playedMelody);
        }

        private String[] parseMelody(String melody) {
            List<String> list = new ArrayList<>();
            for (int i = 0; i < melody.length() - 1; i++) {
                char now = melody.charAt(i);
                char next = melody.charAt(i + 1);
                String mel = now + "";
                if (now == '#') continue;
                if (next == '#') mel += '#';

                list.add(mel);
            }

            char lastMel = melody.charAt(melody.length() - 1);
            if (lastMel != '#') list.add(lastMel + "");

            return list.toArray(String[]::new);
        }
        private String updateMelody(int playTime, String[] melody) {
            StringBuilder builder = new StringBuilder();
            // start 1, end 3 --> 1,2
            for (int i = 0; i < playTime; i++) {
                builder.append(melody[i % melody.length]);
            }

            return builder.toString();
        }
        public boolean isCorrectWith(String m) {
            if (playedMelody.length() < m.length()) return false;

            int index = playedMelody.indexOf(m);
            if (index == -1) return false;
            
            // 재생된 멜로디의 마지막에 멜로디가 딱 맞는 경우  -> 아래에 나올 '#' 검사하는 조건식에 인덱스 초과를 방지하기 위해
            if (index + m.length() == playedMelody.length()) return true; 
            
            // 멜로디를 포함했으나 알고보니 맨 뒤에 #을 고려하지 않은 경우
            if (playedMelody.charAt(index + m.length()) == '#') { // 마지막 음정이 #이 들어가는 경우
                this.playedMelody = playedMelody.substring(index + m.length()); // 해당 부분 삭제 후 다시 탐색
                return isCorrectWith(m);
            }

            return true;
        }
    }

    public String solution(String m, String[] musicInfos) {
        String answer = "";
        List<Music> musics = new ArrayList<>();
        for (String musicInfo : musicInfos) {
            String[] splited = musicInfo.split(",");

            int start = toMinute(splited[0]);
            int end = toMinute(splited[1]);
            String name = splited[2];
            String melody = splited[3];

            musics.add(new Music(name, end - start, melody));
        }

        int max_playTime = 0;
        for (Music music : musics) {
            if (music.isCorrectWith(m)) {
                if (max_playTime < music.playTime) { // 먼저 입력 기준이므로 < 처리
                    answer = music.name;
                    max_playTime = music.playTime;
                }
            }
        }

        return (max_playTime == 0) ? "(None)" : answer;
    }





    private int toMinute(String s) {
        String[] split = s.split(":");
        int hour = Integer.parseInt(split[0]);
        int minute = Integer.parseInt(split[1]) + hour * 60;

        return minute;
    }
}
/*
1분 1개
더 길면 처음부터 반복 재생

일치하는 음악이 여러 개일때에는 재생된 시간이 제일 긴 음악 제목 반환

1) 한 음악을 반복하는 경우 -> 끝과 처음이 어어진 부분 -> None 반환?
2) 한 음악을 중간에 끊을 경우 -> 멜로디는 곡의 일부이기 때문에 다른 곡과 착각할 수 있다는 의미같음 -> 가장 긴 곡 선택, 시간도 같다면 먼저 입력된 음악 선택

[예제 1]에서 12:00-12:14는 14분동안 재생하는 것으로 계산되었다.

분단위로 바꾸어 전체 배열을 만들면, 한 곡의 끝과 다른 곡의 시작이 엉킨 멜로디에 대한 판별이 어렵다.
*/