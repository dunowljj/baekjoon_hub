import java.util.*;

class Solution {
    private static final int COLUMN = 0;
    private static final int GIRDER = 1;
    
    private static final int DELETE = 0;
    private static final int INSTALL = 1;
    
    private static final int FLOOR = 0;
    
    private static boolean[][] columns;
    private static boolean[][] girders;
    
    public int[][] solution(int n, int[][] build_frame) {
        List<List<Integer>> answer = new ArrayList<>();
        
        columns = new boolean[n + 1][n + 1];
        girders = new boolean[n + 1][n + 1];
        
        for (int[] info : build_frame) {
            int x = info[0];
            int y = info[1];
            int a = info[2]; // 0기둥, 1보
            int b = info[3]; // 0삭제, 1설치
            
            // 설치 -> 새로 짓는 부분이 연결되는지 확인해야한다.
            // 겹친 설치나 삭제는 주어지지지 않는다.
            if (b == INSTALL) {
                
                if (a == COLUMN) {
                    columns[x][y] = canInstall(x, y, COLUMN, n);
                    continue;
                }
                
                if (a == GIRDER) {
                    girders[x][y] = canInstall(x, y, GIRDER, n);
                    continue;
                }
            }
            
            // 삭제 -> 삭제될 부분과 연결되어있던 부분들의 연결을 확인해야한다. -> 삭제한 후 연결된 부분의 설치가능여부를 검사한다.
            if (b == DELETE) {
                
                if (a == COLUMN) {
                    // 해당 기둥을 임시 삭제
                    columns[x][y] = false;
                    boolean canDelete = true;
                    
                    // 위에 기둥을 검사
                    if (y + 1 <= n) {
                        if (columns[x][y + 1]) canDelete &= canInstall(x, y + 1, COLUMN, n);
                        
                        // 위에 있는 2개 보를 검사
                        if (x - 1 >= 0 && girders[x - 1][y + 1]) canDelete &= canInstall(x - 1, y + 1, GIRDER, n);
                        if (girders[x][y + 1]) canDelete &= canInstall(x, y + 1, GIRDER, n);
                    }
                    
                    // 문제가 없다면 삭제, 지울수 없다면 롤백
                    if (!canDelete) {
                        columns[x][y] = true;
                    }
                }
                
                if (a == GIRDER) {
                    // 해당 보를 임시 삭제
                    girders[x][y] = false;
                    boolean canDelete = true;
                    
                    // 보의 양끝 기둥을 검사
                    if (columns[x][y]) canDelete &= canInstall(x, y, COLUMN, n);

                    if (x + 1 <= n && columns[x + 1][y]) {
                        canDelete &= canInstall(x + 1, y, COLUMN, n);
                    }
                    
                    // 양 끝 보를 검사
                    if (x - 1 >= 0 && girders[x - 1][y]) canDelete &= canInstall(x - 1, y, GIRDER, n);
                    if (x + 1 <= n && girders[x + 1][y]) canDelete &= canInstall(x + 1, y, GIRDER, n);
                    
                    // 문제가 없다면 삭제, 지울수 없다면 롤백  
                    if (!canDelete) {
                        girders[x][y] = true;
                    }
                }
                
                continue;
            } 
        }

        
        // 정답 -> x좌표, y좌표, 기둥 우선  오름차순
        for (int x = 0; x < n + 1; x++) {
            for (int y = 0; y < n + 1; y++) {
                if (columns[x][y]) {
                    answer.add(Arrays.asList(x,y,COLUMN));
                }
                
                if (girders[x][y]) {
                    answer.add(Arrays.asList(x,y,GIRDER));
                }
            }
        }
        
        return answer
            .stream()
            .map(
                (list) -> list.stream().mapToInt(Integer::intValue).toArray()
            )
            // .sorted((o1, o2) -> {
            //     if (o1[0] != o2[0]) return Integer.compare(o1[0], o2[0]);
            //     if (o1[1] != o2[1]) return Integer.compare(o1[1], o2[1]);
            //     return Integer.compare(o1[2], o2[2]);
            // })
            .toArray(int[][]::new);
    }
    
    private static boolean canInstall(int x, int y, int a, int n) {
        if (a == COLUMN) {

            // 바닥인지
            if (y == FLOOR) {
                return true;
            }

            // 기둥이 : 보의 한쪽 끝 부분 위인지 확인  -> 설치하려는 기둥 아래 보의 끝이 있는가?
            if (girders[x][y] || (x - 1 >= 0 && girders[x - 1][y])) {
                return true;
            }
            
            // 기둥 위인지 확인 -> 설치하려는 기둥아래 기둥이 있는가?
            if (y - 1 >= 0 && columns[x][y - 1]) {
                return true;
            }
        }
                
        if (a == GIRDER) {

            // 보는 바닥 설치 불가
            if (y == FLOOR) {
                return false;
            }
            
            // 보의 : 한쪽 끝 부분이 기둥 위에 있는지 -> 양끝중 하나라도 받치는 기둥이 존재하는가?
            if (y - 1 >= 0 && (columns[x][y - 1] || (x + 1 <= n && columns[x + 1][y - 1]))) {
                return true;
            }

            // 보의 : 양쪽 끝 부분이 다른 보와 동시에 연결되는지 -> 양끝에 모두 보가 연결되어있는가?
            if (x - 1 >= 0 && x + 1 <= n && girders[x - 1][y] && girders[x + 1][y]) {
                return true;
            }
        }
        
        return false;
    }
    
    
}
/*
규칙을 만족하지 않는 작업을 무시해야한다.
규칙을 만족한다 -> 주변을 조건으로 탐색
 - 설치의 경우 연결이 되는지 확인해야한다.
 - 삭제의 경우 삭제 후 남은 기둥과 보들을 검사해야한다.

기둥은 수직, 보는 수평
[지정 좌표]에서 [기둥]은 [위]로, [보]는 [오른쪽]으로 건설 및 삭제

기둥 설치
- 바닥 위에 있는가? = y좌표가 0인가
- 보의 끝 위 혹은 기둥 위에 있는가?

기둥 삭제
- 위에 다른 기둥이 없는가? 해당 기둥을 삭제해도 다른 보가 기둥을 받히는가?
- 위에 다른 보가 없는가? 있다면 기둥 없이도 보가 유지가능한가?

보 삭제
- 보 위에 기둥이 없는가? 있다면 옆에 다른보가 기둥을 지지해주는가?
- 옆으로 연결되었던 보가 유지될 수 있는가?

!! 결국 삭제 시에는 영향을 받는 기둥과 보를 찾고, 해당 기둥과 보를 설치 조건으로 검사하면 된다.


*/