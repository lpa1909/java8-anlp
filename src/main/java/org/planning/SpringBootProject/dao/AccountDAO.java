package org.planning.SpringBootProject.dao;

import jakarta.persistence.NoResultException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.planning.SpringBootProject.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    public void changePassword(String userName, String newEncryptedPassword) {
        try {
            Session session = this.sessionFactory.getCurrentSession();


            String hql = "UPDATE " + Account.class.getName() + " a SET a.encrytedPassword = :newPassword WHERE a.userName = :userName";
            Query query = session.createQuery(hql);
            query.setParameter("newPassword", newEncryptedPassword);
            query.setParameter("userName", userName);


            int rowsAffected = query.executeUpdate();
            System.out.println("success");
            if (rowsAffected == 0) {
                throw new UsernameNotFoundException("Not found account by username " + userName);
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}