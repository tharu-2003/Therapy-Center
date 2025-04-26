package com.ijse.gdse72.therapycenter.bo.custom;

import com.ijse.gdse72.therapycenter.bo.SuperBO;
import com.ijse.gdse72.therapycenter.dto.TherapySessionDTO;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public interface TherapySessionBO extends SuperBO {
    String getNextTherapySessionId() throws Exception;
    boolean isTherapistAvailable(String therapistName, LocalDate date, LocalTime time) throws Exception;
    ArrayList<TherapySessionDTO> getAllTherapySession() throws Exception;
    boolean saveTherapySession(TherapySessionDTO therapySessionDTO) throws Exception;
    boolean updateTherapySession(TherapySessionDTO therapySessionDTO) throws Exception;
    TherapySessionDTO searchTherapySession(String sessionId) throws Exception;
    boolean deleteTherapySession(String sessionId) throws Exception;
}
