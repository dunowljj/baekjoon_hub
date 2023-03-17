import java.util.*;
import java.util.regex.*;
import java.util.stream.*;

class Solution {
    public static final String HEAD = "head";
    public static final String BODY = "body";
    public static final String OPEN = "<";
    public static final String CLOSE = ">";
    public static final String SLASH = "/";
    public static final String URL_FIND_REGEX = "<meta property=\"og:url\" content=\"https://(.+?)\"/>";
    public static final String EXTERNAL_URL_FIND_REGEX = "<a href=\"https://(\\S*)\"";

    public int solution(String word, String[] pages) {
        Map<String, Page> pageMap = new HashMap<>(); // <URLString, Page>
        int index = 0;
        /**
         * basicScore, linkCount 계산, url 연결된 pageMap 생성
         */
        for (String page : pages) {
            String head = findContentByTag(page, HEAD);
            String body = findContentByTag(page, BODY);

            /**
             * URL
             */
            String url = findByPattern(head, URL_FIND_REGEX);

            /**
             * externalURL
             */
            Set<String> externalURLs = new HashSet<>();
            Matcher externalURLMatcher = Pattern.compile(EXTERNAL_URL_FIND_REGEX).matcher(body);
            while (externalURLMatcher.find()) {
                String externalURL = externalURLMatcher.group(1);
                externalURLs.add(externalURL);
            }

            /**
             * basicScore 계산
             */
            double basicScore = 0.0;
            String[] words = body.split("[^a-zA-Z]");
            for (String s : words) if (s.equalsIgnoreCase(word)) basicScore++;

            pageMap.put(url, new Page(index++, basicScore, externalURLs));
        }

        /**
         * linkScore 계산
         */
        for (String key : pageMap.keySet()) {
            Page nowPage = pageMap.get(key);
            nowPage.externalURLs.stream()
                    .forEach((externalURL) -> updateLinkScore(pageMap, externalURL, nowPage.midScore));
        }

        /**
         * 매칭점수 비교
         */
        double max = 0.0;
        int answer = 0;

        List<Page> pageList = pageMap.values().stream()
                .sorted(Comparator.comparingInt(Page::getIndex))
                .collect(Collectors.toList());

        for (Page page : pageList) {
            double matchingScore = page.getMatchingScore();
            if (Double.compare(max, matchingScore) < 0) {
                max = matchingScore;
                answer = page.index;
            }
        }

        return answer;
    }

    private String findContentByTag(String page, String tag) {
        return page.substring(page.indexOf(OPEN + tag + CLOSE) + tag.length() + OPEN.length() + CLOSE.length(), page.indexOf(OPEN + SLASH + tag + CLOSE));
    }

    private String findByPattern(String page, String regex) {
        Matcher URLMatcher = Pattern.compile(regex).matcher(page);
        URLMatcher.find();
        return URLMatcher.group(1);
    }

    private void updateLinkScore(Map<String, Page> pageMap, String externalURL, double midScore) {
        if (pageMap.containsKey(externalURL)) pageMap.get(externalURL).linkScore += midScore;
    }

    class Page {
        int index;
        double basicScore;
        double midScore;
        double linkScore = 0.0;
        Set<String> externalURLs;

        public Page(int index, double basicScore, Set<String> externalURLs) {
            this.index = index;
            this.basicScore = basicScore;
            this.externalURLs = externalURLs;
            this.midScore = basicScore / externalURLs.size();
        }

        public double getMatchingScore() {
            return basicScore + linkScore;
        }

        public int getIndex() {
            return index;
        }
    }
}

/*
한바퀴돌면서 url 개수 세기

### 검색 및 기본점수
- 검색어는 단어 단위로 일치해야한다.
- 대소문자 구분이 없다.
단어 단위로 일치한다는 것은 해당 단어 주변에 알파벳이 없는 상태

### 외부 링크 수
- 다른 웹페이지로 링크가 걸린 수를 계산할때 없는 페이지에 링크된 것도 계산해야 한다.

### 링크점수
해당 웹 페이지로 링크가 걸린...
해당 웹페이지가 링크하는게 아니다.
--> (해당 페이지로 향하는 다른 웹페이지의 기본점수 / 다른 웹페이지의 외부링크 수) 들의 합
 */