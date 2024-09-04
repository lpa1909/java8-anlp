package com.dd.anlp.bai8;

import org.w3c.dom.ls.LSOutput;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class flatMapTutorial {

    public static void main(String[] args) {
        String[][] array = new String[][]{{"a", "b"}, {"c", "d"}, {"e", "f"}};
        // array to a stream
        Stream<String[]> stream1 = Arrays.stream(array);

        // x is a String[]
//        List<String[]> res = stream1.filter(x -> {
//            for(String s : x){
//                if(s.equals("a")) return false;
//            }
//            return true;
//        }).collect(Collectors.toList());
//
        // print
//        res.forEach(x -> System.out.println(Arrays.toString(x)));

//        // same result
//        Stream<String[]> stream2 = Stream.of(array);
//
//        System.out.println(stream1.toString());

//        String[] res = Stream.of(array).flatMap(Stream::of).toArray(String[]::new);
//        for(String s : res){
//            System.out.println(s);
//        }

        List<String> collect = Stream.of(array).flatMap(Stream::of).filter(x -> !"a".equals(x)).collect(Collectors.toList());

        collect.forEach(System.out::println);
    }
}
