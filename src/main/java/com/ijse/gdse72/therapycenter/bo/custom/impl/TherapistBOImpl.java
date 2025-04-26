package com.ijse.gdse72.therapycenter.bo.custom.impl;

import com.ijse.gdse72.therapycenter.bo.custom.TherapistBO;
import com.ijse.gdse72.therapycenter.dao.DAOFactory;
import com.ijse.gdse72.therapycenter.dao.custom.TherapistDAO;
import com.ijse.gdse72.therapycenter.dto.TherapistDTO;
import com.ijse.gdse72.therapycenter.entity.Therapist;

import java.util.ArrayList;

public class TherapistBOImpl implements TherapistBO {

    private final TherapistDAO therapistDAO = DAOFactory.getInstance().getDAO(DAOFactory.DaoType.THERAPIST);

    @Override
    public TherapistDTO searchTherapist(String id) throws Exception {
        Therapist therapist = therapistDAO.search(id);

        if (therapist == null) {
            return null;
        }
        return new TherapistDTO(
                therapist.getTherapistId(),
                therapist.getName(),
                therapist.getSpecialization(),
                therapist.getAvailability(),
                therapist.getContactNumber(),
                therapist.getAssignedProgram(),
                therapist.getEmail()
        );
    }

    @Override
    public boolean deleteTherapist(String id) throws Exception {
        return therapistDAO.delete(id);
    }

    @Override
    public boolean updateTherapist(TherapistDTO therapistDTO) throws Exception {
        return therapistDAO.update(new Therapist(
                therapistDTO.getTherapistId(),
                therapistDTO.getName(),
                therapistDTO.getSpecialization(),
                therapistDTO.getAvailability(),
                therapistDTO.getContactNumber(),
                therapistDTO.getAssignedProgram(),
                therapistDTO.getEmail()
        ));
    }

    @Override
    public boolean saveTherapist(TherapistDTO therapistDTO) throws Exception {
        return therapistDAO.save( new Therapist(
                therapistDTO.getTherapistId(),
                therapistDTO.getName(),
                therapistDTO.getSpecialization(),
                therapistDTO.getAvailability(),
                therapistDTO.getContactNumber(),
                therapistDTO.getAssignedProgram(),
                therapistDTO.getEmail()
        ));
    }

    @Override
    public ArrayList<TherapistDTO> getAllTherapist() throws Exception {
        ArrayList<TherapistDTO> therapistDTOS = new ArrayList<>();
        ArrayList<Therapist> therapists = (ArrayList<Therapist>) therapistDAO.getAll();

        for (Therapist therapist : therapists) {
            therapistDTOS.add(new TherapistDTO(
                    therapist.getTherapistId(),
                    therapist.getName(),
                    therapist.getSpecialization(),
                    therapist.getAvailability(),
                    therapist.getContactNumber(),
                    therapist.getAssignedProgram(),
                    therapist.getEmail()
            ));
        }
        return therapistDTOS;
    }

    @Override
    public String getNextTherapistId() throws Exception {
        return "";
    }
}
