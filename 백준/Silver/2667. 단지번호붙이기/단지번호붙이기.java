import java.io.*;
import java.util.*;

public class Main {
    static int[][] villageChart;
    static boolean[][] settled;
    static int[][] parent;
    static Map<Integer, Integer> houseCounter = new HashMap<>();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder answer = new StringBuilder();
        int N = Integer.parseInt(br.readLine());

        parent = new int[N + 2][N + 2];
        for (int i = 1; i < N + 1; i++) {
            for (int j = 1; j < N + 1; j++) {
                parent[i][j] = (100 * i) + j;
            }
        }

        // 초기화, 1~N까지 (둘레 0 남김)
        settled = new boolean[N + 2][N + 2];
        villageChart = new int[N + 2][N + 2];
        for (int i = 1; i < N + 1; i++) {
            String line = br.readLine();
            for (int j = 1; j < N + 1; j++) {
                villageChart[i][j] = line.charAt(j - 1) - '0';
            }
        }

        countVillgeAndHouse(N);

        /*
        정답 생성
        */

        // 단지 수 추가
        answer.append(houseCounter.size()).append("\n");
        // 단지별 집 개수 정렬 후 추가
        // 통합된 노드들의 부모노드는 개수에 포함이 안되어 있으므로 + 1, 혼자인 노드는 미리 0으로 초기화 해놨으므로 +1해도 무관하다.
        List<Integer> numOfHouses = new ArrayList<>(houseCounter.values());
        Collections.sort(numOfHouses);
        for (Integer numOfHouse : numOfHouses) {
            answer.append(numOfHouse + 1).append("\n");
        }

        // 출력
        System.out.print(answer.toString().trim());
     /*   bw.write(answer.toString().trim());
        bw.flush();
        bw.close();*/
    }

    private static void countVillgeAndHouse(int N) {
        for (int i = 1; i < N + 1; i++) {
            for (int j = 1; j < N + 1; j++) {
                if (villageChart[i][j] == 1 && !settled[i][j]) {
                    uniteAndCountWhenHasNeighbour(i, j);
                }
            }
        }
    }

    private static void uniteAndCountWhenHasNeighbour(int currI, int currJ) {
        // 현재 위치 방문(처리)여부 체크
        settled[currI][currJ] = true;

        int[] iMapper = new int[]{1, 0, -1, 0};
        int[] jMapper = new int[]{0, 1, 0, -1};

        boolean noNeighbour = true;
        for (int order = 0; order < 4; order++) {
            int neighbourI = currI + iMapper[order];
            int neighbourJ = currJ + jMapper[order];

            if (hasNeighbour(neighbourI, neighbourJ)) {
                // 처리된 노드는 다시 방문 x
                if (!settled[neighbourI][neighbourJ]) {
                    uniteAndCount(currI, currJ, neighbourI, neighbourJ);
                }
                noNeighbour = false;
            }
        }

        // 이웃이 4방면 모두 없는 경우, 0 값으로 초기화
        if(noNeighbour) {
            int curr = currI * 100 + currJ;
            houseCounter.put(curr, 0);
        }
    }
    private static boolean hasNeighbour(int indexI, int indexJ) {
        return villageChart[indexI][indexJ] == 1;
    }
    private static void uniteAndCount(int currI, int currJ, int neighbourI, int neighbourJ) {
        int currParent = findParent(currI, currJ);
        int neighbourParent = findParent(neighbourI, neighbourJ);

        if (neighbourParent != currParent) {

            // unite
            parent[neighbourParent / 100][neighbourParent % 100] = currParent;

            // count
            // 통합될때마다 해당 노드를 키 값으로 가지는 key-value 쌍 등록, value는 1부터 시작해서 1씩 증가
            // 마지막에 해당 값 + 1 하면 해당 마을의 개수
            houseCounter.put((currParent), houseCounter.getOrDefault(currParent, 0) + 1);


            // 통합시킨 이웃을 시작점으로 주변 탐색
            uniteAndCountWhenHasNeighbour(neighbourParent / 100, neighbourParent % 100);
        }

    }
    private static int findParent(int i, int j) {
        int ij = i * 100 + j;
        if (parent[i][j] == ij) return ij;

        return findParent( parent[i][j] / 100 , parent[i][j] % 100);
    }
}

/*
1. 크루스칼 알고리즘, dfs
- 첫 인덱스부터 지나가면서, 4방면에 같은 수가 있는지 체크, 있다면 노드를 통합
- 통합된 노드 내의 노드 개수를 셀 수 있는가? -> 노드를 통합하면서 정답 배열의 부모노드의 번호의 인덱스에 값을 ++ 한다? -> Map<노드값, 통합된 노드 수> 와 같이 구현

- 2차원 배열의 크루스칼? 숫자를 어떤식으로 나타낼 것인가? 100*i + j
- 단지 크기 +2을 해서 인덱스 문제를 방지한다

정답 출력
- 같은 단지는 같은 부모노드에 통합되도록 구현하면, HashMap의 size()는 단지의 개수가 된다.
- 단지 내 집의 수 -> (key)노드번호의 value 값

문제 발생 : 나중에 통용될 예정인 떨어져있는 노드의 경우 문제가 생김 -> 결국 인덱스 기준 순차적인 통합이 아닌, 재귀형식의 통합이 필요해 보인다.
해결 : 해당 노드를 탐색 -> 주변에 이웃 노드를 탐색 후 통합 -> 재귀 : 통합된 이웃 노드를 기준으로 다시 탐색
해당 경우에는 맨 처음 노드에서 통합하는 로직만 구현하면 된다.

과정 : 인덱스를 순회하다가 방문하지 않은 1 발견 -> dfs하면서 방문체크 및 통합  -> 다시 순회, 방문하지 않은 1 찾기

문제 발생 : 혼자 떨어져있는 집의 경우 계산이 되질 않는다.
*/