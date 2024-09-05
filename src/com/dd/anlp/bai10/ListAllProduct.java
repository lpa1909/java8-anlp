package com.dd.anlp.bai10;

import com.dd.anlp.bai9.Product;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class ListAllProduct {
    public static void main(String[] args) {
        List<Product> products = getListProduct();
        products.forEach(product -> System.out.println(product.getName()));
    }

    public static List<Product> getListProduct(){
        LocalDate localDate = LocalDate.now();
        Date saleDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        List<Product> lProduct = Arrays.asList(
                new Product(1, "Iphone 13", 1, saleDate, 5, false),
                new Product(2, "S24 Ultra", 2, saleDate, 10, true),
                new Product(3, "S24", 2, saleDate, 8, false),
                new Product(4, "Zfold 3", 2, saleDate, 3, true),
                new Product(5, "Zplip 3", 2, saleDate, 2, false),
                new Product(6, "Iphone 15 Pro Max", 1, saleDate, 3, false),
                new Product(7, "Iphone 13 Mini", 1, saleDate, 5, false),
                new Product(8, "Macbook Air M1", 3, saleDate, 5, false),
                new Product(9, "Macbook Pro M1", 3, saleDate, 5, true),
                new Product(10, "IMac M2", 3, saleDate, 0, false)
        );
        return lProduct;
    }
}