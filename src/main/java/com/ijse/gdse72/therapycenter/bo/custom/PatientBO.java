package com.ijse.gdse72.therapycenter.bo.custom;

import com.ijse.gdse72.therapycenter.bo.SuperBO;
import com.ijse.gdse72.therapycenter.dto.PatientDTO;
import com.ijse.gdse72.therapycenter.entity.Patient;

import java.util.ArrayList;

public interface PatientBO extends SuperBO {
    ArrayList<PatientDTO> getAllPatients() throws Exception;
    boolean savePatient(PatientDTO patientDTO) throws Exception;
    boolean updatePatient(PatientDTO patientDTO) throws Exception;
    boolean deletePatient(String patientId) throws Exception;
}
