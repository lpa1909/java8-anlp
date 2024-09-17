package org.planning.SpringBootProject.repository;

import org.planning.SpringBootProject.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, String> {
}
