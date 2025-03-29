import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {

    static int[] parent;
    static boolean[] knowFact;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        // [line 1]
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        parent = new int[N + 1];
        for (int i = 0; i < parent.length; i++) {
            parent[i] = i;
        }

        // [line 2] 진실을 아는 사람들 수, 번호 목록
        st = new StringTokenizer(br.readLine());
        int knowPersonCount = Integer.parseInt(st.nextToken());
        if (knowPersonCount == 0) {
            System.out.print(M);
            System.exit(0);
        }

        knowFact = new boolean[N + 1];
        for (int i = 0; i < knowPersonCount; i++) {
            int personNo = Integer.parseInt(st.nextToken());
            knowFact[personNo] = true;
        }

        // [line 3~] 각 파티별 참여자 수, 참여자 번호 목록
        List<int[]> parties = new ArrayList<>();
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int partInCount = Integer.parseInt(st.nextToken());
            int[] party = new int[partInCount];
            // 파티 참여자들

            int before = Integer.parseInt(st.nextToken());
            party[0] = before;

            for (int j = 1; j < partInCount; j++) {
                int participant = Integer.parseInt(st.nextToken());
                party[j] = participant;

                union(parent[before], participant);
                before = participant;
            }

            parties.add(party);
        }

        int answer = 0;
        for (int[] party : parties) {
            boolean noOneKnowFact = true;

            for (int participant : party) {
                int group = find(participant);
                if (knowFact[group]) {
                    noOneKnowFact = false;
                    break;
                }
            }

            if (noOneKnowFact) {
                answer++;
            }
        }

        System.out.print(answer);
    }


    private static int find(int no) {
        if (parent[no] == no) return no;
        return parent[no] = find(parent[no]);
    }

    private static void union(int n1, int n2) {
        int p1 = find(n1);
        int p2 = find(n2);

        if (p1 != p2) {
            // 둘 중 하나라도 진실을 안다면, 그룹도 진실을 듣게 된다.
            if (knowFact[p1] || knowFact[p2]) {
                knowFact[p1] = true;
            }
            parent[p2] = p1;
        }
    }
}

/**
 * 50개의 파티, 50명 -> 2500번
 *
 *
 *
 *  어떤 사람이 어떤 파티에서는 진실을 듣고,
 *  또다른 파티에서는 과장된 이야기를 들었을 때도 지민이는 거짓말쟁이로 알려지게 된다.
 *
 *  1) 파티가 겹치는 사람들끼리 그룹을 지어준다.
 *  2) 만약 해당 그룹에 한명이라도 진실을 알고 있으면, 해당 그룹은 모두 진실만 듣게 된다.
 *  3) 진실을 아는 사람이 하나도 없다면, 해당 그룹은 거짓을 듣게 된다.
 *
 *  진실을 아는 사람 수가 0일수도 있다.
 */