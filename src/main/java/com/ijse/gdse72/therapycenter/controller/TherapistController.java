package com.ijse.gdse72.therapycenter.controller;

import com.ijse.gdse72.therapycenter.bo.BOFactory;
import com.ijse.gdse72.therapycenter.bo.custom.TherapistBO;
import com.ijse.gdse72.therapycenter.bo.custom.impl.TherapistBOImpl;
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
    private Button btnDelete;

    @FXML
    private Button btnRefresh;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private ComboBox<String> cmbAssignedProgram;

    @FXML
    private ComboBox<String> cmbAvailability;

    @FXML
    private ComboBox<String> cmbSpecialization;

    @FXML
    private TableColumn<TherapistTM , String> colAssignedProgram;

    @FXML
    private TableColumn<TherapistTM , Integer> colContact;

    @FXML
    private TableColumn<TherapistTM , String> colName;

    @FXML
    private TableColumn<TherapistTM , String> colSpecialization;

    @FXML
    private TableColumn<TherapistTM , String> colStatus;

    @FXML
    private TableColumn<TherapistTM , String> colTherapistId;

    @FXML
    private TableView<TherapistTM> customerTable;

    @FXML
    private ImageView imgSearch;

    @FXML
    private TextField txtTherapistContact;

    @FXML
    private TextField txtTherapistGmail;

    @FXML
    private TextField txtTherapistId;

    @FXML
    private TextField txtTherapistName;

    private final TherapistBO therapistBO = (TherapistBO) BOFactory.getInstance().getBO(BOFactory.BOType.THERAPIST);


    public void initialize() {
        try {
            populateComboBoxes();
            loadTableData();
            visibleData();

            String nextTherapistId = therapistBO.getNextTherapistId();
            txtTherapistId.setText(nextTherapistId);

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Id Genaration Failed: " + e.getMessage()).show();
        }
    }

    private void refrashPage() {
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

        for (TherapistDTO therapistDTO : therapistDTOS) {
            TherapistTM therapistTM = new TherapistTM(
                    therapistDTO.getTherapistId(),
                    therapistDTO.getName(),
                    therapistDTO.getSpecialization(),
                    therapistDTO.getAvailability(),
                    therapistDTO.getContactNumber(),
                    therapistDTO.getAssignedProgram(),
                    therapistDTO.getEmail()
            );
            therapistTMS.add(therapistTM);
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
        ObservableList<String> availability = FXCollections.observableArrayList(
                "Available",
                "Unavailable",
                "On Leave",
                "Busy"
        );

        cmbAvailability.setItems(availability);

        ObservableList<String> programes = FXCollections.observableArrayList(
                "Cognitive Behavioral Therapy", "Mindfulness-Based Stress Reduction" ,
                "Dialectical Behavior Therapy" , "Group Therapy Sessions" , "Family Counseling"
        );

        cmbAssignedProgram.setItems(programes);

        ObservableList<String> specialization = FXCollections.observableArrayList(
                "Child Psychology",
                "Depression & Anxiety",
                "Addiction Recovery",
                "Trauma Therapy",
                "Relationship Counseling",
                "PTSD Specialist"
        );

        cmbSpecialization.setItems(specialization);
    }

    @FXML
    void clickOnAction(MouseEvent event) {
        TherapistTM selectedTherapist = customerTable.getSelectionModel().getSelectedItem();

        if (selectedTherapist != null) {
            txtTherapistId.setText(selectedTherapist.getTherapistId());
            txtTherapistName.setText(selectedTherapist.getName());
            cmbSpecialization.setValue(selectedTherapist.getSpecialization());
            cmbAssignedProgram.setValue(selectedTherapist.getAssignedProgram());
            cmbAvailability.setValue(selectedTherapist.getAvailability());
            txtTherapistContact.setText(String.valueOf(selectedTherapist.getContactNumber()));
            txtTherapistGmail.setText(selectedTherapist.getEmail());
        }
    }

    @FXML
    void deleteOnAction(ActionEvent event) {
        TherapistTM selectedTherapist = customerTable.getSelectionModel().getSelectedItem();

        if (selectedTherapist != null) {
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Confirm Deletion");
            confirmAlert.setHeaderText(null);
            confirmAlert.setContentText("Are you sure you want to delete Therapist : " + selectedTherapist.getTherapistId() + "?");

            Optional<ButtonType> result = confirmAlert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    boolean isDeleted = therapistBO.deleteTherapist(selectedTherapist.getTherapistId());

                    if (isDeleted) {
                        new Alert(Alert.AlertType.INFORMATION, "Therapist deleted successfully!").show();
                        loadTableData();
                        refrashPage();
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Failed to delete the Therapist!").show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    new Alert(Alert.AlertType.ERROR, "Database error: " + e.getMessage()).show();
                }
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Please select a Therapist to delete.").show();
        }
    }

    @FXML
    void refreshOnAction(ActionEvent event) {
        refrashPage();
    }

    @FXML
    void saveOnAction(ActionEvent event) {
        try {
            String therapistId = txtTherapistId.getText();
            String therapistName = txtTherapistName.getText();
            String specialization = cmbSpecialization.getValue();
            String availability = cmbAvailability.getValue();
            int contact = Integer.parseInt(txtTherapistContact.getText());
            String program = cmbAssignedProgram.getValue();
            String email = txtTherapistGmail.getText();

            if (!therapistId.isEmpty() && !therapistName.isEmpty() && !email.isEmpty() && specialization != null && program != null && availability != null && contact > 0) {

                TherapistDTO therapistDTO = new TherapistDTO(
                        therapistId,
                        therapistName,
                        specialization,
                        availability,
                        contact,
                        program,
                        email
                );

                boolean isSaved = therapistBO.saveTherapist(therapistDTO);

                if (isSaved) {
                    new Alert(Alert.AlertType.INFORMATION, "Therapist Saved Successfully!").show();
                    loadTableData();
                    refrashPage();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to Save Therapist!").show();
                }
            } else {
                new Alert(Alert.AlertType.WARNING, "Please fill all the fields with valid data!").show();
            }

        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Please enter valid numeric values").show();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }


    @FXML
    void searchOnAction(MouseEvent event) {
        String therapistId = txtTherapistId.getText();

        if (!therapistId.isEmpty()) {
            try{
                TherapistDTO therapistDTO = therapistBO.searchTherapist(therapistId);

                if (therapistDTO !=null) {
                    txtTherapistName.setText(therapistDTO.getName());
                    txtTherapistContact.setText(String.valueOf(therapistDTO.getContactNumber()));
                    txtTherapistGmail.setText(therapistDTO.getEmail());
                    cmbSpecialization.setValue(therapistDTO.getSpecialization());
                    cmbAssignedProgram.setValue(therapistDTO.getAssignedProgram());
                    cmbAvailability.setValue(therapistDTO.getAvailability());

                } else {
                    new Alert(Alert.AlertType.WARNING, "Therapist Not Found!").show();
                }
            }catch (Exception e){
                new Alert(Alert.AlertType.ERROR, "An error occurred while searching!").show();            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Please enter a Therapist ID to search!").show();
        }
    }

    @FXML
    void updateOnAction(ActionEvent event) {
        try {

            String therapistId = txtTherapistId.getText();
            String therapistName = txtTherapistName.getText();
            String specialization = cmbSpecialization.getValue();
            String availability = cmbAvailability.getValue();
            int contact = Integer.parseInt(txtTherapistContact.getText());
            String program = cmbAssignedProgram.getValue();
            String email = txtTherapistGmail.getText();

            if (!therapistId.isEmpty() && !therapistName.isEmpty() && !email.isEmpty() && specialization != null && program != null && availability != null && contact > 0) {

                TherapistDTO therapistDTO = new TherapistDTO(
                        therapistId,
                        therapistName,
                        specialization,
                        availability,
                        contact,
                        program,
                        email
                );

                boolean isSUpdate = therapistBO.updateTherapist(therapistDTO);

                if (isSUpdate) {
                    new Alert(Alert.AlertType.INFORMATION, "Therapist Updated Successfully!").show();
                    loadTableData();
                    refrashPage();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to Update Therapist!").show();
                }
            } else {
                new Alert(Alert.AlertType.WARNING, "Please fill all the fields with valid data!").show();
            }

        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Please enter valid numeric values").show();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

}
