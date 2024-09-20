package org.planning.SpringBootProject.repository;

import org.planning.SpringBootProject.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {
    @Query(value = "Select a From Account a WHERE a.userName = :userName")
    Account findByUsername(@Param("userName") String userName);

    @Query(value = "Select a From Account a WHERE a.id = :id")
    Account findByUserId(@Param("id") String id);

    @Modifying
    @Query(value = "UPDATE Account a SET a.encrytedPassword = :newPassword WHERE a.userName = :userName")
    void changePasswordAccount(@Param("newPassword") String newPassword, @Param("userName") String userName);

    @Modifying
    @Query("UPDATE Account a SET a.isDeleted = true, a.active = false, a.deletedAt = CURRENT_TIMESTAMP WHERE a.id = :id")
    void softDeleteAccount(@Param("id") String id);

    @Modifying
    @Query("UPDATE Account a SET a.isDeleted = false, a.active = true, a.updatedAt = CURRENT_TIMESTAMP WHERE a.id = :id")
    void activeAccount(@Param("id") String id);

    @Query("SELECT a FROM Account a WHERE a.userName LIKE %:query% OR a.fullName LIKE %:query% OR a.gmail LIKE %:query%")
    List<Account> searchAccount(String query);
}
