package com.dd.anlp.bai16;

import com.dd.anlp.bai10.ListAllProduct;
import com.dd.anlp.bai9.Product;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class FilterProductBySaleDate {
    public static void main(String[] args) {
        ListAllProduct listAllProduct = new ListAllProduct();
        List<Product> products = listAllProduct.getListProduct();
        List<String[]> res = filterProductBySaleDate(products);
        res.forEach(product -> {
            for(String value : product){
                System.out.print(value + " ");
            }
            System.out.println();
        });
    }

    static List<String[]> filterProductBySaleDate(List<Product> products){
        LocalDate localDate = LocalDate.now();
        Date saleDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        return products.stream().filter(product -> product.getSaleDate().before(saleDate)).map(product -> new String[]{product.getId().toString(), product.getName()}).collect(Collectors.toList());
    }

    // don't use stream
//    static List<String[]> filterProductBySaleDate(List<Product> listProduct) {
//        LocalDate localDate = LocalDate.now();
//        Date saleDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
//        List<String[]> result = new ArrayList<>();
//
//        for (Product product : listProduct) {
//            if (product.getSaleDate().before(saleDate) && product.getQuanlity() > 0) {
//                result.add(new String[]{product.getId().toString(), product.getName()});
//            }
//        }
//
//        return result;
//    }
}