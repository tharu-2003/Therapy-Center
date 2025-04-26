package com.ijse.gdse72.therapycenter.controller;

import com.ijse.gdse72.therapycenter.bo.BOFactory;
import com.ijse.gdse72.therapycenter.bo.custom.TherapyProgramBO;
import com.ijse.gdse72.therapycenter.dao.custom.TherapistDAO;
import com.ijse.gdse72.therapycenter.dao.custom.impl.TherapistDAOImpl;
import com.ijse.gdse72.therapycenter.dto.TherapyProgramDTO;
import com.ijse.gdse72.therapycenter.dto.tm.TherapyProgramTM;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class TherapyProgramController {

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnRefresh;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<TherapyProgramTM , Integer> colCost;

    @FXML
    private TableColumn<TherapyProgramTM , String> colDescription;

    @FXML
    private TableColumn<TherapyProgramTM , String> colDuration;

    @FXML
    private TableColumn<TherapyProgramTM , String> colName;

    @FXML
    private TableColumn<TherapyProgramTM , String> colProgramId;

    @FXML
    private TableColumn<TherapyProgramTM , String> colTherapists;

    @FXML
    private ImageView imgSearch;

    @FXML
    private TableView<TherapyProgramTM> tblPrograms;

    @FXML
    private TextField txtCost;

    @FXML
    private TextArea txtDescription;

    @FXML
    private TextField txtDuration;

    @FXML
    private TextField txtProgramId;

    @FXML
    private TextField txtProgramName;

    @FXML
    private ComboBox<String> cmbTherapists;

    private final TherapyProgramBO THERAPYPROGRAMBO = (TherapyProgramBO) BOFactory.getInstance().getBO(BOFactory.BOType.THERAPY_PROGRAM);


    public void initialize() {
        try {
            populateComboBoxes();
            loadTableData();
            visibleData();

            String nextProgramId = THERAPYPROGRAMBO.getNextTherapyProgramId();
            txtProgramId.setText(nextProgramId);

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Fail to load Page: " + e.getMessage()).show();
        }
    }
    public void visibleData() {
        colProgramId.setCellValueFactory(new PropertyValueFactory<>("programId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        colCost.setCellValueFactory(new PropertyValueFactory<>("fee"));
        colTherapists.setCellValueFactory(new PropertyValueFactory<>("therapist"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
    }

    public void populateComboBoxes() throws SQLException {
        TherapistDAO therapistDAO = new TherapistDAOImpl();

        ArrayList<String> therapistIds = therapistDAO.getTherapist();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(therapistIds);
        cmbTherapists.setItems(observableList);

    }
    public void loadTableData() throws Exception {
        ArrayList<TherapyProgramDTO> therapyProgramDTOS = THERAPYPROGRAMBO.getAllTherapyProgram();
        ObservableList<TherapyProgramTM> therapyProgramTMS = FXCollections.observableArrayList();

        for (TherapyProgramDTO therapyProgramDTO : therapyProgramDTOS) {

            TherapyProgramTM therapyProgramTM = new TherapyProgramTM(
                    therapyProgramDTO.getProgramId(),
                    therapyProgramDTO.getName(),
                    therapyProgramDTO.getDuration(),
                    therapyProgramDTO.getFee(),
                    therapyProgramDTO.getTherapist(),
                    therapyProgramDTO.getDescription()
            );
            therapyProgramTMS.add(therapyProgramTM);
        }
        tblPrograms.setItems(therapyProgramTMS);
    }

    public void refrashPage(){
        txtProgramId.clear();
        txtProgramName.clear();
        txtDuration.clear();
        txtCost.clear();
        cmbTherapists.setValue(null);
        txtDescription.clear();
    }

    @FXML
    void deleteOnAction(ActionEvent event) {
        TherapyProgramTM selectedTherapyProgram = tblPrograms.getSelectionModel().getSelectedItem();

        if (selectedTherapyProgram != null) {
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Confirm Deletion");
            confirmAlert.setHeaderText(null);
            confirmAlert.setContentText("Are you sure you want to delete patient ID: " + selectedTherapyProgram.getProgramId() + "?");

            Optional<ButtonType> result = confirmAlert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    boolean isDeleted = THERAPYPROGRAMBO.deleteTherapyProgram(selectedTherapyProgram.getProgramId());

                    if (isDeleted) {
                        new Alert(Alert.AlertType.INFORMATION, "Therapy Program deleted successfully!").show();
                        loadTableData();
                        refrashPage();
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
    void refreshOnAction(ActionEvent event) {
        refrashPage();
    }

    @FXML
    void saveOnAction(ActionEvent event) {
        try {

            String programId = txtProgramId.getText();
            String programName = txtProgramName.getText();
            String duration = txtDuration.getText();
            BigDecimal fee = new BigDecimal(txtCost.getText());
            String description = txtDescription.getText();
            String therapistId = cmbTherapists.getValue();

            if (!programId.isEmpty() && !programName.isEmpty() && !duration.isEmpty() && fee != null  && !description.isEmpty() && !therapistId.isEmpty()) {
                TherapyProgramDTO therapyProgramDTO = new TherapyProgramDTO(
                        programId,
                        programName,
                        duration,
                        fee,
                        therapistId,
                        description
                );

                boolean isSaved = THERAPYPROGRAMBO.saveTherapyProgram(therapyProgramDTO);

                if (isSaved) {
                    new Alert(Alert.AlertType.INFORMATION, "Therapy_Program Saved Successfully!").show();
                    loadTableData();
                    refrashPage();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to Save Therapy_Program!").show();
                }
            } else {
                new Alert(Alert.AlertType.WARNING, "Please fill all the fields with valid data!").show();
            }


        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Please enter valid numeric valuesx!").show();
        }
    }

    @FXML
    void searchOnAction(MouseEvent event) {
        String therapyProgramId = txtProgramId.getText();

        if (!therapyProgramId.isEmpty()) {
            try {
                TherapyProgramDTO therapyProgramDTO = THERAPYPROGRAMBO.searchTherapyProgram(therapyProgramId);

                if (therapyProgramDTO != null) {
                    txtProgramId.setText(therapyProgramDTO.getProgramId());
                    txtProgramName.setText(therapyProgramDTO.getName());
                    txtDuration.setText(therapyProgramDTO.getDuration());
                    txtCost.setText(therapyProgramDTO.getFee().toString());
                    txtDescription.setText(therapyProgramDTO.getDescription());
                    cmbTherapists.setValue(therapyProgramDTO.getTherapist());

                } else {
                    new Alert(Alert.AlertType.WARNING, "Therapy _ Program Not Found!").show();
                }
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "An error occurred while searching!").show();
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Please enter a Therapy_Programm ID to search!").show();
        }
    }

    @FXML
    void tableClickOnAction(MouseEvent event) {
        TherapyProgramTM selectedTherapyProgram = tblPrograms.getSelectionModel().getSelectedItem();

        if (selectedTherapyProgram != null) {
            txtProgramId.setText(selectedTherapyProgram.getProgramId());
            txtProgramName.setText(selectedTherapyProgram.getName());
            txtDescription.setText(selectedTherapyProgram.getDescription());
            txtDuration.setText(selectedTherapyProgram.getDuration());
            cmbTherapists.setValue(selectedTherapyProgram.getTherapist());
            txtCost.setText(String.valueOf(selectedTherapyProgram.getFee()));
        }
    }

    @FXML
    void updateOnAction(ActionEvent event) {
        try {

            String programId = txtProgramId.getText();
            String programName = txtProgramName.getText();
            String duration = txtDuration.getText();
            BigDecimal fee = new BigDecimal(txtCost.getText());
            String description = txtDescription.getText();
            String therapistId = cmbTherapists.getValue();

            if (!programId.isEmpty() && !programName.isEmpty() && !duration.isEmpty() && fee != null  && !description.isEmpty() && !therapistId.isEmpty()) {
                TherapyProgramDTO therapyProgramDTO = new TherapyProgramDTO(
                        programId,
                        programName,
                        duration,
                        fee,
                        therapistId,
                        description
                );

                boolean isUpdate = THERAPYPROGRAMBO.updateTherapyProgram(therapyProgramDTO);

                if (isUpdate) {
                    new Alert(Alert.AlertType.INFORMATION, "Therapy_Program Updated Successfully!").show();
                    loadTableData();
                    refrashPage();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to update Therapy_Program!").show();
                }
            } else {
                new Alert(Alert.AlertType.WARNING, "Please fill all the fields with valid data!").show();
            }


        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Please enter valid numeric values!").show();
        }
    }

}
