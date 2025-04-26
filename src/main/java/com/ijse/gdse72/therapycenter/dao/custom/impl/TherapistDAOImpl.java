package com.ijse.gdse72.therapycenter.dao.custom.impl;

import com.ijse.gdse72.therapycenter.config.FactoryConfiguration;
import com.ijse.gdse72.therapycenter.dao.custom.TherapistDAO;
import com.ijse.gdse72.therapycenter.entity.Therapist;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TherapistDAOImpl implements TherapistDAO {
    @Override
    public String getNextID() {
        return "";
    }

    @Override
    public ArrayList<String> getTherapist() throws SQLException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = null;
        ArrayList<String> therapistIds = new ArrayList<>();

        try {
            transaction = session.beginTransaction();

            Query<String> query = session.createQuery("SELECT t.name FROM Therapist t", String.class);
            therapistIds = (ArrayList<String>) query.getResultList();

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

        return therapistIds;
    }

    @Override
    public boolean save(Therapist entity) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.persist(entity);
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
            throw e;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public boolean update(Therapist entity) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try {

            Therapist existingTherapist = session.get(Therapist.class, entity.getTherapistId());
            if (existingTherapist == null) {
                return false;
            }

            session.merge(entity);
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
            throw e;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public boolean delete(String id) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try {
            Query query = session.createQuery("DELETE FROM Therapist WHERE therapistId = :therapistId");
            query.setParameter("therapistId", id);
            int result = query.executeUpdate();

            transaction.commit();
            return result > 0;
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
            throw e;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Therapist search(String id) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            Therapist therapist = session.get(Therapist.class, id);
            return therapist;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<Therapist> getAll() throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            List<Therapist> therapists = session.createQuery("FROM Therapist ", Therapist.class).list();
            return therapists;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void setSession(Session session) throws Exception {

    }
}
