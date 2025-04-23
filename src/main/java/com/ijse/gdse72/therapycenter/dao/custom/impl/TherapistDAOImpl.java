package com.ijse.gdse72.therapycenter.dao.custom.impl;

import com.ijse.gdse72.therapycenter.dao.custom.TherapistDAO;
import org.hibernate.Session;

import java.util.List;

public class TherapistDAOImpl implements TherapistDAO {
    @Override
    public boolean save(Object entity) throws Exception {
        return false;
    }

    @Override
    public boolean update(Object entity) throws Exception {
        return false;
    }

    @Override
    public boolean delete(Object o) throws Exception {
        return false;
    }

    @Override
    public Object search(Object o) throws Exception {
        return null;
    }

    @Override
    public List getAll() throws Exception {
        return List.of();
    }

    @Override
    public void setSession(Session session) throws Exception {

    }
}
