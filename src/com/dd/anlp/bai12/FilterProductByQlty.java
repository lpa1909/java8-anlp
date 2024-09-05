package com.dd.anlp.bai12;

import com.dd.anlp.bai10.ListAllProduct;
import com.dd.anlp.bai9.Product;

import java.sql.Array;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class FilterProductByQlty {
    public static void main(String[] args) {
        ListAllProduct listAllProduct = new ListAllProduct();
        List<Product> products = listAllProduct.getListProduct();
        List<Product> resProduct = filterByQuality(products);
        resProduct.forEach(product -> System.out.println(product.getName()));
    }
    // filter product by stream
//    public static List<Product> filterByQuality(List<Product> products){
//        List<Product> res = products.stream().filter(productt -> productt.getQuanlity() > 0 && productt.isDelete() == false).collect(Collectors.toList());
//        return res;
//    }

    // filter product don't use stream
    public static List<Product> filterByQuality(List<Product> products){
        List<Product> res = new ArrayList<>();
        for(Product p : products){
            if(p.getQuanlity() > 0 && p.isDelete() == false){
                res.add(p);
            }
        }
        return res;
    }

}