import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int input = Integer.parseInt(br.readLine());
		
		int cycleLength = 0;
		int copy = input;
		
		do {
			int firstDigit = input % 10;
			int secondDigit = input / 10; // 100미만일때 2번째 자리수
			input = ((firstDigit)*10) + ((firstDigit+secondDigit)%10);
			cycleLength++;
		} while (copy != input);
		
		System.out.println(cycleLength);
	}
}
