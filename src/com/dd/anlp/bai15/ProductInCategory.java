package com.dd.anlp.bai15;

import com.dd.anlp.bai10.ListAllProduct;
import com.dd.anlp.bai9.Product;

import java.util.List;

public class ProductInCategory {
    public static void main(String[] args) {
        ListAllProduct listAllProduct = new ListAllProduct();
        List<Product> products = listAllProduct.getListProduct();
        System.out.println(isHavaProductInCategory(products, 1));
    }
    // don't use stream
//    static boolean isHavaProductInCategory(List<Product> products, Integer categoryId){
//        for(Product p : products){
//            if(p.getCategoryId() == categoryId){
//                return true;
//            }
//        }
//        return false;
//    }

    // use stream
    static boolean isHavaProductInCategory(List<Product> products, Integer categoryId){
        boolean res = products.stream().anyMatch(product -> product.getCategoryId() == categoryId);
        return res;
    }
}