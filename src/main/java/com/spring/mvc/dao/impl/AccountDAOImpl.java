package com.spring.mvc.dao.impl;

import com.spring.mvc.dao.AccountDAO;
import com.spring.mvc.entity.Account;
import jakarta.persistence.TypedQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(propagation = Propagation.REQUIRED)
public class AccountDAOImpl implements AccountDAO {

    private final SessionFactory sessionFactory;

    public AccountDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Account getAccountByID(int id) {

        return null;
    }

    @Override
    public Account getAccountByEmail(String email) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "from Account where email = :email";
        TypedQuery<Account> query = session.createQuery(hql, Account.class);
        query.setParameter("email", email);

        // Use getResultList() and check if the list is empty
        List<Account> results = query.getResultList();
        return results.isEmpty() ? null : results.get(0);
    }

}
