import java.util.*;
import java.util.regex.*;
import java.util.stream.*;

import static java.util.Comparator.*;


class Solution {
    
    final Pattern URL_FIND_PATTERN = Pattern.compile("<meta property=\"og:url\" content=\"https://(\\S*)\"/>");
    final Pattern OUT_URL_FIND_PATTERN = Pattern.compile("<a href=\"https://(\\S*)\">");
    
    public int solution(String word, String[] pages) {
        Map<String, Page> pageMap = new HashMap<>(); // <url -> page>
        for (int i = 0; i < pages.length; i++) { 
            Page page = new Page(i, pages[i], word);
            pageMap.put(page.url, page);
        }
        
        // 링크점수 구하기
        for (Page page : pageMap.values()) {
            for (String outUrl : page.outUrls) {
                if (pageMap.containsKey(outUrl)) {
                    Page outPage = pageMap.get(outUrl);
                    outPage.linkScore += page.basicScore / page.outUrls.size();  //0처리?  
                }
            }
        }
        
        return pageMap.values().stream()
            .sorted(comparingInt(Page::getIndex))
            .max(comparingDouble(Page::getMatchScore))
            .map(Page::getIndex).get();
    }
    
    
    class Page {
        int index;
        String url;
        
        double basicScore = 0.0;
        double linkScore = 0.0;
        
        List<String> outUrls = new ArrayList<>();
        
        Page(int index, String html, String word) {
            this.index = index;
            countWordIn(html, word);
            
            final Matcher urlMatcher = URL_FIND_PATTERN.matcher(html);            
            final Matcher outUrlMatcher = OUT_URL_FIND_PATTERN.matcher(html);
    
            urlMatcher.find();
            url = urlMatcher.group(1);        
    
            while (outUrlMatcher.find()) {
                outUrls.add(outUrlMatcher.group(1));
            }
        }
        
        private void countWordIn(String html, String word) {  
            String[] words = html.split("[^a-zA-Z]");
            for (String s : words) {
                if (s.equalsIgnoreCase(word)) basicScore++;
            }
            
            // System.out.println("basicScore:"+basicScore);
        }
        
        public int getIndex() {
            return this.index;
        }
        
        public double getMatchScore() {
            return this.basicScore + this.linkScore;
        }
    }
}


/**
기본점수 : 텍스트 중, 검색어가 등장하는 횟수 (대소문자 무시)
외부링크 수: (웹페이지 -> 다른 외부 페이지) 링크 수
링크점수: (웹페이지 <- 다른 웹페이지)인 다른 웹페이지의 (기본점수/외부링크 수) 총합
매칭점수 : 기본점수+링크점수


기본점수&외부링크수를 먼저 구해야 링크점수를 구할 수 있음.
링크점수를 구하려면 특정 웹페이지를 참조하고 있는 다른 웹페이지가 누군지 알아야함. -> 페이지 정보에 링크점수를 넣고, 참조하는 웹페이지 기준으로 탐색하면 될듯

외부참조하는 링크는 url로 주어짐 -> 이를 인덱스로 변환해서 저장하거나, url로 해당 페이지를 찾을 수 있어야함.
인덱스는 순차적으로 부여되는데, 처음 페이지 탐색 시점에 다음 인덱스와 매칭되는 Url을 알기 번거롭다. url -> page 맵을 만들고, 정답도 index번호 반환이니 페이지에 인덱스 정보를 넣자.
**/