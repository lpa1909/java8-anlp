package com.dd.anlp.bai6;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TestSorting {
    public static void main(String[] args) {
        List<Developer> listDevs = getDevelopers();

        System.out.println("Before Sort");
        for(Developer dev : listDevs) {
            System.out.println(dev.getName() + " " + dev.getSalary() + " " + dev.getAge());
        }

//        // sort by age
//        Collections.sort(listDevs, new Comparator<Developer>() {
//            @Override
//            public int compare(Developer o1, Developer o2){
//                return o1.getAge() - o2.getAge();
//            }
//        });

        // sort by name
//        Collections.sort(listDevs, new Comparator<Developer>() {
//            @Override
//            public int compare(Developer o1, Developer o2){
//                return o1.getName().compareTo(o2.getName());
//            }
//        });

        // sort by salary
//        Collections.sort(listDevs, new Comparator<Developer>() {
//            @Override
//            public int compare(Developer o1, Developer o2) {
//                return (int) (o1.getSalary() - o2.getSalary());
//            }
//        });

        // lambda expression sort by age
//        listDevs.sort((Developer o1, Developer o2) -> o1.getAge() - o2.getAge());

        //lambda expression sort by name
//        listDevs.sort((Developer o1, Developer o2) -> o1.getName().compareTo(o2.getName()));

        // lambda expression sort by salary
//        listDevs.sort((Developer o1, Developer o2) -> (int)(o1.getSalary() - o2.getSalary()));

        // lambda expression reverse
        Comparator<Developer> salaryComparator = (o1, o2) -> (int)(o1.getSalary() - o2.getSalary());
        listDevs.sort(salaryComparator.reversed());

        // print by lambda expression
//        listDevs.forEach((developer) -> System.out.println(developer));

        System.out.println("After sort");
        for(Developer dev : listDevs){
            System.out.println(dev.getName() + " " + dev.getSalary() + " " + dev.getAge());
        }
    }

    private static List<Developer> getDevelopers() {

        List<Developer> result = new ArrayList<>();

        result.add(new Developer("mkyong", 70000, 33));
        result.add(new Developer("alvin", 100000, 20));
        result.add(new Developer("jason", 900000, 10));
        result.add(new Developer("iris", 170000, 55));

        return result;

    }
}
