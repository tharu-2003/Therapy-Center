package com.ijse.gdse72.therapycenter.bo.custom.impl;

import com.ijse.gdse72.therapycenter.bo.custom.TherapySessionBO;
import com.ijse.gdse72.therapycenter.dao.DAOFactory;
import com.ijse.gdse72.therapycenter.dao.custom.TherapySessionDAO;
import com.ijse.gdse72.therapycenter.dto.TherapySessionDTO;
import com.ijse.gdse72.therapycenter.entity.TherapySession;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class TherapySessionBOImpl implements TherapySessionBO {

    TherapySessionDAO THERAPYSESSIONDAO = DAOFactory.getInstance().getDAO(DAOFactory.DaoType.THERAPY_SESSION);

    @Override
    public String getNextTherapySessionId() throws Exception {
        return THERAPYSESSIONDAO.getNextID();
    }

    @Override
    public boolean isTherapistAvailable(String therapistName, LocalDate date, LocalTime time) throws Exception {
        return false;
    }

    @Override
    public ArrayList<TherapySessionDTO> getAllTherapySession() throws Exception {
        ArrayList<TherapySessionDTO> therapySessionDTOS = new ArrayList<>();
        ArrayList<TherapySession> therapySessions = (ArrayList<TherapySession>) THERAPYSESSIONDAO.getAll();

        for (TherapySession therapySession : therapySessions) {
            therapySessionDTOS.add(new TherapySessionDTO(
                    therapySession.getSessionId(),
                    therapySession.getPatientId(),
                    therapySession.getPatientName(),
                    therapySession.getTherapistId(),
                    therapySession.getProgram(),
                    therapySession.getSessionDate(),
                    therapySession.getTime(),
                    therapySession.getDuration(),
                    therapySession.getStatus()
            ));
        }
        return therapySessionDTOS;
    }

    @Override
    public boolean saveTherapySession(TherapySessionDTO therapySessionDTO) throws Exception {
        return THERAPYSESSIONDAO.save(
                new TherapySession(
                        therapySessionDTO.getSessionId(),
                        therapySessionDTO.getPatientId(),
                        therapySessionDTO.getPatientName(),
                        therapySessionDTO.getTherapistId(),
                        therapySessionDTO.getProgram(),
                        therapySessionDTO.getSessionDate(),
                        therapySessionDTO.getTime(),
                        therapySessionDTO.getDuration(),
                        therapySessionDTO.getStatus()
                )
        );
    }

    @Override
    public boolean updateTherapySession(TherapySessionDTO therapySessionDTO) throws Exception {
        return THERAPYSESSIONDAO.update(
                new TherapySession(
                        therapySessionDTO.getSessionId(),
                        therapySessionDTO.getPatientId(),
                        therapySessionDTO.getPatientName(),
                        therapySessionDTO.getTherapistId(),
                        therapySessionDTO.getProgram(),
                        therapySessionDTO.getSessionDate(),
                        therapySessionDTO.getTime(),
                        therapySessionDTO.getDuration(),
                        therapySessionDTO.getStatus()
                )
        );
    }

    @Override
    public TherapySessionDTO searchTherapySession(String sessionId) throws Exception {
        TherapySession therapySession = THERAPYSESSIONDAO.search(sessionId);

        if (therapySession == null) {
            return null;
        }
        return new TherapySessionDTO(
                therapySession.getSessionId(),
                therapySession.getPatientId(),
                therapySession.getPatientName(),
                therapySession.getTherapistId(),
                therapySession.getProgram(),
                therapySession.getSessionDate(),
                therapySession.getTime(),
                therapySession.getDuration(),
                therapySession.getStatus()
        );
    }

    @Override
    public boolean deleteTherapySession(String sessionId) throws Exception {
        return THERAPYSESSIONDAO.delete(sessionId);
    }
}
