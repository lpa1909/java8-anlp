package com.dd.anlp.bai12;

import com.dd.anlp.bai9.Product;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class FilterProductByQlty {
    public static void main(String[] args) {
        List<Product> products = getListProduct();
        List<Product> resProduct = filterByQuality(products);
        resProduct.forEach(product -> System.out.println(product.getName()));
    }

    public static List<Product> filterByQuality(List<Product> products){
        List<Product> res = products.stream().filter(productt -> productt.getQuanlity() > 0 && productt.isDelete() == true).collect(Collectors.toList());
        return res;
    }

    static List<Product> getListProduct(){
        LocalDate localDate = LocalDate.now();
        Date saleDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        List<Product> lProduct = Arrays.asList(
                new Product(1, "Iphone 13", 1, saleDate, 5, false),
                new Product(2, "S24 Ultra", 2, saleDate, 10, true),
                new Product(3, "S24", 2, saleDate, 8, true),
                new Product(4, "Zfold 3", 2, saleDate, 3, false),
                new Product(5, "Zflip 3", 2, saleDate, 2, true),
                new Product(6, "Iphone 15 Pro Max", 1, saleDate, 3, false),
                new Product(7, "Iphone 13 Mini", 1, saleDate, 5, false),
                new Product(8, "Macbook Air M1", 3, saleDate, 5, false),
                new Product(9, "Macbook Pro M1", 3, saleDate, 5, false),
                new Product(10, "IMac M2", 3, saleDate, 5, false)
        );
        return lProduct;
    }
}