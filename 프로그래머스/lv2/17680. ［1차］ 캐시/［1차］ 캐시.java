import java.util.LinkedList;
import java.util.List;

class Solution {
    int time = 0;

    public int solution(int cacheSize, String[] cities) {
        List<String> cache = new LinkedList<>();

        if (cacheSize == 0) return cities.length * 5;

        for (String city : cities) {
            calculateExecuteTime(cacheSize, cache, city.toLowerCase());
        }
        
        return time;
    }

    private void calculateExecuteTime(int cacheSize, List<String> cache, String city) {
        if (isCacheable(city, cache)) {
            cacheHit(city, cache);
        } else {
            cacheMiss(city, cache, cacheSize);
        }
    }

    private boolean isCacheable(String city, List<String> caches) {
        for (String cache : caches) {
            if (city.equals(cache)) {
                return true;
            }
        }
        return false;
    }

    private void cacheHit(String city, List<String> cache) {
        // 해당 city 최신위치로 이동
        cache.remove(city);
        cache.add(city);

        time += 1;
    }

    private void cacheMiss(String city, List<String> cache, int cacheSize) {
        // cache에 추가, 가장 오래된 요소 제거
        if (cache.size() >= cacheSize) cache.remove(0);
        cache.add(city);

        time += 5;
    }
}