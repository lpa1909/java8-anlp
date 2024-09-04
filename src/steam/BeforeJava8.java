package steam;

import java.util.Arrays;
import java.util.List;

public class BeforeJava8 {
    public static void main(String[] args) {
        List<Person> persons = Arrays.asList(
            new Person("mkyong", 30),
            new Person("jack", 20),
            new Person("lawrence", 40)
        );

        Person res = getPersonByName(persons, "lawrence");
        System.out.println(res.getName());
    }

    private static Person getPersonByName(List<Person> persons, String name){
        Person res = null;
        for(Person p : persons){
            if(name.equals(p.getName())){
                res = p;
            }
        }
        return res;
    }
}
