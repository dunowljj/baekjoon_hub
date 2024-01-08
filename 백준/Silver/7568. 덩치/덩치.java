import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        StringTokenizer st;

        List<Person> people = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            int weight = Integer.parseInt(st.nextToken());
            int height = Integer.parseInt(st.nextToken());

            people.add(new Person(weight, height));
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (i == j) continue;

                if (people.get(i).isBiggerThan(people.get(j))) {
                    people.get(j).addScore();
                }
            }
        }

        people.stream()
                .map(Person::getScore)
                .forEach((score) -> System.out.print(score+" "));
    }

    static class Person {
        int weight;
        int height;
        int score;

        public Person(int weight, int height) {
            this.weight = weight;
            this.height = height;
            this.score = 1;
        }

        public void addScore() {
            score++;
        }

        public boolean isBiggerThan(Person target) {
            return this.weight > target.weight && this.height > target.height;
        }

        public int getScore() {
            return score;
        }
    }
}
