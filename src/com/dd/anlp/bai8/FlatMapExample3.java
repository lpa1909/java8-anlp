package com.dd.anlp.bai8;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class FlatMapExample3 {
    public static void main(String[] args) {
        Path path = Paths.get("E:\\Java Practice\\text.txt");
        try{
            Stream<String> lines = Files.lines(path, StandardCharsets.UTF_8);
            Stream<String> words = lines.flatMap(line -> Stream.of((line.split(" +"))));
            long noOfWords = words.count();
            System.out.println(noOfWords);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
