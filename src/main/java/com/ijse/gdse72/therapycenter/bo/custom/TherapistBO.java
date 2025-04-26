package com.ijse.gdse72.therapycenter.bo.custom;

import com.ijse.gdse72.therapycenter.bo.SuperBO;
import com.ijse.gdse72.therapycenter.dto.TherapistDTO;

import java.util.ArrayList;

public interface TherapistBO extends SuperBO {
    TherapistDTO searchTherapist(String id) throws Exception;
    boolean deleteTherapist(String id) throws Exception;
    boolean updateTherapist(TherapistDTO therapistDTO) throws Exception;
    boolean saveTherapist(TherapistDTO therapistDTO) throws Exception;
    ArrayList<TherapistDTO> getAllTherapist() throws Exception;
    String getNextTherapistId() throws Exception;
}
