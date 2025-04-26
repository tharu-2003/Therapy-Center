package com.ijse.gdse72.therapycenter.dao.custom;

import com.ijse.gdse72.therapycenter.bo.SuperBO;
import com.ijse.gdse72.therapycenter.dao.CrudDAO;
import com.ijse.gdse72.therapycenter.entity.Therapist;

import java.sql.SQLException;
import java.util.ArrayList;

public interface TherapistDAO extends CrudDAO<Therapist , String> {

    String getNextID();
    ArrayList<String> getTherapist() throws SQLException;
}
