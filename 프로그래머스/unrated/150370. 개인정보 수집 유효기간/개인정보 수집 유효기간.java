import java.util.*;

class Solution {
    public int[] solution(String today, String[] terms, String[] privacies) {
        Map<Character, Integer> termMap = new HashMap<>();
        List<Integer> result = new ArrayList<>();
        
        MyDate nowDate = new MyDate(today);
        
        for (String term : terms) {
            String[] typeAndExpDate = term.split("\\s");
            char type = typeAndExpDate[0].charAt(0);
            int month = Integer.parseInt(typeAndExpDate[1]);
            termMap.put(type, month);
        }
        
        for (int i = 0; i < privacies.length; i++) {
            String[] startDateAndType = privacies[i].split("\\s");
            String startDate = startDateAndType[0];
            char type = startDateAndType[1].charAt(0);
            
            MyDate targetDate = new MyDate(startDate);
            int month = termMap.get(type);
            
            MyDate expiredDate = targetDate.calcExpiredDate(month);
            if (expiredDate.isExpired(nowDate)) result.add(i + 1);
        }
        
        
        return result.stream()
            .mapToInt(Integer::intValue)
            .toArray();
        
    }
}

class MyDate {
    int year;
    int month;
    int day;
    
    MyDate(String dateInput) {
        String[] splited = dateInput.split("\\.");

        this.year = Integer.parseInt(splited[0]);
        this.month = Integer.parseInt(splited[1]);
        this.day = Integer.parseInt(splited[2]);
    }
    
    MyDate(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }
    
    public MyDate calcExpiredDate(int monthVal) {
        this.year += monthVal / 12;
        this.month += monthVal % 12;
        
        if (this.month >= 13) {
            this.year += 1;
            this.month -= 12;
        }
        
        if (this.day == 1) {
            this.month -= 1;
            this.day = 28;
            
            if (this.month == 0) {
                this.year -= 1;
                this.month = 12;
            }
        } else {
            this.day -= 1;
        }
        
        return new MyDate(this.year, this.month, this.day);
    }
    
    public boolean isExpired(MyDate today) {
        if (this.year == today.year) {
            if (this.month == today.month) {
                return this.day < today.day;
            }
            
            return this.month < today.month;
        }
        
        return this.year < today.year;
    }
}

/*
만료일 = 시작일 + term(약관 유효기한) - 1Day
if (만료일 > today) 만료됨. -> 파기해야한다.

>>

시작일 > today - term + 1Day
: 변형해서 다음과 같이 계산하면 term의 개수만큼만 날짜계산을 하면된다.

하지만 괜히 가독성 떨어지고 헷갈릴듯하다.
*/