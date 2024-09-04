package com.dd.anlp.bai7;

import java.util.Arrays;
import java.util.List;

public class streamsForMultiCondition {
    public static void main(String[] args) {
        List<Person> persons = Arrays.asList(
                new Person("John", 20),
                new Person("Jun", 30),
                new Person("Sarah", 19)
        );

        Person person1 = persons.stream().filter(p -> "John".equals(p.getName()) && 20 == p.getAge()).findAny().orElse(null);
        System.out.println(person1.getName() + " " + person1.getAge());

        Person person2 = persons.stream().filter(p -> "Sarah".equals(p.getName()) && 20 == p.getAge()).findAny().orElse(null);
        if(person2 == null){
            System.out.println("Nothing");
        }else{
            System.out.println(person2.getName() + " " + person2.getAge());
        }
    }
}
