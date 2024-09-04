package com.dd.anlp.bai9;

import java.math.BigDecimal;
import java.util.Date;

public class Product {
    private Integer id;
    private String name;
    private Integer categoryId;
    private Date saleDate;
    private Integer quanlity;
    private boolean isDelete;

    public Product() {
    }

    public Product(Integer id, String name, Integer categoryId, Date saleDate, Integer quantity, boolean isDelete) {
        this.id = id;
        this.name = name;
        this.categoryId = categoryId;
        this.saleDate = saleDate;
        this.quanlity = quantity;
        this.isDelete = isDelete;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isDelete() {
        return isDelete;
    }

    public void setDelete(boolean delete) {
        isDelete = delete;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuanlity() {
        return quanlity;
    }

    public void setQuanlity(Integer quantity) {
        this.quanlity = quantity;
    }

    public Date getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(Date saleDate) {
        this.saleDate = saleDate;
    }
}
