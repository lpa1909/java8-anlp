package steam;

import java.util.Arrays;
import java.util.List;

public class NowJava8 {
    public static void main(String[] args) {
        List<Person> persons = Arrays.asList(
                new Person("mkyong", 30),
                new Person("jack", 20),
                new Person("lawrence", 40)
        );

        Person res1 = persons.stream().filter(p -> "mkyong".equals(p.getName())).findAny().orElse(null);
        System.out.println(res1.getName());

        Person res2 = persons.stream().filter(p -> "jack".equals(p.getName())).findAny().orElse(null);
        System.out.println(res2.getName());
    }
}
