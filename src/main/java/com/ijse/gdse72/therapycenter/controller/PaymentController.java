package com.ijse.gdse72.therapycenter.controller;

import com.ijse.gdse72.therapycenter.bo.BOFactory;
import com.ijse.gdse72.therapycenter.bo.custom.PaymentBO;
import com.ijse.gdse72.therapycenter.dao.custom.TherapySessionDAO;
import com.ijse.gdse72.therapycenter.dao.custom.impl.TherapySessionDAOImpl;
import com.ijse.gdse72.therapycenter.dto.PaymentDTO;
import com.ijse.gdse72.therapycenter.dto.TherapySessionDTO;
import com.ijse.gdse72.therapycenter.dto.tm.PaymentTM;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

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
    private ComboBox<String> cmbPaymentMethod;

    @FXML
    private ComboBox<String> cmbStatus;

    @FXML
    private ComboBox<String> cmbSessionId;

    @FXML
    private TableColumn<PaymentTM , BigDecimal> colAmount;

    @FXML
    private TableColumn<PaymentTM , BigDecimal> colBalance;

    @FXML
    private TableColumn<PaymentTM , LocalDate> colDate;

    @FXML
    private TableColumn<PaymentTM , String> colPatient;

    @FXML
    private TableColumn<PaymentTM , BigDecimal> colPayedAmound;

    @FXML
    private TableColumn<PaymentTM , String> colPaymentId;

    @FXML
    private TableColumn<PaymentTM , String> colPaymentMethod;

    @FXML
    private TableColumn<PaymentTM , String> colSessionId;

    @FXML
    private TableColumn<PaymentTM , String> colStatus;

    @FXML
    private TableColumn<PaymentTM , String> colStatus2;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TableView<PaymentTM> tblAppointments;

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

    private final PaymentBO paymentBO = (PaymentBO) BOFactory.getInstance().getBO(BOFactory.BOType.PAYMENT);


    public void initialize() {
        try {
            populateComboBoxes();
            refrashPage();
            loadTableData();
            visibleData();

            String nextTherapistId = paymentBO.getNextPaymentId();
            txtPaymentId.setText(nextTherapistId);

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Id Genaration Failed: " + e.getMessage()).show();
        }
    }

    private void visibleData() {
        colPaymentId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colSessionId.setCellValueFactory(new PropertyValueFactory<>("sessionId"));
        colPatient.setCellValueFactory(new PropertyValueFactory<>("patientName"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colPaymentMethod.setCellValueFactory(new PropertyValueFactory<>("paymentMethod"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("paymentDate"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colPayedAmound.setCellValueFactory(new PropertyValueFactory<>("paidAmount"));
        colBalance.setCellValueFactory(new PropertyValueFactory<>("balance"));
    }

    private void loadTableData() throws Exception {
        ArrayList<PaymentDTO> paymentDTOS = paymentBO.getAllPayments();
        ObservableList<PaymentTM> paymentTMS = FXCollections.observableArrayList();

        for (PaymentDTO paymentDTO : paymentDTOS) {
            PaymentTM paymentTM = new PaymentTM(
                    paymentDTO.getId(),
                    paymentDTO.getSessionId(),
                    paymentDTO.getPatientName(),
                    paymentDTO.getAmount(),
                    paymentDTO.getPaymentMethod(),
                    paymentDTO.getPaymentDate(),
                    paymentDTO.getStatus(),
                    paymentDTO.getPaidAmount(),
                    paymentDTO.getBalance()
            );
            paymentTMS.add(paymentTM);
        }
        tblAppointments.setItems(paymentTMS);
    }

    private void refrashPage() throws Exception {
        String nextTherapistId = paymentBO.getNextPaymentId();
        txtPaymentId.setText(nextTherapistId);
        cmbSessionId.setValue("");
        txtPatientName.setText("");
        txtAmount.setText("");
        cmbPaymentMethod.setValue(null);
        cmbStatus.setValue(null);
        cmbSessionId.setValue(null);
        txtBalance.setText("");
        txtPayedAmound.setText("");
        datePicker.setValue(null);
    }

    private void populateComboBoxes() throws SQLException {
        TherapySessionDAO sessionDAO = new TherapySessionDAOImpl();

        ArrayList<String> sessionId = sessionDAO.getSessionId();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(sessionId);
        cmbSessionId.setItems(observableList);

        ObservableList<String> methods = FXCollections.observableArrayList(
                "Cash",
                "Credit/Debit Card",
                "Bank Transfer",
                "Cheque"
        );

        cmbPaymentMethod.setItems(methods);

        ObservableList<String> status = FXCollections.observableArrayList(
                "Pending", "Completed", "Failed",
                "Refunded"
        );

        cmbStatus.setItems(status);
    }

    @FXML
    void cancelOnAction(ActionEvent event) {
        PaymentTM selectedPayment = tblAppointments.getSelectionModel().getSelectedItem();

        if (selectedPayment != null) {
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Confirm Deletion");
            confirmAlert.setHeaderText(null);
            confirmAlert.setContentText("Are you sure you want to Cansel Payment : " + selectedPayment.getId() + "?");

            Optional<ButtonType> result = confirmAlert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    boolean isDeleted = paymentBO.deletePayment(selectedPayment.getId());

                    if (isDeleted) {
                        new Alert(Alert.AlertType.INFORMATION, "Payment deleted successfully!").show();
                        loadTableData();
                        refrashPage();
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Failed to delete the Payemnt!").show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    new Alert(Alert.AlertType.ERROR, "Database error: " + e.getMessage()).show();
                }
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Please select a Payment to delete.").show();
        }
    }

    @FXML
    void generateBillOnAction(ActionEvent event) {

    }

    @FXML
    void loadOnAction(ActionEvent event) {
        try {
            TherapySessionDAO therapySessionDAO = new TherapySessionDAOImpl();
            TherapySessionDTO session = therapySessionDAO.getSession(cmbSessionId.getValue());
            if (session != null) {
                txtPatientName.setText(session.getPatientName());
            } else {
                new Alert(Alert.AlertType.WARNING, "No Session found!").show();
                refrashPage();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load patient: " + e.getMessage()).show();
        }
    }

    @FXML
    void payOnAction(ActionEvent event) {
        try{
            String paymentId = txtPaymentId.getText();
            String sessionId = cmbSessionId.getValue();
            String patientName = txtPatientName.getText();
            BigDecimal amount = new BigDecimal(txtAmount.getText());
            String paymentMethod = cmbPaymentMethod.getValue();
            LocalDate paymentDate = datePicker.getValue();
            String status = cmbStatus.getValue();
            BigDecimal paidAmount = new BigDecimal(txtPayedAmound.getText());
            BigDecimal balance = new BigDecimal(txtBalance.getText());



            if (!paymentId.isEmpty() && !sessionId.isEmpty() && !patientName.isEmpty() && !paymentMethod.isEmpty() && !status.isEmpty() && paymentDate != null) {
                PaymentDTO paymentDTO = new PaymentDTO(
                        paymentId,
                        sessionId,
                        patientName,
                        amount,
                        paymentMethod,
                        paymentDate,
                        status,
                        paidAmount,
                        balance

                );

                boolean isSaved = paymentBO.savePayment(paymentDTO);

                if (isSaved) {
                    new Alert(Alert.AlertType.INFORMATION, "Payment Process Saved Successfully!").show();
                    loadTableData();
                    refrashPage();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to Save Payment Process!").show();
                }
            } else {
                new Alert(Alert.AlertType.WARNING, "Please fill all the fields with valid data!").show();
            }

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void refreshOnAction(ActionEvent event) throws Exception {

            refrashPage();
    }

    @FXML
    void searchOnAction(ActionEvent event) {
        String paymentId = txtPaymentId.getText();

        if (!paymentId.isEmpty()) {
            try{
                PaymentDTO paymentDTO = paymentBO.searchPayment(paymentId);

                if (paymentDTO !=null) {
                    txtPaymentId.setText(paymentDTO.getId());
                    cmbSessionId.setValue(paymentDTO.getSessionId());
                    txtPatientName.setText(paymentDTO.getPatientName());
                    txtAmount.setText(String.valueOf(paymentDTO.getAmount()));
                    cmbStatus.setValue(paymentDTO.getStatus());
                    cmbPaymentMethod.setValue(paymentDTO.getPaymentMethod());
                    datePicker.setValue(paymentDTO.getPaymentDate());
                    txtBalance.setText(String.valueOf(paymentDTO.getBalance()));
                    txtPayedAmound.setText(String.valueOf(paymentDTO.getPaidAmount()));

                    ObservableList<PaymentTM> paymentTMS = FXCollections.observableArrayList();

                    PaymentTM paymentTM = new PaymentTM(
                            paymentDTO.getId(),
                            paymentDTO.getSessionId(),
                            paymentDTO.getPatientName(),
                            paymentDTO.getAmount(),
                            paymentDTO.getPaymentMethod(),
                            paymentDTO.getPaymentDate(),
                            paymentDTO.getStatus(),
                            paymentDTO.getPaidAmount(),
                            paymentDTO.getBalance()
                    );
                    paymentTMS.add(paymentTM);
                    tblAppointments.setItems(paymentTMS);
                } else {
                    new Alert(Alert.AlertType.WARNING, "Payment Not Found!").show();
                }
            }catch (Exception e){
                new Alert(Alert.AlertType.ERROR, "An error occurred while searching!").show();            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Please enter a Payment ID to search!").show();
        }
    }

    @FXML
    void calOnAction(ActionEvent event) {
        try {
            if (txtAmount.getText().isEmpty() || txtPayedAmound.getText().isEmpty()) {
                throw new IllegalArgumentException("Amount and paid amount cannot be empty.");
            }

            BigDecimal amount = new BigDecimal(txtAmount.getText().trim());
            BigDecimal paidAmount = new BigDecimal(txtPayedAmound.getText().trim());

            if (amount.compareTo(BigDecimal.ZERO) < 0 || paidAmount.compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalArgumentException("Values must be positive.");
            }

            BigDecimal balance = paidAmount.subtract(amount); // balance = change to return

            if (balance.compareTo(BigDecimal.ZERO) < 0) {
                // Not enough payment
                txtBalance.setText("0.00");
                new Alert(Alert.AlertType.WARNING,
                        "Not enough payment. You still owe: Rs. " +
                                balance.abs().setScale(2, RoundingMode.HALF_UP)
                ).show();
            } else {
                // Paid enough or overpaid – show the balance (change)
                txtBalance.setText(balance.setScale(2, RoundingMode.HALF_UP).toString());
            }

        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid number format. Enter numeric values only.").show();
        } catch (IllegalArgumentException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void ClickOnAction(MouseEvent event) {

    }

    public void ClickOnAction(javafx.scene.input.MouseEvent mouseEvent) {
        PaymentTM selectedPayment = tblAppointments.getSelectionModel().getSelectedItem();

        if (selectedPayment != null) {
            txtPaymentId.setText(selectedPayment.getId());
            cmbSessionId.setValue(selectedPayment.getSessionId());
            txtPatientName.setText(selectedPayment.getPatientName());
            txtAmount.setText(selectedPayment.getAmount().toString());
            cmbPaymentMethod.setValue(selectedPayment.getPaymentMethod());
            cmbStatus.setValue(selectedPayment.getStatus());
            txtBalance.setText(selectedPayment.getBalance().toString());
            txtPayedAmound.setText(selectedPayment.getPaidAmount().toString());
            datePicker.setValue(LocalDate.from(selectedPayment.getPaymentDate()));
        }

    }
}
