package com.ijse.gdse72.therapycenter.controller;

import com.ijse.gdse72.therapycenter.bo.BOFactory;
import com.ijse.gdse72.therapycenter.bo.custom.TherapySessionBO;
import com.ijse.gdse72.therapycenter.dao.custom.PatientDAO;
import com.ijse.gdse72.therapycenter.dao.custom.TherapistDAO;
import com.ijse.gdse72.therapycenter.dao.custom.TherapyProgramDAO;
import com.ijse.gdse72.therapycenter.dao.custom.impl.PatientDAOImpl;
import com.ijse.gdse72.therapycenter.dao.custom.impl.TherapistDAOImpl;
import com.ijse.gdse72.therapycenter.dao.custom.impl.TherapyProgramDAOImpl;
import com.ijse.gdse72.therapycenter.dto.PatientDTO;
import com.ijse.gdse72.therapycenter.dto.TherapySessionDTO;
import com.ijse.gdse72.therapycenter.dto.tm.TherapySessionTM;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.input.MouseEvent;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class TherapySessionController {

    @FXML
    private Button btnBook;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnLoad;

    @FXML
    private Button btnRefresh;

    @FXML
    private Button btnReschedule;

    @FXML
    private Button btnSearch;

    @FXML
    private ComboBox<String> cmbDuration;

    @FXML
    private ComboBox<String> cmbProgram;

    @FXML
    private ComboBox<String> cmbStatus;

    @FXML
    private ComboBox<String> cmbTherapist;

    @FXML
    private ComboBox<String> cmbTimeSlot;

    @FXML
    private TableColumn<TherapySessionTM , Date> colDate;

    @FXML
    private TableColumn<TherapySessionTM, String> colDuration;

    @FXML
    private TableColumn<TherapySessionTM, String> colPatientName;

    @FXML
    private TableColumn<TherapySessionTM, String> colProgram;

    @FXML
    private TableColumn<TherapySessionTM, String> colSessionId;

    @FXML
    private TableColumn<TherapySessionTM, String> colStatus;

    @FXML
    private TableColumn<TherapySessionTM, String> colTherapist;

    @FXML
    private TableColumn<TherapySessionTM , Time> colTime;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TableView<TherapySessionTM> tblAppointments;

    @FXML
    private TextField txtPatientId;

    @FXML
    private TextField txtPatientName;

    @FXML
    private TextField txtSessionId;

    private final TherapySessionBO THERAPYSESSIONBO = (TherapySessionBO) BOFactory.getInstance().getBO(BOFactory.BOType.THERAPY_SESSION);

    public void initialize() {
        try {
            populateComboBoxes();
            loadTableData();
            visibleData();
            txtSessionId.setText(THERAPYSESSIONBO.getNextTherapySessionId());
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load page: " + e.getMessage()).show();
        }
    }

    private void visibleData() {
        colSessionId.setCellValueFactory(new PropertyValueFactory<>("sessionId"));
        colPatientName.setCellValueFactory(new PropertyValueFactory<>("patientName"));
        colTherapist.setCellValueFactory(new PropertyValueFactory<>("therapistId"));
        colProgram.setCellValueFactory(new PropertyValueFactory<>("program"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("sessionDate"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        colDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    private void loadTableData() throws Exception {
        ObservableList<TherapySessionTM> sessionList = FXCollections.observableArrayList();
        for (TherapySessionDTO dto : THERAPYSESSIONBO.getAllTherapySession()) {
            sessionList.add(new TherapySessionTM(
                    dto.getSessionId(),
                    dto.getPatientId(),
                    dto.getPatientName(),
                    dto.getTherapistId(),
                    dto.getProgram(),
                    dto.getSessionDate(),
                    dto.getTime(),
                    dto.getDuration(),
                    dto.getStatus()
            ));
        }
        tblAppointments.setItems(sessionList);
    }

    private void refreshPage() {
        try {
            txtSessionId.setText(THERAPYSESSIONBO.getNextTherapySessionId());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        txtPatientId.clear();
        txtPatientName.clear();
        cmbTherapist.setValue(null);
        cmbStatus.setValue(null);
        cmbDuration.setValue(null);
        cmbProgram.setValue(null);
        datePicker.setValue(null);
        cmbTimeSlot.setValue(null);
    }

    private void populateComboBoxes() throws SQLException {
        // Therapist
        TherapistDAO therapistDAO = new TherapistDAOImpl();
        cmbTherapist.setItems(FXCollections.observableArrayList(therapistDAO.getTherapist()));

        // Program
        TherapyProgramDAO therapyProgramDAO = new TherapyProgramDAOImpl();
        cmbProgram.setItems(FXCollections.observableArrayList(therapyProgramDAO.getPrograms()));

        // Time slots
        List<String> timeSlots = new ArrayList<>();
        LocalTime start = LocalTime.of(9, 0);
        LocalTime end = LocalTime.of(17, 0);
        while (!start.isAfter(end)) {
            timeSlots.add(start.format(DateTimeFormatter.ofPattern("hh:mm a")));
            start = start.plusMinutes(30);
        }
        cmbTimeSlot.setItems(FXCollections.observableArrayList(timeSlots));

        // Duration
        cmbDuration.setItems(FXCollections.observableArrayList(
                "15 mins", "30 mins", "45 mins", "1 hour", "1 hour 30 mins", "2 hours"
        ));

        // Status
        cmbStatus.setItems(FXCollections.observableArrayList(
                "Available", "Unavailable"
        ));
    }


    @FXML
    void bookOnAction(ActionEvent event) {
        try {
            String sessionId = txtSessionId.getText();
            String patientId = txtPatientId.getText();
            String patientName = txtPatientName.getText();
            String therapistId = cmbTherapist.getValue();
            String program = cmbProgram.getValue();
            LocalDate date = datePicker.getValue();
            String selectedTime = cmbTimeSlot.getValue();
            String duration = cmbDuration.getValue();
            String status = cmbStatus.getValue();

            if (sessionId.isEmpty() || patientId.isEmpty() || therapistId == null || program == null ||
                    patientName.isEmpty() || date == null || selectedTime == null || duration == null || status == null) {
                new Alert(Alert.AlertType.WARNING, "Please fill all the fields correctly!").show();
                return;
            }

            LocalTime time = LocalTime.parse(selectedTime, DateTimeFormatter.ofPattern("hh:mm a"));

            TherapySessionDTO dto = new TherapySessionDTO(
                    sessionId, patientId, patientName, therapistId, program, date, time, duration, status
            );

            if (THERAPYSESSIONBO.saveTherapySession(dto)) {
                new Alert(Alert.AlertType.INFORMATION, "Session booked successfully!").show();
                loadTableData();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to book session!").show();
            }

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage()).show();
        }
    }

    @FXML
    void cancelOnAction(ActionEvent event) {
        TherapySessionTM selectedTherapySession = tblAppointments.getSelectionModel().getSelectedItem();

        if (selectedTherapySession != null) {
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Confirm Deletion");
            confirmAlert.setHeaderText(null);
            confirmAlert.setContentText("Are you sure you want to delete Session ID: " + selectedTherapySession.getSessionId() + "?");

            Optional<ButtonType> result = confirmAlert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    boolean isDeleted = THERAPYSESSIONBO.deleteTherapySession(selectedTherapySession.getSessionId());

                    if (isDeleted) {
                        new Alert(Alert.AlertType.INFORMATION, "Therapy Program deleted successfully!").show();
                        loadTableData();
                        refreshPage();
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Failed to delete the Therapy Program!").show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    new Alert(Alert.AlertType.ERROR, "Database error: " + e.getMessage()).show();
                }
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Please select a Therapy Program to delete.").show();
        }
    }

    @FXML
    void loadOnAction(ActionEvent event) {
        try {

            PatientDAO patientDAO = new PatientDAOImpl();
            PatientDTO patient = patientDAO.getPatient(txtPatientId.getText());
            if (patient != null) {
                txtPatientId.setText(patient.getId());
                txtPatientName.setText(patient.getName());
            } else {
                new Alert(Alert.AlertType.WARNING, "No patient found!").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load patient: " + e.getMessage()).show();
        }
    }

    @FXML
    void tableClickOnAction(MouseEvent event) {
        TherapySessionTM selected = tblAppointments.getSelectionModel().getSelectedItem();
        if (selected != null) {
            txtSessionId.setText(selected.getSessionId());
            txtPatientId.setText(selected.getPatientId());
            txtPatientName.setText(selected.getPatientName());
            cmbTherapist.setValue(selected.getTherapistId());
            cmbProgram.setValue(selected.getProgram());
            cmbStatus.setValue(selected.getStatus());
            cmbDuration.setValue(selected.getDuration());
            datePicker.setValue(selected.getSessionDate());
            cmbTimeSlot.setValue(selected.getTime().format(DateTimeFormatter.ofPattern("hh:mm a")));
        }
    }

    @FXML
    void refreshOnAction(ActionEvent event) {
        refreshPage();
    }

    @FXML
    void rescheduleOnAction(ActionEvent event) {
        try {
            String sessionId = txtSessionId.getText();
            String patientId = txtPatientId.getText();
            String patientName = txtPatientName.getText();
            String therapistId = cmbTherapist.getValue();
            String program = cmbProgram.getValue();
            LocalDate date = datePicker.getValue();
            String selectedTime = cmbTimeSlot.getValue();
            String duration = cmbDuration.getValue();
            String status = cmbStatus.getValue();

            if (sessionId.isEmpty() || patientId.isEmpty() || therapistId == null || program == null ||
                    patientName.isEmpty() || date == null || selectedTime == null || duration == null || status == null) {
                new Alert(Alert.AlertType.WARNING, "Please fill all the fields correctly!").show();
                return;
            }

            LocalTime time = LocalTime.parse(selectedTime, DateTimeFormatter.ofPattern("hh:mm a"));

            TherapySessionDTO dto = new TherapySessionDTO(
                    sessionId, patientId, patientName, therapistId, program, date, time, duration, status
            );

            if (THERAPYSESSIONBO.updateTherapySession(dto)) {
                new Alert(Alert.AlertType.INFORMATION, "Session ReScheduled successfully!").show();
                loadTableData();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to ReScheduled session!").show();
            }

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage()).show();
        }
    }

    @FXML
    void searchOnAction(ActionEvent event) {
        String sessionId = txtSessionId.getText();

        if (!sessionId.isEmpty()) {
            try{
                TherapySessionDTO therapySessionDTO = THERAPYSESSIONBO.searchTherapySession(sessionId);

                if (therapySessionDTO != null) {
                    txtSessionId.setText(therapySessionDTO.getSessionId());
                    txtPatientId.setText(therapySessionDTO.getPatientId());
                    txtPatientName.setText(therapySessionDTO.getPatientName());
                    cmbTherapist.setValue(therapySessionDTO.getTherapistId());
                    cmbProgram.setValue(therapySessionDTO.getProgram());
                    cmbStatus.setValue(therapySessionDTO.getStatus());
                    cmbDuration.setValue(therapySessionDTO.getDuration());
                    datePicker.setValue(therapySessionDTO.getSessionDate());
                    cmbTimeSlot.setValue(therapySessionDTO.getTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")));


                }else {
                    new Alert(Alert.AlertType.WARNING, "Therapy _ Session Not Found!").show();
                }
            }catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "An error occurred while searching!").show();
            }
        }else {
            new Alert(Alert.AlertType.WARNING, "Please enter a Therapy_Session ID to search!").show();
        }
    }
}
