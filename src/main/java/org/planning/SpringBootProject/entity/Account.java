package org.planning.SpringBootProject.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "ACCOUNTS")
@Data
public class Account extends BaseEntity {
    private static final long serialVersionUID = -2054386655979281969L;
    public static final String ROLE_MANAGER = "MANAGER";
    public static final String ROLE_EMPLOYEE = "EMPLOYEE";
    @Column(name = "User_Name", length = 20, nullable = false)
    private String userName;
    @Column(name = "Encryted_Password", length = 128, nullable = false)
    private String encrytedPassword;
    @Column(name = "Active", length = 1, nullable = false)
    private boolean active;
    @Column(name = "User_Role", length = 20, nullable = false)
    private String userRole;
//    @Column(name = "createAt", length = 20)
//    private Date createdAt;
//    @Column(name = "updateAt", length = 20)
//    private Date updatedAt;
//    @Column(name = "deleteAt", length = 20)
//    private Date deletedAt;
//    @Column(name = "isDelete", length = 1, nullable = false)
//    private boolean isDelete;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEncrytedPassword() {
        return encrytedPassword;
    }

    public void setEncrytedPassword(String encrytedPassword) {
        this.encrytedPassword = encrytedPassword;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

//    public Date getCreatedAt() {
//        return createdAt;
//    }
//
//    public void setCreatedAt(Date createdAt) {
//        this.createdAt = createdAt;
//    }
//
//    public Date getDeletedAt() {
//        return deletedAt;
//    }
//
//    public void setDeletedAt(Date deletedAt) {
//        this.deletedAt = deletedAt;
//    }
//
//    public boolean isDelete() {
//        return isDelete;
//    }
//
//    public void setDelete(boolean delete) {
//        isDelete = delete;
//    }
//
//    public Date getUpdatedAt() {
//        return updatedAt;
//    }
//
//    public void setUpdatedAt(Date updatedAt) {
//        this.updatedAt = updatedAt;
//    }

    @Override
    public String toString() {
        return "[" + this.userName + "," + this.encrytedPassword + "," + this.userRole + "]";
    }
}
