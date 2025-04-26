package com.ijse.gdse72.therapycenter.dao.custom.impl;

import com.ijse.gdse72.therapycenter.config.FactoryConfiguration;
import com.ijse.gdse72.therapycenter.dao.custom.PatientDAO;
import com.ijse.gdse72.therapycenter.dto.PatientDTO;
import com.ijse.gdse72.therapycenter.entity.Patient;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class PatientDAOImpl implements PatientDAO {

    @Override
    public boolean save(Patient entity) throws Exception {
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
    public boolean update(Patient entity) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try {

            Patient existingPatient = session.get(Patient.class, entity.getId());
            if (existingPatient == null) {
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
            Query query = session.createQuery("DELETE FROM Patient WHERE id = :patientId");
            query.setParameter("patientId", id);
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
    public Patient search(String id) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            Patient patient = session.get(Patient.class, id);
            return patient;
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
    public List<Patient> getAll() throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            List<Patient> patients = session.createQuery("FROM Patient", Patient.class).list();
            return patients;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }    @Override
    public void setSession(Session session) throws Exception {

    }

    @Override
    public String getNextId() {
        return "";
    }

    @Override
    public PatientDTO getPatient(String patientId) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try {

//            Patient patient = session.createQuery("FROM Patient ORDER BY id DESC", Patient.class)
//                    .setMaxResults(1)
//                    .uniqueResult();


            Patient patient = session.createQuery(
                            "FROM Patient WHERE id = :patientId", Patient.class)
                    .setParameter("patientId", patientId)
                    .uniqueResult();

            transaction.commit();
            session.close();

            if (patient != null) {
                return new PatientDTO(
                        patient.getId(),
                        patient.getName(),
                        patient.getMedicalHistory(),
                        patient.getContactNumber()
                );
            } else {
                return null;
            }

        } catch (Exception e) {
            transaction.rollback();
            session.close();
            throw e;
        }
    }
}
