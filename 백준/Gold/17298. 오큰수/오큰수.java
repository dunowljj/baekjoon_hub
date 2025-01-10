import java.io.*;
import java.util.*;

public class Main {
    static Stack<Integer> stack = new Stack<>();
    static int[] arr;
    static int N = 0;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int N = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        arr = new int[N];
        for(int i=0; i<N; i++){
            arr[i] = Integer.parseInt(st.nextToken());
        }

        for(int i=0; i<N; i++) {

            while (!stack.isEmpty() && arr[stack.peek()] < arr[i]){
                arr[stack.pop()] = arr[i];
            }

            stack.push(i);

        }

        while(!stack.isEmpty()){
            arr[stack.pop()] = -1;
        }
        for(int num : arr){
            bw.write(num+" ");
        }
        bw.flush();
        bw.close();
        }
    }
