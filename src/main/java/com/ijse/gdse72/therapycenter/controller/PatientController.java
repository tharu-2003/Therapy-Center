package com.ijse.gdse72.therapycenter.controller;

import com.ijse.gdse72.therapycenter.bo.BOFactory;
import com.ijse.gdse72.therapycenter.bo.custom.PatientBO;
import com.ijse.gdse72.therapycenter.dto.PatientDTO;
import com.ijse.gdse72.therapycenter.dto.tm.PatientTM;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.Optional;

public class PatientController {

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnRefresh;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<PatientTM , Integer> colContact;

    @FXML
    private TableColumn<PatientTM , String> colMedicalHistory;

    @FXML
    private TableColumn<PatientTM , String> colName;

    @FXML
    private TableColumn<PatientTM , String> colPatientId;

    @FXML
    private ImageView imgSearch;

    @FXML
    private TableView<PatientTM> tblPatients;

    @FXML
    private TextArea txtMedicalHistory;

    @FXML
    private TextField txtPatientContact;

    @FXML
    private TextField txtPatientId;

    @FXML
    private TextField txtPatientName;

    private final PatientBO PATIENTBO = (PatientBO) BOFactory.getInstance().getBO(BOFactory.BOType.PATIENT);

    public void initialize() {
        try {
            loadTableData();
            visibleData();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void loadTableData() throws Exception {
        ArrayList<PatientDTO> patientDTOS = PATIENTBO.getAllPatients();
        ObservableList<PatientTM> patientTMS = FXCollections.observableArrayList();

        for (PatientDTO patientDTO : patientDTOS) {
            PatientTM patientTM = new PatientTM(
                    patientDTO.getId(),
                    patientDTO.getName(),
                    patientDTO.getMedicalHistory(),
                    patientDTO.getContactNumber()
            );
            patientTMS.add(patientTM);
        }
        tblPatients.setItems(patientTMS);
    }

    private void visibleData() throws Exception {
        colPatientId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contactNumber"));
        colMedicalHistory.setCellValueFactory(new PropertyValueFactory<>("medicalHistory"));
    }

    @FXML
    void deleteOnAction(ActionEvent event) {
        PatientTM selectedPatient = tblPatients.getSelectionModel().getSelectedItem();

        if (selectedPatient != null) {
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Confirm Deletion");
            confirmAlert.setHeaderText(null);
            confirmAlert.setContentText("Are you sure you want to delete patient ID: " + selectedPatient.getId() + "?");

            Optional<ButtonType> result = confirmAlert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    boolean isDeleted = PATIENTBO.deletePatient(selectedPatient.getId());

                    if (isDeleted) {
                        new Alert(Alert.AlertType.INFORMATION, "Patient deleted successfully!").show();
                        loadTableData();
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Failed to delete the patient!").show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    new Alert(Alert.AlertType.ERROR, "Database error: " + e.getMessage()).show();
                }
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Please select a patient to delete.").show();
        }
    }

    @FXML
    void refreshOnAction(ActionEvent event) {
        refreshTable();
    }

    void refreshTable() {
        txtPatientId.clear();
        txtPatientName.clear();
        txtPatientContact.clear();
        txtMedicalHistory.clear();
    }

    @FXML
    void saveOnAction(ActionEvent event) {
        try {
            if (isValidInput()) {
                String patientId = txtPatientId.getText();
                String name = txtPatientName.getText();
                String medicalHistory = txtMedicalHistory.getText();
                int contact = Integer.parseInt(txtPatientContact.getText());

                PatientDTO patientDTO = new PatientDTO(patientId, name, medicalHistory, contact);

                boolean isSaved = PATIENTBO.savePatient(patientDTO);
                if (isSaved) {
                    new Alert(Alert.AlertType.INFORMATION, "Patient Saved Successfully!").show();
                    loadTableData();
                    refreshTable();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to Save Patient!").show();
                }
            }
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Please enter valid numeric values for Contact Number!").show();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void searchOnAction(MouseEvent event) {
        String patientId = txtPatientId.getText();

        if (!patientId.isEmpty()) {
            try {
                PatientDTO patientDTO = PATIENTBO.searchPatient(patientId);

                if (patientDTO != null) {
                    txtPatientId.setText(patientDTO.getId());
                    txtPatientName.setText(patientDTO.getName());
                    txtMedicalHistory.setText(patientDTO.getMedicalHistory());
                    txtPatientContact.setText(String.valueOf(patientDTO.getContactNumber()));
                } else {
                    new Alert(Alert.AlertType.WARNING, "Patient Not Found!").show();
                }

            } catch (Exception e) {
                new Alert(Alert.AlertType.WARNING, "Patient Not Found!").show();
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Please enter a Patient ID to search!").show();
        }
    }

    @FXML
    void tableClickOnAction(MouseEvent event) {
        PatientTM selectedPatient = tblPatients.getSelectionModel().getSelectedItem();

        if (selectedPatient != null) {
            txtPatientId.setText(selectedPatient.getId());
            txtPatientName.setText(selectedPatient.getName());
            txtMedicalHistory.setText(selectedPatient.getMedicalHistory());
            txtPatientContact.setText(String.valueOf(selectedPatient.getContactNumber()));
        }
    }

    @FXML
    void updateOnAction(ActionEvent event) {
        try {
            if (isValidInput()) {
                String patientId = txtPatientId.getText();
                String name = txtPatientName.getText();
                String medicalHistory = txtMedicalHistory.getText();
                int contact = Integer.parseInt(txtPatientContact.getText());

                PatientDTO patientDTO = new PatientDTO(patientId, name, medicalHistory, contact);

                boolean isUpdated = PATIENTBO.updatePatient(patientDTO);
                if (isUpdated) {
                    new Alert(Alert.AlertType.INFORMATION, "Patient updated Successfully!").show();
                    loadTableData();
                    refreshTable();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to update Patient!").show();
                }
            }
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Please enter valid numeric values for Contact Number!").show();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    // --------- Regex validation method ----------
    private boolean isValidInput() {
        String patientId = txtPatientId.getText();
        String name = txtPatientName.getText();
        String contact = txtPatientContact.getText();
        String medicalHistory = txtMedicalHistory.getText();

        String idPattern = "^(P)[0-9]{3}$"; // Example: P001
        String namePattern = "^[A-Za-z\\s]{3,50}$"; // Only letters and spaces
        String contactPattern = "^[0-9]{10}$"; // Exactly 10 digits

        if (!patientId.matches(idPattern)) {
            new Alert(Alert.AlertType.ERROR, "Invalid Patient ID! Format should be like P001").show();
            return false;
        }
        if (!name.matches(namePattern)) {
            new Alert(Alert.AlertType.ERROR, "Invalid Name! Only letters and spaces allowed (3-50 characters)").show();
            return false;
        }
        if (!contact.matches(contactPattern)) {
            new Alert(Alert.AlertType.ERROR, "Invalid Contact Number! It should be exactly 10 digits.").show();
            return false;
        }
        if (medicalHistory.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Medical History cannot be empty!").show();
            return false;
        }
        return true;
    }
}
