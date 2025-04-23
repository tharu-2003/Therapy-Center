package com.ijse.gdse72.therapycenter.bo.custom.impl;

import com.ijse.gdse72.therapycenter.bo.custom.PatientBO;
import com.ijse.gdse72.therapycenter.dao.DAOFactory;
import com.ijse.gdse72.therapycenter.dao.custom.PatientDAO;
import com.ijse.gdse72.therapycenter.dto.PatientDTO;
import com.ijse.gdse72.therapycenter.entity.Patient;

import java.util.ArrayList;

public class PatientBOImpl implements PatientBO {

    PatientDAO patientDAO = DAOFactory.getInstance().getDAO(DAOFactory.DaoType.PATIENT);

    @Override
    public ArrayList<PatientDTO> getAllPatients() throws Exception {
        ArrayList<PatientDTO> patientDTOS = new ArrayList<>();
        ArrayList<Patient> patients = (ArrayList<Patient>) patientDAO.getAll();

        for (Patient patient : patients) {
            patientDTOS.add(new PatientDTO(
                    patient.getId(),
                    patient.getName(),
                    patient.getMedicalHistory(),
                    patient.getContactNumber()
            ));
        }
        return patientDTOS;
    }

    @Override
    public boolean savePatient(PatientDTO patientDTO) throws Exception {
        return patientDAO.save(new Patient(
               patientDTO.getId(),
                patientDTO.getName(),
                patientDTO.getMedicalHistory(),
                patientDTO.getContactNumber()
        ));
    }

    @Override
    public boolean updatePatient(PatientDTO patientDTO) throws Exception {
        return patientDAO.update(new Patient(
                patientDTO.getId(),
                patientDTO.getName(),
                patientDTO.getMedicalHistory(),
                patientDTO.getContactNumber()
        ));
    }

    @Override
    public boolean deletePatient(String patientId) throws Exception {
        return patientDAO.delete(patientId);
    }
}
