class Point {
    int x;
    int y;
    
    Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public int distanceTo(Point dest) {
        return Math.abs(dest.x - x) + Math.abs(dest.y - y);
    }
}

class Solution {
    Point[] keypad = new Point[12 + 1];
    
    // 왼손 오른손 시작 위치
    int left = 10;
    int right = 12;
    
    boolean isRightHand = false;
    
    public String solution(int[] numbers, String hand) {
        StringBuilder sb = new StringBuilder();
        
        isRightHand = hand.equals("right") ? true : false;
        
        // 1~12에 각 위치 (Point) 집어넣기  (keypad의 인덱스에 번호 입력시 해당 위치가 나온다.)
        for (int i = 0; i < 12; i++) {
            keypad[i + 1] = new Point(i / 3 ,i % 3);
        }
        
        // 0은 그냥 직접 넣어주기
        keypad[0] = keypad[11];

                
        for (int i = 0; i < numbers.length; i++) {
            int dest = numbers[i];
            char used = getHand(dest);
            
            if (used == 'R') right = dest;
            if (used == 'L') left = dest;
            
            sb.append(getHand(dest));
        }
        
        return sb.toString();
    }
    
    private char getHand(int dest) {
        // 왼쪽일때
        if (isLeftSide(dest)) {
            return 'L';
        }    
        // 오른쪽일때
        if (isRightSide(dest)) {
            return 'R';
        }

        // 가운데라인
        int toRight = keypad[dest].distanceTo(keypad[right]);
        int toLeft = keypad[dest].distanceTo(keypad[left]);

        // 왼손이 가까움
        if (toRight > toLeft) {
            return 'L';
        } 
        
        // 오른손이 가까움        
        if (toRight < toLeft) {
            return 'R';
        }

        // 거리 같으면 손 방향
        return isRightHand ? 'R' : 'L';
    }
    
    private boolean isLeftSide(int dest) {
        return dest == 1 || dest == 4 || dest == 7;
    }
    private boolean isRightSide(int dest) {
        return dest == 3 || dest == 6 || dest == 9;
    }
}

/*
상하좌우로만 이동 가능. 한 칸은 거리로 1

1) 2개의 위치를 저장

2) 
147 -> 왼손 위치 갱신, 왼손 추가
369 -> 오른손 위치 갱신, 오른손 추가
가운데 -> 해당 위치에서 목적지까지 거리 구하고 더 가까운 쪽 손 위치 갱신, 해당 손 추가

3) 거리가 같으면 주로 쓰는 손으로 갱신, 추가


Point[] 배열 만들어서 구현하기

*/