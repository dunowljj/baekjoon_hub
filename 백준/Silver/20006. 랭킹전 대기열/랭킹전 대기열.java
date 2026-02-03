import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    public static final int MAX_LEVEL = 500;
    public static final String STARTED = "Started!";
    public static final String SPACE = " ";
    public static final String WAITING = "Waiting!";
    static int p, m;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        p = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        List<Room> entireRooms = new ArrayList<>();
        Room[] rooms = new Room[MAX_LEVEL + 1];

        for (int i = 0; i < p; i++) {
            st = new StringTokenizer(br.readLine());
            int level = Integer.parseInt(st.nextToken());
            String name = st.nextToken();

            int low = Math.max(0, level - 10);
            int high = Math.min(500, level + 10);

            // 범위 내에 존재하는 방 저장
            List<Room> enableRooms = new ArrayList<>();
            for (int j = low; j <= high; j++) {

                if (rooms[j] != null) {

                    // 방이 꽉찼다면 방 제거
                    if (rooms[j].isFull()) {
                        rooms[j] = null;
                        continue;
                    }
                    
                    enableRooms.add(rooms[j]);
                }
            }

            // 들어갈 방이 없다면 생성. 생성 후 정답 리스트에 생성 순으로 방을 넣어놓기.
            if (enableRooms.isEmpty()) {
                rooms[level] = new Room(entireRooms.size());
                rooms[level].add(new Player(level, name));
                entireRooms.add(rooms[level]);
            // 방이 있다면 생성이 빠른 방에 입장
            } else {
                int minNo = Integer.MAX_VALUE;
                for (Room enableRoom : enableRooms) {
                    minNo = Math.min(minNo, enableRoom.no);
                }

                entireRooms.get(minNo).add(new Player(level, name));
            }
        }

        StringBuilder sb = new StringBuilder();
        for (Room room : entireRooms) {
            room.sortByName();

            if (room.isFull()) {
                sb.append(STARTED).append(System.lineSeparator());
            } else {
                sb.append(WAITING).append(System.lineSeparator());
            }

            for (Player player : room.players) {
                sb.append(player.level).append(SPACE).append(player.name).append(System.lineSeparator());
            }
        }

        System.out.print(sb.toString().trim());

    }

    static class Room {
        int no;
        List<Player> players;

        public Room(int no) {
            this.no = no;
            this.players = new ArrayList<>();
        }

        public int size() {
            return players.size();
        }

        public boolean isFull() {
            return players.size() == m;
        }

        public void add(Player player) {
            players.add(player);
        }

        public void sortByName() {
            Collections.sort(players, Comparator.comparing(p2 -> p2.name));
        }
    }

    static class Player {
        int level;
        String name;

        public Player(int level, String name) {
            this.level = level;
            this.name = name;
        }
    }
}

/**
 * 방은 생성된 순서가 유지되어야 한다.
 * 방 정원이 있어서, 특정 레벨에 모두 찰 수 있음.
 *
 * 입장 가능 방 여러개면, 먼저 생성된거 가야함..
 */