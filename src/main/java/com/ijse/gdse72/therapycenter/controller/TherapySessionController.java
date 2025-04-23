package com.ijse.gdse72.therapycenter.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

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
    private ComboBox<?> cmbDuration;

    @FXML
    private ComboBox<?> cmbProgram;

    @FXML
    private ComboBox<?> cmbStatus;

    @FXML
    private ComboBox<?> cmbTherapist;

    @FXML
    private ComboBox<?> cmbTimeSlot;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colDuration;

    @FXML
    private TableColumn<?, ?> colPatientName;

    @FXML
    private TableColumn<?, ?> colProgram;

    @FXML
    private TableColumn<?, ?> colSessionId;

    @FXML
    private TableColumn<?, ?> colStatus;

    @FXML
    private TableColumn<?, ?> colTherapist;

    @FXML
    private TableColumn<?, ?> colTime;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TableView<?> tblAppointments;

    @FXML
    private TextField txtPatientId;

    @FXML
    private TextField txtPatientName;

    @FXML
    private TextField txtSessionId;

    @FXML
    void bookOnAction(ActionEvent event) {

    }

    @FXML
    void cancelOnAction(ActionEvent event) {

    }

    @FXML
    void loadOnAction(ActionEvent event) {

    }

    @FXML
    void refreshOnAction(ActionEvent event) {

    }

    @FXML
    void rescheduleOnAction(ActionEvent event) {

    }

    @FXML
    void searchOnAction(ActionEvent event) {

    }

}
