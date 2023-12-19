import java.util.*;
import java.util.stream.*;

class Solution {
    public int[] solution(String[] genres, int[] plays) {

        // <장르 이름, 총 재생 수>
        Map<String, Long> countMap = IntStream.range(0, genres.length)
                .boxed()
                .collect(Collectors.groupingBy(
                        (i) -> genres[i],
                        Collectors.summingLong((i) -> plays[i]))
                );

        // <장르 이름, 장르(수록곡들, 총 재생수)> 
        Map<String, Genre> genreMap = new HashMap<>();
        for (int i = 0; i < genres.length; i++) {
            String genreName = genres[i];
            int playCount = plays[i];
            long totalPlay = countMap.get(genreName);

            Genre findGenre = genreMap.getOrDefault(genreName, new Genre(totalPlay));
            findGenre.addSong(new Song(i, playCount));
            genreMap.put(genreName, findGenre);
        }

        return genreMap.values().stream()
                .sorted(Comparator.comparingLong(Genre::getTotalPlay).reversed())
                .flatMap((genre) -> genre.getBestSongs().stream())
                .mapToInt(Song::getSerialNo)
                .toArray();
    }

    static class Genre {
        long totalPlay;
        List<Song> songs;

        public Genre(long totalPlay) {
            this.totalPlay = totalPlay;
            this.songs = new ArrayList<>();
        }

        public long getTotalPlay() {
            return this.totalPlay;
        }

        public void addSong(Song song) {
            this.songs.add(song);
        }

        // 기준에 맞게 정렬하여 베스트 곡 가져오기
        public List<Song> getBestSongs() {
            Collections.sort(songs,
                    Comparator.comparingInt(Song::getPlay).reversed()
                            .thenComparingInt(Song::getSerialNo)
            );

            if (songs.size() >= 2) {
                return List.of(songs.get(0), songs.get(1));
            } else {
                return List.of(songs.get(0));
            }
        }
    }

    static class Song {
        int serialNo;
        int play;

        public Song(int serialNo, int play) {
            this.serialNo = serialNo;
            this.play = play;
        }

        public int getSerialNo() {
            return this.serialNo;
        }

        public int getPlay() {
            return this.play;
        }
    }
}

/**
 - 많이 재생된 장르 먼저
 - 장르 내에서 많이 재생된 노래 먼저
 - 장르 내에서 횟수가 같은 노래 중 고유번호가 낮은 노래 먼저

 1) 장르별로 묶은 총 재생 수를 알아야 장르별로 정렬이 가능하다.
 2) 각 장르에서 많이 재생된 노래부터, 재생회수가 같다면 고유번호가 낮은 노래부터 수록한다.
 3) 각 고유번호를 기억해야한다.
 3) 장르에 곡이 1개뿐이면 하나의 곡만 선택. 장르별로 2개씩인데 총 2~4개가 될 수 있다는 의미.
 palys의 값 범위가 안나와 있다?
 모든 장르는 재생한 횟수가 다르다.

 */