package org.planning.SpringBootProject.repository;

import org.planning.SpringBootProject.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {
    @Query(value = "Select a From Account a WHERE a.userName = :userName")
    Account findByUsername(@Param("userName") String userName);

    @Modifying
    @Query(value = "UPDATE Account a SET a.encrytedPassword = :newPassword WHERE a.userName = :userName")
    void changePasswordAccount(@Param("newPassword") String newPassword, @Param("userName") String userName);
}
