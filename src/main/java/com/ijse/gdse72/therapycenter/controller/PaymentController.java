package com.ijse.gdse72.therapycenter.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class PaymentController {

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnGenerateBill;

    @FXML
    private Button btnLoad;

    @FXML
    private Button btnPay;

    @FXML
    private Button btnRefresh;

    @FXML
    private Button btnSearch;

    @FXML
    private ComboBox<?> cmbPaymentMethod;

    @FXML
    private ComboBox<?> cmbStatus;

    @FXML
    private TableColumn<?, ?> colAmount;

    @FXML
    private TableColumn<?, ?> colBalance;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colPatient;

    @FXML
    private TableColumn<?, ?> colPayedAmound;

    @FXML
    private TableColumn<?, ?> colPaymentId;

    @FXML
    private TableColumn<?, ?> colPaymentMethod;

    @FXML
    private TableColumn<?, ?> colSessionId;

    @FXML
    private TableColumn<?, ?> colStatus;

    @FXML
    private TableColumn<?, ?> colStatus2;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TableView<?> tblAppointments;

    @FXML
    private TextField txtAmount;

    @FXML
    private TextField txtBalance;

    @FXML
    private TextField txtPatientName;

    @FXML
    private TextField txtPayedAmound;

    @FXML
    private TextField txtPaymentId;

    @FXML
    private TextField txtSessionId;

    @FXML
    void cancelOnAction(ActionEvent event) {

    }

    @FXML
    void generateBillOnAction(ActionEvent event) {

    }

    @FXML
    void loadOnAction(ActionEvent event) {

    }

    @FXML
    void payOnAction(ActionEvent event) {

    }

    @FXML
    void refreshOnAction(ActionEvent event) {

    }

    @FXML
    void searchOnAction(ActionEvent event) {

    }

}
