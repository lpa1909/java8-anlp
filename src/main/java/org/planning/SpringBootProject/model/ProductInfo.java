package org.planning.SpringBootProject.model;

import org.planning.SpringBootProject.entity.*;

public class ProductInfo {
    private String code;
    private String name;
    private double price;
    private int quanityProduct;

    public ProductInfo() {
    }

    public ProductInfo(Product product) {
        this.code = product.getCode();
        this.name = product.getName();
        this.price = product.getPrice();
        this.quanityProduct = product.getQuanityProduct();
    }

    // Sử dụng trong JPA/Hibernate query
    public ProductInfo(String code, String name, double price, int quanityProduct) {
        this.code = code;
        this.name = name;
        this.price = price;
        this.quanityProduct = quanityProduct;
    }

    public int getQuanityProduct() {
        return quanityProduct;
    }

    public void setQuanityProduct(int quanityProduct) {
        this.quanityProduct = quanityProduct;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}