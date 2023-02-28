import java.util.*;

class Solution {
    
    static class Student {
        String[] row;
        
        Student(String[] row) {
            this.row = row;
        }
    }
    
    int answer = 0;
    
    public int solution(String[][] relation) {
        List<Student> students = new ArrayList<>();
        for (String[] row : relation) students.add(new Student(row));
        
        int colCount = relation[0].length;
        int rowCount = relation.length;
        Set<String> visited = new HashSet<>();
        
        findKeys(students, colCount, rowCount, visited);
        return answer == 0 ? 1 : answer;
    }
    
    private void findKeys(List<Student> students, int colCount, int rowCount, Set<String> visited) {
        List<List<Integer>> combs;
        List<Integer> indexes;
        
        // column 수 -1 까지만 만들면 된다.
        for (int size = 1; size < colCount; size++) {
            indexes = findNotUsed(colCount, visited);
            combs = new ArrayList<>();
            
            makeCombs(indexes, visited, combs, new StringBuilder(), 0, size); // 최소성 만족하는 조합을 찾는다.
            
            for (List<Integer> comb : combs) {    
                findKeyByBruteForce(students, rowCount, visited, comb); // 유일성을 검사해서 후보키를 찾는다.
            }
        }
    }
    
    private List<Integer> findNotUsed(int colCount, Set<String> visited) {
        List<Integer> indexes = new ArrayList<>();
        for (int idx = 0; idx < colCount; idx++) {
            if (!visited.contains(idx +"")) indexes.add(idx);
        }
        return indexes;
    }
    
    private void makeCombs(List<Integer> indexes, Set<String> visited, List<List<Integer>> combs, StringBuilder comb, int start, int size) {
        if (comb.length() == size) {
            // (방문배열에 있는) 사용했던 모든 후보키 조합에 대해서 최소성 검사
            for (String vComb : visited) {
                if (!isMiniamal(comb.toString(), vComb)) return;
            }

            //  조합을 사용하기 편하게 Integer리스트에 변환해서 넣기
            List<Integer> list = new ArrayList<>();
            for (char ch : comb.toString().toCharArray()) list.add(ch - '0');
            combs.add(list);
            return;
        }
        
        for (int i = start; i < indexes.size(); i++) {
            comb.append(indexes.get(i));
            makeCombs(indexes, visited, combs, comb, i + 1, size);
            comb.deleteCharAt(comb.length() - 1);
        }
    }
    
    private boolean isMiniamal(String comb, String vComb) {
        for (char v : vComb.toCharArray()) { 
            if (!comb.contains(v +"")) return true;
        }
        return false;
    }
        
    private void findKeyByBruteForce(List<Student> students, int rowCount, Set<String> visited, List<Integer> comb) {
        
        // !Unique : row끼리 (comb에 있는) 특정 column들의 값이 모두 일치하는 경우 종료 
        for (int i = 0; i < rowCount; i++) {
            Student now = students.get(i);
            
            for (int j = 0; j < rowCount; j++) {
                Student next = students.get(j);
                if (i != j && isSame(now, next, comb)) return;
            }
        }
        
        //  Unique : 일치하는 row 하나도 없으면 정답처리
        StringBuilder idxes = new StringBuilder();
        for (int idx : comb) idxes.append(idx); 
        visited.add(idxes.toString());
        
        answer++;
        return;
    }
    
    private boolean isSame(Student now, Student next, List<Integer> comb) {
        int count = 0;
        for (int col : comb) {
            if ((now.row[col]).equals(next.row[col])) count++;
        }

        return count == comb.size();
    }
}


/*
모든 튜플은 유일하게 식별 가능하다 -> 전체가 모두 똑같은 튜플은 없다는 의미로, 후보키가 하나도 없어도 전체를 후보키로 볼 수 있다?

2,3이 후보키일때 1,3,4도 후보키가 될 수 있다.
그렇다면 후보키를 방문체크할때 어떻게 해야하는가?

1,3,4 -> 1 / 3 / 4 / 1,3 / 1,4/ 3,4 가 나오면 안된다.
그냥 Set<String>을 만들고 체크하기. 문자하나하나 모두 체크해야 한다.

*/