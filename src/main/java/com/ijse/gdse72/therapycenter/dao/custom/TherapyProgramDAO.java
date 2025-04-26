package com.ijse.gdse72.therapycenter.dao.custom;

import com.ijse.gdse72.therapycenter.bo.SuperBO;
import com.ijse.gdse72.therapycenter.dao.CrudDAO;
import com.ijse.gdse72.therapycenter.entity.TherapyProgram;

import java.sql.SQLException;
import java.util.ArrayList;

public interface TherapyProgramDAO extends CrudDAO<TherapyProgram , String> {
    String getNextID() throws SQLException;
    ArrayList<String> getPrograms() throws SQLException;
}
