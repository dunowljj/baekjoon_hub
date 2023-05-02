import java.util.*;
import java.util.stream.*;

import static java.util.Comparator.*;

class Solution {
    
    static class Genre {
        // String name;
        int totalPlay;
        List<Song> songs;
        
        Genre() {
            // this.name = name;
            this.totalPlay = 0;
            this.songs = new ArrayList<>();
        }
        
        public void addSong(int no, int play) {
            songs.add(new Song(no, play));
            addTotal(play);
        }
        
        private void addTotal(int play) {
            this.totalPlay += play;
        }
        
        public int getTotalPlay() {
            return totalPlay;
        }
        
        public List<Song> getHotestSongs() {
            if (songs.isEmpty()) return new ArrayList<>();
            if (songs.size() == 1) return new ArrayList<>(songs);
            
            Collections.sort(songs);
            
            List<Song> answers = new ArrayList<>();
            answers.add(songs.get(0));
            answers.add(songs.get(1));
            
            return answers;
        }
    }
    
    static class Song implements Comparable<Song> {
        int no;
        int play;
        
        Song(int no, int play) {
            this.no = no;
            this.play = play;
        }
        
        public int compareTo(Song song) {
            if (this.play == song.play) {
                return this.no - song.no;
            }
            return song.play - this.play;
        }
        
        public int getNo() {
            return no;
        }
    }
    
    public int[] solution(String[] genres, int[] plays) {
        Map<String, Genre> genreMap = new HashMap<>(); // <genreName, Genre>
        
        for (int i = 0; i < genres.length; i++) {
            String key = genres[i];
            
            Genre genre = genreMap.getOrDefault(key, new Genre());
            genre.addSong(i, plays[i]);
            
            genreMap.put(key, genre);
        }
        
        List<Genre> genreList = new ArrayList<>(genreMap.values());
        Collections.sort(genreList, comparingInt(Genre::getTotalPlay).reversed());
        
        List<Song> answers = new ArrayList<>();
        for (Genre genre : genreList) {
            List<Song> hotSongs = genre.getHotestSongs();
            answers.addAll(hotSongs);
        }
        
        return answers.stream()
            .map(Song::getNo)
            .mapToInt(Integer::valueOf)
            .toArray();
            
    }
}
