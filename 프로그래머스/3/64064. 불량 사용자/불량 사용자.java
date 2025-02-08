import java.util.*;

class Solution {
    
    private static final char WILD_CARD = '*';
    
    private static final Set<Set<Integer>> combs = new HashSet<>();
    
    private static String[] userIds, bannedIds;
    private static int n,m;
    private static Set<Integer>[] mapping;
    
    public int solution(String[] user_id, String[] banned_id) {
        userIds = user_id;
        bannedIds = banned_id;
        
        n = userIds.length;
        m = bannedIds.length;
            
        mapping = new Set[n]; //  ->  [banned_id_idx] -> Set[user_id_idxs]
        for (int i = 0; i < mapping.length; i++) {
            mapping[i] = new HashSet<>();
        }
        
        for (int b_idx = 0; b_idx < m; b_idx++) {
            for (int u_idx = 0; u_idx < n; u_idx++) {
                if (match(bannedIds[b_idx], userIds[u_idx])) {
                    mapping[b_idx].add(u_idx);
                }
            }
        }
        
        // n개의 사용자 유저아이디로 m개를 뽑는 모든 조합을 만듬
        findCombs(0, n, m, new HashSet<>());
        
//         for (Set<Integer> comb : combs) {
//             for (int u_id : comb) {
//                 System.out.print(u_id + " ");
//             }
//             System.out.println();
//         }
        
        return combs.size();
    }
    
    private boolean match(String bannedId, String userId) {
        if (bannedId.length() != userId.length()) return false;
        
        for (int i = 0; i < bannedId.length(); i++) {
            if (bannedId.charAt(i) == WILD_CARD) continue;
            if (bannedId.charAt(i) != userId.charAt(i)) return false;
        }   
        
        return true;
    }
    
    
    // 오래걸리더라도 중복되는 순서 다른 경우를 모두 뽑아야 banned_id와 일치하지만 순서가 섞인 경우도 처리 가능한다.
    private void findCombs(int depth, int n, int m, Set<Integer> comb) {
        if (depth == m) {
            combs.add(new HashSet(comb));
            return;
        }
        
        for (int i = 0; i < n; i++) {
            if (!comb.contains(i) && mapping[depth].contains(i)) {
                comb.add(i);
                findCombs(depth + 1, n, m, comb);
                comb.remove((Integer) i);
            }
        }
    }
}
/**
user_id 1~8

순서가 다른 중복된 재재아이디는 제외해야함.
조합의 수를 구한다.


0. 미리 패턴에 대해 가능한 문자열을 찾아놓기 Set<String>[]  [banned_id idx] => Set = [user_idx...]
반대로 만들어야 할듯. 조합에 user_id_ idx가 있는데, 그걸로 해당 패턴 번호를 찾아야한다.

1. (유저ID길이 C bannedID길이) 모든 조합을 구하고, 대입 가능한지 확인하기. 순서만 다른 조합은 만들어서 안됨.


와일드카드로 모두 같은자릿수 8개 다되는 경우가 나온다면?
*/