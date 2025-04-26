package com.ijse.gdse72.therapycenter.bo.custom;

import com.ijse.gdse72.therapycenter.bo.SuperBO;
import com.ijse.gdse72.therapycenter.dto.TherapyProgramDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface TherapyProgramBO extends SuperBO {
    String getNextTherapyProgramId() throws SQLException;
    ArrayList<TherapyProgramDTO> getAllTherapyProgram() throws Exception;
    boolean saveTherapyProgram(TherapyProgramDTO therapyProgram) throws Exception;
    boolean updateTherapyProgram(TherapyProgramDTO therapyProgram) throws Exception;
    TherapyProgramDTO searchTherapyProgram(String id) throws Exception;
    boolean deleteTherapyProgram(String id) throws Exception;
}
