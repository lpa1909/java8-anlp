package org.planning.SpringBootProject.form;

import org.planning.SpringBootProject.entity.Product;
import org.springframework.web.multipart.MultipartFile;

public class ProductForm{
    private String code;
    private String name;
    private double price;

    private boolean newProduct = false;

    private MultipartFile fileData;

    public ProductForm(){

    }

    public ProductForm(Product product){
        this.code = product.getCode();
        this.name = product.getName();
        this.price = product.getPrice();
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isNewProduct() {
        return newProduct;
    }

    public void setNewProduct(boolean newProduct) {
        this.newProduct = newProduct;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MultipartFile getFileData() {
        return fileData;
    }

    public void setFileData(MultipartFile fileData) {
        this.fileData = fileData;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}