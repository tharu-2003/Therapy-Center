package com.ijse.gdse72.therapycenter.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

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
    private ComboBox<?> cmbAssignedProgram;

    @FXML
    private ComboBox<?> cmbAvailability;

    @FXML
    private ComboBox<?> cmbSpecialization;

    @FXML
    private TableColumn<?, ?> colAssignedProgram;

    @FXML
    private TableColumn<?, ?> colContact;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colSpecialization;

    @FXML
    private TableColumn<?, ?> colStatus;

    @FXML
    private TableColumn<?, ?> colTherapistId;

    @FXML
    private TableView<?> customerTable;

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

    @FXML
    void clickOnAction(MouseEvent event) {

    }

    @FXML
    void deleteOnAction(ActionEvent event) {

    }

    @FXML
    void refreshOnAction(ActionEvent event) {

    }

    @FXML
    void saveOnAction(ActionEvent event) {

    }

    @FXML
    void searchOnAction(MouseEvent event) {

    }

    @FXML
    void updateOnAction(ActionEvent event) {

    }

}
