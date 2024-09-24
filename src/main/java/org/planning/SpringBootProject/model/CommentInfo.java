package org.planning.SpringBootProject.model;

import jakarta.persistence.Column;
import jakarta.persistence.Id;

import java.util.Date;

public class CommentInfo {

    private String id;
    private String details;
    private String star;
    private String userId;
    private String productId;
    private String fullName;
    private String userName;
    private Date createdAt;

    public CommentInfo(String details, String fullName, String id, String productId, String star, String userId, String userName) {
        this.details = details;
        this.fullName = fullName;
        this.id = id;
        this.productId = productId;
        this.star = star;
        this.userId = userId;
        this.userName = userName;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public CommentInfo() {
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
