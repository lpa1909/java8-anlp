package com.dd.anlp.bai7;

import java.util.Arrays;
import java.util.List;

public class streamsFilterAndFindAnyOrElse {
    private static Person getPersonByName(List<Person> persons, String name){
        Person res = null;
        for(Person p : persons){
            if(name.equals(p.getName())){
                res = p;
            }
        }
        return res;
    }

    public static void main(String[] args) {

        // before in java 8
//        List<Person> persons = Arrays.asList(
//                new Person("mkyong", 30),
//                new Person("jack", 20),
//                new Person("lawrence", 40)
//        );
//
//        Person res = getPersonByName(persons, "lawrence");
//        System.out.println(res.getName());


        // now in java 8
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
