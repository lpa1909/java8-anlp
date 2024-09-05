package com.dd.anlp.bai14;

import com.dd.anlp.bai10.ListAllProduct;
import com.dd.anlp.bai9.Product;

import java.util.List;

public class TotalProduct{
    public static void main(String[] args) {
        ListAllProduct listAllProduct = new ListAllProduct();
        List<Product> products = listAllProduct.getListProduct();
        int totalProduct = getTotalProduct(products);
        System.out.println(totalProduct);
    }

    // totalProduct don't use stream
//    static int getTotalProduct(List<Product> products){
//        int totalProduct = 0;
//        for(Product p : products){
//            if(p.isDelete() == false){
//                totalProduct += p.getQuanlity();
//            }
//        }
//        return totalProduct;
//    }

    // totalProduct use stream
    static int getTotalProduct(List<Product> products){
        return products.stream().filter(product -> !product.isDelete()).map(Product::getQuanlity).reduce(0, Integer::sum);
    }
}