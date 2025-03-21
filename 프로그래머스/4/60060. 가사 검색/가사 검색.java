import java.util.*;

class Solution {
    
    private static final int MAX_LEN = 100_001;
    private static final char WILD_CARD = '?';
    
    public int[] solution(String[] words, String[] queries) {
        int[] answer = new int[queries.length];
        
        Dictionary dict = new Dictionary();
        for (String word : words) {
            dict.insert(word);
        }
        
        for (int i = 0; i < queries.length; i++) {
            answer[i] = dict.search(queries[i]);
        }

        return answer;
    }
    
    
    // 같은 길이를 가진 단어끼리 Trie를 형성한다.
    // 최상위 Trie의 count는 해당 길이를 가진 모든 단어의 수다.
    // 그 다음 Trie에 word의 첫번째 알파벳부터 주어진다.
    static class Dictionary {
        Trie[] forward;
        Trie[] reverse;
        
        Dictionary() {
            forward = new Trie[MAX_LEN];
            reverse = new Trie[MAX_LEN];
        };
        
        
        public void insert(String word) {
            int len = word.length();
            
            if (forward[len] == null) forward[len] = new Trie();
            if (reverse[len] == null) reverse[len] = new Trie();
            
            insert(word, forward[len]);
            insert(reverse(word), reverse[len]);
        }
        
        private void insert(String word, Trie nowTrie) {
            nowTrie.count++;
            
            for (int i = 0; i < word.length(); i++) {
                char nowCh = word.charAt(i);
                Trie nextTrie = nowTrie.children.getOrDefault(nowCh, new Trie());
                nextTrie.count++;
                nowTrie.children.put(nowCh, nextTrie);
                
                nowTrie = nextTrie;
            }
        }
        
        private String reverse(String query) {
            return new StringBuilder(query).reverse().toString();
        }
        
        public int search(String query) {
            int len = query.length();
            
            if (forward[len] == null) return 0;
            
            if (query.charAt(0) == WILD_CARD) return search(reverse(query), reverse[len]);
            else return search(query, forward[len]);
        }
        
        private int search(String query, Trie nowTrie) {
            for (int i = 0; i < query.length(); i++) {
                char ch = query.charAt(i);
                
                if (ch == WILD_CARD) {
                    return nowTrie.count;
                }
                
                if (!nowTrie.hasNext(ch)) {
                    return 0;
                }
                
                nowTrie = nowTrie.getNext(ch);
            }
            
            return nowTrie.count;
        }

        class Trie {
            int count;
            HashMap<Character, Trie> children;

            public Trie() {
                count = 0;
                children = new HashMap<>();
            }
            
            public boolean hasNext(char nextCh) {
                return this.children.containsKey(nextCh);
            }
            
            public Trie getNext(char nextCh) {
                return this.children.get(nextCh);
            }
        }
        
        
    }
}