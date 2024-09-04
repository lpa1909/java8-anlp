package com.dd.anlp.bai7;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class streamsFilterAndCollect {

     // before java 8
//    public static void main(String[] args) {
//        List<String> lines = Arrays.asList("spring", "node", "mkyong");
//        List<String> res = getFilterOutput(lines, "mkyong");
//        for(String tmp : res){
//            System.out.println(tmp);
//        }
//    }
//
//    private static List<String> getFilterOutput(List<String> lines, String filter){
//        List<String> res = new ArrayList<>();
//        for(String line : lines){
//            if(!filter.equals(line)){
//                res.add(line);
//            }
//        }
//        return res;
//    }

    // now java 8
    public static void main(String[] args) {
        List<String> lines = Arrays.asList("spring", "node", "mkyong");
        List<String> res = lines.stream().filter(line -> !"mkyong".equals(line)).collect(Collectors.toList());
        res.forEach(System.out::println);
    }
}
