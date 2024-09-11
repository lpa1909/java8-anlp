package org.planning.SpringBootProject.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.planning.SpringBootProject.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class AccountDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public Account findAccount(String username) {
        Session session = this.sessionFactory.getCurrentSession();
        return session.find(Account.class, username);
    }
}