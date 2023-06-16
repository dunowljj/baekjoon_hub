import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        Map<String, Integer> extMap = new HashMap<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());

        for (int i = 0; i < N; i++) {
            String fileName = br.readLine();
            String ext = fileName.substring(fileName.indexOf(".") + 1); // extension 확장자

            int value = extMap.getOrDefault(ext, 0) + 1;
            extMap.put(ext, value);
        }

        extMap.entrySet().stream()
                .map((e) -> new Extension(e.getKey(), e.getValue()))
                .sorted()
                .forEach(System.out::println);
    }

    private static class Extension implements Comparable<Extension> {
        String key;
        int value;

        public Extension(String key, int value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public int compareTo(Extension o) {
            return (this.key).compareTo(o.key);
        }

        @Override
        public String toString() {
            return key + " " + value;
        }
    }
}
