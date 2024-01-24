// 방법 1 : 카운트 배열을 직접 만든다. 음수까지 고려해서 인덱스 변환해줌

import java.io.*;
import java.util.StringTokenizer;

public class Main {

    public static final int IDX_CONVERT = 10_000_000;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        int N = Integer.parseInt(br.readLine());
        int[] cardsCount = new int[20_000_001];

        // 가지고 있는 카드들의 수에 해당하는 인덱스에 ++해서 카운트 0을 고려 안했음;;
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            int input = Integer.parseInt(st.nextToken());
            cardsCount[input + IDX_CONVERT]++;
        }

        int M = Integer.parseInt(br.readLine());

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < M; i++) {
            int curr = Integer.parseInt(st.nextToken());
            int count = cardsCount[curr + IDX_CONVERT];

            sb.append(count).append(" ");
        }

        bw.write(sb.toString().trim());
        bw.flush();
        bw.close();
    }
}

// 방법 2 : 이분탐색 사용
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    public static final String SPACE = " ";

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine(), SPACE);

        int[] ownCards = new int[N];
        for (int i = 0; i < N; i++) {
            ownCards[i] = Integer.parseInt(st.nextToken());
        }

        Arrays.sort(ownCards);

        int M = Integer.parseInt(br.readLine());
        st = new StringTokenizer(br.readLine(), SPACE);

        StringBuilder answer = new StringBuilder();
        for (int i = 0; i < M; i++) {
            int searchingCard = Integer.parseInt(st.nextToken());
            answer.append(countFindCard(ownCards, searchingCard)).append(" ");
        }

        System.out.print(answer);
    }

    private static int countFindCard(int[] ownCards, int searchingCard) {
        int firstIdx = findFirst(ownCards, searchingCard);
        int lastIdx = findLast(ownCards, searchingCard);

        return lastIdx - firstIdx + 1;
    }

    private static int findFirst(int[] ownCards, int searchingCard) {
        int low = -1;
        int high = ownCards.length;

        // F F F T3 T3 T T T T
        // T3 T3 T T T T
        while (low + 1 < high) {
            int mid = (low + high) / 2;

            if (ownCards[mid] >= searchingCard) {
                high = mid;
            } else {
                low = mid;
            }
        }

        // 처음 T가 되는 지점. 즉 해당 찾는 숫자와 일치하는 부분을 의미한다.
        return high;
    }

    private static int findLast(int[] ownCards, int searchingCard) {
        int low = 0;
        int high = ownCards.length;

        // F F F3 F3 T T T T
        // F T T T T
        while (low + 1 < high) {
            int mid = (low + high) / 2;

            if (ownCards[mid] > searchingCard) {
                high = mid;
            } else {
                low = mid;
            }
        }

        // 처음 T가 되기 직전의 F인 지점. 즉 해당 찾는 숫자의 마지막 부분을 찾는다.
        return low;
    }
}

// 방법 3 : 이분탐색 (인덱스 방식 다름) : 출처 : https://st-lab.tistory.com/267 
import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.Scanner;
 
public class Main {
 
	public static void main(String[] args) {
 
		Scanner in = new Scanner(System.in);
		
		int N = in.nextInt();
		int[] arr = new int[N];
		
		for(int i = 0; i < N; i++) {
			arr[i] = in.nextInt();
		}
		
		Arrays.sort(arr);	// 이분 탐색을 하기 위해서는 반드시 정렬되어있어야 한다.
		
		int M = in.nextInt();
		
		StringBuilder sb = new StringBuilder();
		
		for(int i = 0; i < M; i++) {
			int key = in.nextInt();
 
			// upperBound와 lowerBound의 차이 값을 구한다.
			sb.append(upperBound(arr, key) - lowerBound(arr, key)).append(' ');
		}
		System.out.println(sb);
	}
 
 
	private static int lowerBound(int[] arr, int key) {
		int lo = 0; 
		int hi = arr.length; 
 
		// lo가 hi랑 같아질 때 까지 반복
		while (lo < hi) {
 
			int mid = (lo + hi) / 2; // 중간위치를 구한다.
 
			/*
			 *  key 값이 중간 위치의 값보다 작거나 같을 경우
			 *  
			 *  (중복 원소에 대해 왼쪽으로 탐색하도록 상계를 내린다.)
			 */
			if (key <= arr[mid]) {
				hi = mid;
			}
 
			else {
				lo = mid + 1;
			}
 
		}
 
		return hi;
	}
 
	private static int upperBound(int[] arr, int key) {
		int lo = 0; 
		int hi = arr.length; 
 
		// lo가 hi랑 같아질 때 까지 반복
		while (lo < hi) {
 
			int mid = (lo + hi) / 2; // 중간위치를 구한다.
 
			// key값이 중간 위치의 값보다 작을 경우
			if (key < arr[mid]) {
				hi = mid;
			}
			// 중복원소의 경우 else에서 처리된다.
			else {
				lo = mid + 1;
			}
 
		}
 
		return hi;
	}
	
}


// 방법 4 : 해시맵 사용
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class Main {

    public static final String SPACE = " ";

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        Map<Integer, Integer> ownCards = new HashMap<>();
        for (int i = 0; i < N; i++) {
            int cardNo = Integer.parseInt(st.nextToken());
            ownCards.put(cardNo, ownCards.getOrDefault(cardNo, 0) + 1);
        }

        int M = Integer.parseInt(br.readLine());

        int[] answer = new int[M];
        st = new StringTokenizer(br.readLine());
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < M; i++) {
            int findCardNo = Integer.parseInt(st.nextToken());
            int ownCount = ownCards.getOrDefault(findCardNo, 0);

            result.append(ownCount).append(SPACE);
        }

        System.out.print(result.toString().trim());
    }
}
