import java.util.*;

class Solution {
    
    enum Status {
        LEAVE, ENTER;
    }
    
    static class Message {
        User user;
        Status status;
        
        Message(User user, Status status) {
            this.user = user;
            this.status = status;
        }
        
        public String toString() {
            if (this.status == status.ENTER) {
                return user.nickName + "님이 들어왔습니다.";
            }
                
            if (this.status == status.LEAVE)  {
                return this.user.nickName + "님이 나갔습니다.";
            }
            
            throw new IllegalArgumentException();
        }
    }
    
    static class User {
        String uid;
        String nickName;
        
        User(String uid, String nickName) {
            this.uid = uid;
            this.nickName = nickName;
        }
        
        public void setName(String nickName) {
            this.nickName = nickName;
        }
    }
    
    public String[] solution(String[] record) {
        Map<String, User> userMap = new HashMap<>();
        Queue<Message> messageQueue = new LinkedList<>();
        
        for (String rec : record) {
            String[] splited = rec.split(" ");
            
            String command = splited[0];
            String uid = splited[1];
            
            if (command.equals("Enter")) {
                String nickName = splited[2];
                
                // 이름 바꾸면서 들어온 경우 이전에 저장해논 User를 뽑아 닉네임을 변경해야한다.
                User user = userMap.getOrDefault(uid, new User(uid, nickName));
                user.setName(nickName); 
                userMap.put(uid, user);
                
                messageQueue.offer(new Message(user, Status.ENTER));
                continue;
            }
            
            if (command.equals("Leave")) {
                User user = userMap.get(uid);
                
                messageQueue.offer(new Message(user, Status.LEAVE));
                continue;
            }
            
            if (command.equals("Change")) {
                String nickName = splited[2];
                
                User user = userMap.get(uid);                
                user.setName(nickName);
                userMap.put(uid, user);
                // continue;
            }
        }
        
        return messageQueue.stream()
            .map(Message::toString)
            .toArray(String[]::new);
    }
}
/*
Queue<Message>
HashMap<uid, User> -> 공통된 User 객체를 재활용하고, 검색해서 객체의 닉네임만 바꿔주면 일괄 수정된다.

Message
- User
- status (leave/enter)

User
- uid
- nickName

- List<uid>의 경우 매번 map에서 nickname을 찾아야 한다.
- List<User>는 객체를 쓸데없이 저장해야 한다. 꺼내기는 바로 가능
--> 큰 차이 없음

Map<uid, User>

1) 사용자 객체를 만든다. User(uid, nickname) 
2) Queue에 상태(Enter/Leave)를 구분하는 Message(사용자, 상태) 객체를 넣는다. -> 해당 객체 이름만 변경하면 모두 변경된다.
3) 해당 uid를 가진 유저가 닉네임을 변경 시, 해당 객체만 찾아서 변경한다.
*/