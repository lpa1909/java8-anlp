package com.dd.anlp.bai7;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class streamsFilterAndMap {
    public static void main(String[] args) {
        // Now in java 8
        List<Person> persons = Arrays.asList(
                new Person("mykong", 30),
                new Person("jack", 30),
                new Person("lawrencen", 30)
        );

        String name = persons.stream().filter(p -> "jack".equals(p.getName())).map(Person::getName).findAny().orElse("");

        System.out.println("Name: " + name);

        List<String> collect = persons.stream().map(Person::getName).collect(Collectors.toList());
        collect.forEach(System.out::println);


    }
}
