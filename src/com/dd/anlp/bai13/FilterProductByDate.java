package com.dd.anlp.bai13;

import com.dd.anlp.bai10.ListAllProduct;
import com.dd.anlp.bai9.Product;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class FilterProductByDate {
    public static void main(String[] args) {
        ListAllProduct listAllProduct = new ListAllProduct();
        List<Product> products = listAllProduct.getListProduct();
        List<Product> resProduct = filterProductBySaleDate(products);
        resProduct.forEach(product -> System.out.println(product.getName()));
    }
    //filter product by sale date (stream)
    static List<Product> filterProductBySaleDate(List<Product> products){
        LocalDate localDate = LocalDate.now();
        Date saleDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        List<Product> res = products.stream().filter(product -> saleDate.after(product.getSaleDate()) && product.isDelete() == false).collect(Collectors.toList());
        return res;
    }

    // filter product don't use stream
//    static List<Product> filterProductBySaleDate(List<Product> products){
//        LocalDate localDate = LocalDate.now();
//        Date saleDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
//        List<Product> res = new ArrayList<>();
//        for(Product p : products){
//            if(saleDate.after(p.getSaleDate()) && p.isDelete() == false) res.add(p);
//        }
//        return res;
//    }



}
