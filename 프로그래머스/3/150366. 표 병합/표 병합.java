import java.util.*;

class Solution {
    
    private final static Table table = new Table();
    private final static List<String> answer = new ArrayList<>();
    
    public String[] solution(String[] commands) {
        for (String command : commands) {
            String[] split = command.split(" ");
            
            if (split[0].equals("UPDATE")) {
                if (split.length == 4) table.updateCell(split);
                if (split.length == 3) table.updateValue(split);
                
            } else if (split[0].equals("MERGE")) {
                table.merge(split);
            } else if (split[0].equals("UNMERGE")) {
                table.unmerge(split);
            } else if (split[0].equals("PRINT")) {
                table.print(split);
            }
        }
        
        return answer.stream().toArray(String[]::new);
    }
    
    static class Table {
        private static final String EMPTY = "";
        private static final int SIZE = 51;
        private final Cell[][] table = new Cell[SIZE][SIZE];
        
        public Table() {
            for (int r = 0; r < SIZE; r++) {
                for (int c = 0; c < SIZE; c++) {
                    table[r][c] = new Cell(r,c);
                }
            }
        }
        
        public void updateCell(String[] command) {
            int r = Integer.parseInt(command[1]);
            int c = Integer.parseInt(command[2]);
            String newValue = command[3];

            table[r][c].value = newValue;
        }
    
        public void updateValue(String[] command) {
            String originValue = command[1];
            String newValue = command[2];

            for (int r = 0; r < SIZE; r++) {
                for (int c = 0; c < SIZE; c++) {
                    Cell target = table[r][c];
                    if ((target.value).equals(originValue)) {
                        target.value = newValue;
                    }
                }
            }
        }

        public void merge(String[] command) {
            int r1 = Integer.parseInt(command[1]);
            int c1 = Integer.parseInt(command[2]);
            int r2 = Integer.parseInt(command[3]);
            int c2 = Integer.parseInt(command[4]);
            
            if (r1 == r2 && c1 == c2) return;

            Cell target1 = table[r1][c1];
            Cell target2 = table[r2][c2];

            // target1에 통합
            target1.trace.add(new Cell(r1,c1));
            target1.trace.add(new Cell(r2,c2));                
            target1.trace.addAll(target2.trace);
               
            
            // (e,e) -> 둘다 빈값이므로 아무 방향으로 통합해도 같음 -> 2의 값으로 merge
            // (e,o) -> 2의 값으로 merge
            if (target1.isEmpty()) {  
                target1.value = target2.value;
            }
            
            // (o,e),(o,o)  -> 1의 값으로 merge
            if (!target1.isEmpty()) {
                // to do nothing
            }
            
            for (Cell t : target1.trace) {
                table[t.r][t.c] = target1;    
            }
        }

        
        public void unmerge(String[] command) {
            int r = Integer.parseInt(command[1]);
            int c = Integer.parseInt(command[2]);
            
            Cell target = table[r][c];
            String temp = target.value;
            
            // 병합된 셀 모두 빈칸으로 초기화
            for (Cell t : target.trace) {
                table[t.r][t.c] = new Cell(t.r,t.c);
            }
            
            table[r][c].value = temp;
        }

        public void print(String[] command) {
            int r = Integer.parseInt(command[1]);
            int c = Integer.parseInt(command[2]);
            
            Cell target = table[r][c];
            if (target.isEmpty()) answer.add("EMPTY");
            else answer.add(target.value);
        }
        
        static class Cell {
            int r;
            int c;
            String value;
            Set<Cell> trace = new HashSet<>();

            public Cell(int r, int c) {
                this.r = r;
                this.c = c;
                this.value = "";
            }
            

            public boolean isEmpty() {
                return (this.value).equals(EMPTY);
            }
            
            @Override
            public boolean equals(Object o) {
                Cell c = (Cell) o;
                
                return this.r == c.r && this.c == c.c;
            }
            
            @Override
            public int hashCode() {
                return (this.r * 100) + c;
            }
        }
    }   
}


/**
1.UPDATE -> Cell[][] 선언 후 특정 좌표의 값 갱신
2.UPDATE v1 v2 -> value 기반 변경. value도 객체로 만들고 매핑을 해놔야하나? 최대 2500칸이므로, 전체 탐색해도 문제 없을듯
3.MERGE r1 c1 r2 c2
- 같은 셀 무시
- 사이의 셀들은 영향x
- 값이 하나만 있다면 해당 값
- 두 셀 모두 있다면 r1,c1 값
- r1 c1, r2 c2 어딜가든 해당 값에 접근해야함

UPDATE, MERGE -> 참조를 사용하려 했는데, UNMERGE도 있음 -> 기존 정보를 모두 저장해놔야한다.

이차원 배열 Cell[][] 생성 후, 특정 좌표에 cell의 참조를 저장
Cell안에 병합된 정보 유지

4. UNMERGE 기존 저장한 정보로 복원시키기
특정 셀만 unmerge해야함. 실행초기로 돌아간다는 것은 칸의 경계만 "병합 형태만 실행초기로 돌아가는 것"으로 합쳐진 셀의 값은 해당 셀이 가져가버린다.
즉 trace에는 좌표값만 있으면 되고, 대표값을 Unmerge 지정 셀에서 가져가는 것.

5. 특정 위치 값 출력. 빈 셀은 "EMPTY" 출력. 값이 "EMPTY"인 경우?

Cell {
    String value; 
    
    int r;
    int c;
    
    // 원복할 좌표들, 현재 이 클래스의 좌표 정보도 넣어놓기
    Stack<Cell> trace;
}


trace를 어떻게 통합할 것인가? -> Set 기반으로 trace를 유지하고, r,c좌표로 같은 좌표를 검증
*/