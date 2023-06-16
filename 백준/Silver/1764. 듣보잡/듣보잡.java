import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        Set<String> names = new HashSet<>();
        List<String> dautBoJobs = new ArrayList<>();
        int daut = Integer.parseInt(st.nextToken());
        int bo = Integer.parseInt(st.nextToken());

        for (int i = 0; i < daut; i++) {
            String name = br.readLine();
            names.add(name);
        }

        for (int i = 0; i < bo; i++) {
            String name = br.readLine();
            if (names.contains(name)) {
                dautBoJobs.add(name);
            }
        }

        Collections.sort(dautBoJobs);

        System.out.println(dautBoJobs.size());
        for (String dautBoJob : dautBoJobs) {
            System.out.println(dautBoJob);
        }
    }
}
