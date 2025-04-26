package com.ijse.gdse72.therapycenter.bo.custom;

import com.ijse.gdse72.therapycenter.bo.SuperBO;
import com.ijse.gdse72.therapycenter.dto.PaymentDTO;

import java.util.ArrayList;

public interface PaymentBO extends SuperBO {
    String getNextPaymentId() throws Exception;
    ArrayList<PaymentDTO> getAllPayments() throws Exception;
    boolean savePayment(PaymentDTO payment) throws Exception;
    boolean updatePayment(PaymentDTO payment) throws Exception;
    boolean deletePayment(String paymentId) throws Exception;
    PaymentDTO searchPayment(String paymentId) throws Exception;
}
