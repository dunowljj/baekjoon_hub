import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int lx1 = Integer.parseInt(st.nextToken());
        int by1 = Integer.parseInt(st.nextToken());
        int rx1 = Integer.parseInt(st.nextToken());
        int ty1 = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        int lx2 = Integer.parseInt(st.nextToken());
        int by2 = Integer.parseInt(st.nextToken());
        int rx2 = Integer.parseInt(st.nextToken());
        int ty2 = Integer.parseInt(st.nextToken());

        int dx = Math.min(rx1, rx2) - Math.max(lx1, lx2);
        int dy = Math.min(ty1, ty2) - Math.max(by1, by2);

        if (dx < 0 || dy < 0) {
            System.out.print("NULL");
            return;
        }

        if (dx == 0 && dy == 0) {
            System.out.print("POINT");
            return;
        }

        if ((dx == 0 && dy > 0) || (dy == 0 && dx > 0)) {
            System.out.print("LINE");
        }

        if (dx > 0 && dy > 0) {
            System.out.print("FACE");
        }
    }
}