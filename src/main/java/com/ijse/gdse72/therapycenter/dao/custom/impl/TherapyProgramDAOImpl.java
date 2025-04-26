package com.ijse.gdse72.therapycenter.dao.custom.impl;

import com.ijse.gdse72.therapycenter.config.FactoryConfiguration;
import com.ijse.gdse72.therapycenter.dao.custom.TherapyProgramDAO;
import com.ijse.gdse72.therapycenter.entity.TherapyProgram;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TherapyProgramDAOImpl implements TherapyProgramDAO {


    @Override
    public boolean save(TherapyProgram entity) throws Exception {
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
    public boolean update(TherapyProgram entity) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try {

            TherapyProgram existingTherapyProgram = session.get(TherapyProgram.class, entity.getProgramId());
            if (existingTherapyProgram == null) {
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
            Query query = session.createQuery("DELETE FROM TherapyProgram WHERE programId = :programId");
            query.setParameter("programId", id);
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
    public TherapyProgram search(String id) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            TherapyProgram therapyProgram = session.get(TherapyProgram.class, id);
            return therapyProgram;
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
    public List<TherapyProgram> getAll() throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            List<TherapyProgram> therapyPrograms = session.createQuery("FROM TherapyProgram ", TherapyProgram.class).list();
            return therapyPrograms;
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

    @Override
    public String getNextID() throws SQLException {
        return "";
    }

    @Override
    public ArrayList<String> getPrograms() throws SQLException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = null;
        ArrayList<String> therapyProgram = new ArrayList<>();

        try {
            transaction = session.beginTransaction();

            Query<String> query = session.createQuery("SELECT tp.name FROM TherapyProgram tp", String.class);
            therapyProgram = (ArrayList<String>) query.getResultList();

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

        return therapyProgram;
    }
}
