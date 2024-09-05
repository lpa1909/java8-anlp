package com.dd.anlp.bai11;

import com.dd.anlp.bai10.ListAllProduct;
import com.dd.anlp.bai9.Product;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class FilterProduct {
    public static void main(String[] args) {
        ListAllProduct listAllProduct = new ListAllProduct();
        List<Product> products = listAllProduct.getListProduct();
//        products.forEach(product -> System.out.println(product.getName()));
        Product resProcuct = filterProductByID(products, 8);
        System.out.println(resProcuct.getName());
    }
    // filter product by stream
//    public static Product filterProductByID(List<Product> products, Integer productID) {
//        Product product = products.stream().filter(productt -> productt.getId() == productID).findAny().orElse(null);
//        return product;
//    }

    // filter product don't use stream
    public static Product filterProductByID(List<Product> products, Integer productID) {
        for(Product product : products){
            if(product.getId() == productID){
                return product;
            }
        }
        return null;
    }

}
