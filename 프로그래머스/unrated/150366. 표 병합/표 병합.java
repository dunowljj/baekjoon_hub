import java.util.*;

class Point {
    int x;
    int y;
    
    Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

class Cell {
    private static final int MIN_SIZE = 1;
    String value;
    List<Point> mergedPoints;
    
    Cell(String value) {
        this.value = value;
        this.mergedPoints = new ArrayList<>();
    }
    
    public void addAll(Cell added) {
        mergedPoints.addAll(added.mergedPoints);
    }
    
    public boolean hasValue() {
        return !(value.equals("EMPTY"));
    }
    
    public boolean isSingleCell() {
        return mergedPoints.size() == MIN_SIZE;
    }
}



class Grid {
    private static final String EMPTY_VALUE = "EMPTY";
    private static final int LENGTH_OF_GRID = 50;
    private final Cell[][] grid;
    List<String> printResult = new ArrayList<>();
    
    Grid() {
        grid = new Cell[LENGTH_OF_GRID][LENGTH_OF_GRID];
        
        for (int i = 0; i < LENGTH_OF_GRID; i++) {
            for (int j = 0; j < LENGTH_OF_GRID; j++) {
                grid[i][j] = new Cell(EMPTY_VALUE);
                grid[i][j].mergedPoints.add(new Point(i,j));
            }
        }
    }
    
    public void execute(String command) {
        String[] splitedCommand = command.split(" ");
        
        if (command.startsWith("UPDATE")) {
            if (splitedCommand.length == 4) updateOne(splitedCommand);
            if (splitedCommand.length == 3) updateSelected(splitedCommand);
            return;
        }
        
        if (command.startsWith("MERGE")) {
            merge(splitedCommand);
            return;
        }
        
        if (command.startsWith("UNMERGE")) {
            unmerge(splitedCommand);
            return;
        }
        
        if (command.startsWith("PRINT")) {
            print(splitedCommand);
            return;
        }
    }
    
    private void updateOne(String[] splitedCommand) {
        int row = Integer.parseInt(splitedCommand[1]) - 1;
        int col = Integer.parseInt(splitedCommand[2]) - 1;
        String updateValue = splitedCommand[3];
        
        grid[row][col].value = updateValue;
    }
    
    private void updateSelected(String[] splitedCommand) {
        String targetValue = splitedCommand[1];
        String updateValue = splitedCommand[2];
        
        for (int i = 0; i < LENGTH_OF_GRID; i++) {
            for (int j = 0; j < LENGTH_OF_GRID; j++) {
                if (targetValue.equals(grid[i][j].value)) {
                    grid[i][j].value = updateValue;
                }
            }
        }
    }
    
    private void merge(String[] splitedCommand) {
        int row1 = Integer.parseInt(splitedCommand[1]) - 1;
        int col1 = Integer.parseInt(splitedCommand[2]) - 1;
        int row2 = Integer.parseInt(splitedCommand[3]) - 1;
        int col2 = Integer.parseInt(splitedCommand[4]) - 1;

        Cell cell1 = grid[row1][col1];
        Cell cell2 = grid[row2][col2];
       
        // 먼저 주어진 위치에 value가 있다면 해당 Cell 기준으로 합쳐진다.
        if (cell1.hasValue()) {
            merge(cell1, cell2);
                 
        } else {
            merge(cell2, cell1);
        }
    }
    
    private void merge(Cell base, Cell merged) {
        // 같은 Cell이면 무시
        if (base == merged) return;
        
        // grid에 합쳐질 내용이 들어있는 Cell들은 기준점이 될 base로 대체
        for (Point point : merged.mergedPoints) {
            grid[point.x][point.y] = base;
        }

        // base의 리스트에 합쳐질 Cell이 가진 리스트 내용 모두 추가
        base.addAll(merged);
    }

    
    private void unmerge(String[] splitedCommand) {
        int row = Integer.parseInt(splitedCommand[1]) - 1;
        int col = Integer.parseInt(splitedCommand[2]) - 1;
        
        Cell cell = grid[row][col];
        
        String remainValue = cell.value;

        // 병합된 모든 Cell들 기본값으로 초기화
        for (Point point : cell.mergedPoints) {
            grid[point.x][point.y] = new Cell(EMPTY_VALUE); 
            grid[point.x][point.y].mergedPoints.add(new Point(point.x, point.y));
        }
        
        grid[row][col].value = remainValue;
    }
    
    private void print(String[] splitedCommand) {
        int row = Integer.parseInt(splitedCommand[1]) - 1;
        int col = Integer.parseInt(splitedCommand[2]) - 1;
        
        printResult.add(grid[row][col].value);
    }
    
    public String[] getPrintResult() {
        return printResult.stream()
            .toArray(String[]::new);
    }
}

class Solution {
    
    public String[] solution(String[] commands) {
        Grid grid = new Grid();
        
        for (String command : commands) {
            grid.execute(command);    
        }
        
        return grid.getPrintResult();
    }
}
/*
MERGE 
- 어떤 셀을 선택해도 병합된 셀이 선택되어야 한다.
    -> 합쳐지는 배열 위치에 같은 Cell 객체를 넣는다. -> 어떤 Cell을 선택해도 병합된 Cell이 모두 선택된다.
    
- 병합된 셀 과 병합된 셀을 병합하는 경우
    -> 두 셀중 기준을 잡고, 배열에서 합쳐질 셀이 위치한 곳을 모두 기준 셀로 대체해야 한다. 통합될 셀의 모든 위치를 알아야 한다. 
    -> 그러기 위해 사전에 Cell 객체 마다 List<Point>를 필드에 둔다. 해당 List에 병합된 배열 위치값을 저장한다.
    -> 두 Cell을 병합할때, List를 병합시킨다.

UNMERGE
- 병합된 셀 중 어떤 셀을 선택해도 모두 초기화되어야 한다.
    -> 어떤 셀을 선택해도 병합에 포함된 셀들을 찾을 수 있어야 한다. -> Cell마다 필드에 List<Point> 저장


빈 셀은 EMPTY처리가 되는데, 초기화 시에 셀 값을 EMPTY로 해놓기.
- 업데이트할때 value를 EMPTY로 지정하는 경우는 없다. 소문자와 숫자만 주어지기 때문이다.
- 캐싱해서 재활용


병합이 없다면 초깃값으로 돌아가는가?
*/