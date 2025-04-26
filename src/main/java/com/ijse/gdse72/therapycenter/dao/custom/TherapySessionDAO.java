package com.ijse.gdse72.therapycenter.dao.custom;

import com.ijse.gdse72.therapycenter.bo.SuperBO;
import com.ijse.gdse72.therapycenter.dao.CrudDAO;
import com.ijse.gdse72.therapycenter.dto.TherapySessionDTO;
import com.ijse.gdse72.therapycenter.entity.TherapySession;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public interface TherapySessionDAO extends CrudDAO<TherapySession , String> {
    String getNextID() throws SQLException;
    boolean isTherapistAvailable(String therapistId, LocalDate date, LocalTime time) throws SQLException;
    TherapySessionDTO getSession(String sessionId) throws SQLException;
    ArrayList<String> getSessionId() throws SQLException;
}
