import java.io.*;
import java.util.*;

class Jewel {
    int weight;
    int price;

    public Jewel(int weight, int price) {
        this.weight = weight;
        this.price = price;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        // 높은 가격순 정렬, 가격이 같다면 높은 무게 순으로 정렬
        PriorityQueue<Jewel> jewels = new PriorityQueue<>(new Comparator<Jewel>() {
            @Override
            public int compare(Jewel o1, Jewel o2) {
                if (o1.price == o2.price) {
                    return o2.weight - o1.weight;
                }
                return o2.price - o1.price;
            }
        });

        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken()); // 총 보석 수
        int K = Integer.parseInt(st.nextToken()); // 가방 개수


        // 보석 우선순위큐에 넣기
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            int weight = Integer.parseInt(st.nextToken());
            int price = Integer.parseInt(st.nextToken());
            jewels.offer(new Jewel(weight, price));
        }

        // <가방 무게 수용 - 가방 개수> 입력
        TreeMap<Integer, Integer> capacities = new TreeMap();
        for (int i = 0; i < K; i++) {
            int input = Integer.parseInt(br.readLine());
            capacities.put(input, capacities.getOrDefault(input, 0) + 1);
        }

        long answer = 0;

        while (!jewels.isEmpty()) {
            Jewel jewel = jewels.poll();

            // 빈 가방 없으면 종료
            if (capacities.isEmpty()) break;

            // 가장 큰 가방이 보석을 담지 못한다면 다음 해당 보석 포기
            if (capacities.lastKey() < jewel.weight) continue;

            // 보석 무게보다 큰 것 중 최솟값 구해서 사용처리(제거)
            int optimalBag = capacities.higherKey(jewel.weight - 1); //higher은 더 큰수부터 검색이므로, -1해서 자신까지 검색

            // 방금 사용한 가방이 마지막 가방이라면 Map에서 제거
            if (capacities.get(optimalBag) == 1) {
                capacities.remove(optimalBag);
            // 마지막 아니면 개수 - 1
            } else {
                capacities.put(optimalBag, capacities.get(optimalBag) - 1);
            }

            // 해당 보석 가방에 넣었으므로 가격 추가
            answer += jewel.price;
        }


        bw.write(answer+"");
        bw.flush();
        bw.close();
    }
}
/*
보석 가격 합의 최댓값

## 범위
총 보석 수 1~30만
총 가발 수 1~30만

가격, 무게 1~100만
최대 무개 1~10^8

## 풀이
가방에는 보석을 하나밖에 못넣는다. -> 비싼 보석부터 넣을 수 있는지 확인하기

보석을 비싼 순으로 정렬,  (가방 무게 >= 보석 무게) 인경우의 최솟값을 검색

## 시간복잡도
가방 개수를 배열로 만들어 모두 탐색 시, 시간은 가방 개수 최대 30만 * 보석의 개수 최대 30만 -> 9 * 10^10
-> Tree의 범위 탐색을 이용해보자. TreeSet을 사용하면 중복되는 가방 무게가 사라진다. TreeMap을 사용해야한다.

higherKey()를 사용하면 더 큰 수 중 최솟값이 찾아진다. 하지만 가방에는 같은무게면 들어갈 수 있다.
보석 무게와 동일한 것도 검색하기 위해 검색하는 보석 무게에서 1을 빼자.
 */
