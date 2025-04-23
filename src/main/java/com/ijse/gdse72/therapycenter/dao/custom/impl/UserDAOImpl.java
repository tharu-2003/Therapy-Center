package com.ijse.gdse72.therapycenter.dao.custom.impl;

import com.ijse.gdse72.therapycenter.config.FactoryConfiguration;
import com.ijse.gdse72.therapycenter.dao.custom.UserDAO;
import com.ijse.gdse72.therapycenter.entity.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class UserDAOImpl implements UserDAO {

    @Override
    public boolean save(User entity) throws Exception {
        Transaction transaction = null;
        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(User entity) throws Exception {
        return false;
    }

    @Override
    public boolean delete(String s) throws Exception {
        return false;
    }

    @Override
    public User search(String s) throws Exception {
        return null;
    }

    @Override
    public List getAll() throws Exception {
        return List.of();
    }

    @Override
    public void setSession(Session session) throws Exception {

    }

    @Override
    public User findByUsername(String username) throws Exception {
        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            String hql = "FROM User WHERE userName = :username";
            Query<User> query = session.createQuery(hql, User.class);
            query.setParameter("username", username);
            return query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
