package com.ijse.gdse72.therapycenter.controller;

import com.ijse.gdse72.therapycenter.bo.BOFactory;
import com.ijse.gdse72.therapycenter.bo.custom.TherapistBO;
import com.ijse.gdse72.therapycenter.dto.TherapistDTO;
import com.ijse.gdse72.therapycenter.dto.tm.TherapistTM;
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

public class TherapistController {

    @FXML
    private Button btnDelete, btnRefresh, btnSave, btnUpdate;
    @FXML
    private ComboBox<String> cmbAssignedProgram, cmbAvailability, cmbSpecialization;
    @FXML
    private TableColumn<TherapistTM , String> colAssignedProgram, colName, colSpecialization, colStatus, colTherapistId;
    @FXML
    private TableColumn<TherapistTM , Integer> colContact;
    @FXML
    private TableView<TherapistTM> customerTable;
    @FXML
    private ImageView imgSearch;
    @FXML
    private TextField txtTherapistContact, txtTherapistGmail, txtTherapistId, txtTherapistName;

    private final TherapistBO therapistBO = (TherapistBO) BOFactory.getInstance().getBO(BOFactory.BOType.THERAPIST);

    // Regex patterns
    private static final String ID_REGEX = "^T\\d{3}$";
    private static final String NAME_REGEX = "^[A-Za-z ]{3,50}$";
    private static final String CONTACT_REGEX = "^[0-9]{10}$";
    private static final String GMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@gmail\\.com$";

    public void initialize() {
        try {
            populateComboBoxes();
            loadTableData();
            visibleData();
            String nextTherapistId = therapistBO.getNextTherapistId();
            txtTherapistId.setText(nextTherapistId);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "ID Generation Failed: " + e.getMessage()).show();
        }
    }

    private void refreshPage() {
        txtTherapistId.clear();
        txtTherapistName.clear();
        txtTherapistGmail.clear();
        txtTherapistContact.clear();
        cmbAssignedProgram.setValue(null);
        cmbAvailability.setValue(null);
        cmbSpecialization.setValue(null);
    }

    private void loadTableData() throws Exception {
        ArrayList<TherapistDTO> therapistDTOS = therapistBO.getAllTherapist();
        ObservableList<TherapistTM> therapistTMS = FXCollections.observableArrayList();

        for (TherapistDTO dto : therapistDTOS) {
            therapistTMS.add(new TherapistTM(
                    dto.getTherapistId(),
                    dto.getName(),
                    dto.getSpecialization(),
                    dto.getAvailability(),
                    dto.getContactNumber(),
                    dto.getAssignedProgram(),
                    dto.getEmail()
            ));
        }
        customerTable.setItems(therapistTMS);
    }

    public void visibleData() {
        colTherapistId.setCellValueFactory(new PropertyValueFactory<>("therapistId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colSpecialization.setCellValueFactory(new PropertyValueFactory<>("specialization"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contactNumber"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("availability"));
        colAssignedProgram.setCellValueFactory(new PropertyValueFactory<>("assignedProgram"));
    }

    public void populateComboBoxes() {
        cmbAvailability.setItems(FXCollections.observableArrayList("Available", "Unavailable", "On Leave", "Busy"));
        cmbAssignedProgram.setItems(FXCollections.observableArrayList(
                "Cognitive Behavioral Therapy", "Mindfulness-Based Stress Reduction",
                "Dialectical Behavior Therapy", "Group Therapy Sessions", "Family Counseling"));
        cmbSpecialization.setItems(FXCollections.observableArrayList(
                "Child Psychology", "Depression & Anxiety", "Addiction Recovery",
                "Trauma Therapy", "Relationship Counseling", "PTSD Specialist"));
    }

    @FXML
    void clickOnAction(MouseEvent event) {
        TherapistTM selected = customerTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            txtTherapistId.setText(selected.getTherapistId());
            txtTherapistName.setText(selected.getName());
            cmbSpecialization.setValue(selected.getSpecialization());
            cmbAssignedProgram.setValue(selected.getAssignedProgram());
            cmbAvailability.setValue(selected.getAvailability());
            txtTherapistContact.setText(String.valueOf(selected.getContactNumber()));
            txtTherapistGmail.setText(selected.getEmail());
        }
    }

    @FXML
    void deleteOnAction(ActionEvent event) {
        TherapistTM selected = customerTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                    "Are you sure you want to delete Therapist : " + selected.getTherapistId() + "?",
                    ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> result = confirm.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.YES) {
                try {
                    if (therapistBO.deleteTherapist(selected.getTherapistId())) {
                        new Alert(Alert.AlertType.INFORMATION, "Therapist deleted successfully!").show();
                        loadTableData();
                        refreshPage();
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Failed to delete the Therapist!").show();
                    }
                } catch (Exception e) {
                    new Alert(Alert.AlertType.ERROR, "Database error: " + e.getMessage()).show();
                }
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Please select a Therapist to delete.").show();
        }
    }

    @FXML
    void refreshOnAction(ActionEvent event) {
        refreshPage();
    }

    @FXML
    void saveOnAction(ActionEvent event) {
        try {
            if (!validateInputs()) return;

            TherapistDTO dto = new TherapistDTO(
                    txtTherapistId.getText(),
                    txtTherapistName.getText(),
                    cmbSpecialization.getValue(),
                    cmbAvailability.getValue(),
                    Integer.parseInt(txtTherapistContact.getText()),
                    cmbAssignedProgram.getValue(),
                    txtTherapistGmail.getText()
            );

            boolean isSaved = therapistBO.saveTherapist(dto);
            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Therapist Saved Successfully!").show();
                loadTableData();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to Save Therapist!").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage()).show();
        }
    }

    @FXML
    void searchOnAction(MouseEvent event) {
        String id = txtTherapistId.getText();
        if (!id.isEmpty()) {
            try {
                TherapistDTO dto = therapistBO.searchTherapist(id);
                if (dto != null) {
                    txtTherapistName.setText(dto.getName());
                    txtTherapistContact.setText(String.valueOf(dto.getContactNumber()));
                    txtTherapistGmail.setText(dto.getEmail());
                    cmbSpecialization.setValue(dto.getSpecialization());
                    cmbAssignedProgram.setValue(dto.getAssignedProgram());
                    cmbAvailability.setValue(dto.getAvailability());
                } else {
                    new Alert(Alert.AlertType.WARNING, "Therapist Not Found!").show();
                }
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Error searching therapist!").show();
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Please enter a Therapist ID!").show();
        }
    }

    @FXML
    void updateOnAction(ActionEvent event) {
        try {
            if (!validateInputs()) return;

            TherapistDTO dto = new TherapistDTO(
                    txtTherapistId.getText(),
                    txtTherapistName.getText(),
                    cmbSpecialization.getValue(),
                    cmbAvailability.getValue(),
                    Integer.parseInt(txtTherapistContact.getText()),
                    cmbAssignedProgram.getValue(),
                    txtTherapistGmail.getText()
            );

            boolean isUpdated = therapistBO.updateTherapist(dto);
            if (isUpdated) {
                new Alert(Alert.AlertType.INFORMATION, "Therapist Updated Successfully!").show();
                loadTableData();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to Update Therapist!").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage()).show();
        }
    }

    private boolean validateInputs() {
        String id = txtTherapistId.getText();
        String name = txtTherapistName.getText();
        String email = txtTherapistGmail.getText();
        String contact = txtTherapistContact.getText();

        if (!id.matches(ID_REGEX)) {
            new Alert(Alert.AlertType.WARNING, "Invalid Therapist ID! (e.g., T001)").show();
            return false;
        }
        if (!name.matches(NAME_REGEX)) {
            new Alert(Alert.AlertType.WARNING, "Invalid Name! Only letters allowed, 3-50 characters.").show();
            return false;
        }
        if (!contact.matches(CONTACT_REGEX)) {
            new Alert(Alert.AlertType.WARNING, "Invalid Contact Number! Must be 10 digits.").show();
            return false;
        }
        if (!email.matches(GMAIL_REGEX)) {
            new Alert(Alert.AlertType.WARNING, "Invalid Gmail Address! Must end with @gmail.com.").show();
            return false;
        }
        if (cmbSpecialization.getValue() == null || cmbAssignedProgram.getValue() == null || cmbAvailability.getValue() == null) {
            new Alert(Alert.AlertType.WARNING, "Please select Specialization, Program, and Availability!").show();
            return false;
        }
        return true;
    }
}
