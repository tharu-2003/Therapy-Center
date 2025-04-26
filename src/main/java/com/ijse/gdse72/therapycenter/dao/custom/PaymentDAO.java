package com.ijse.gdse72.therapycenter.dao.custom;

import com.ijse.gdse72.therapycenter.bo.SuperBO;
import com.ijse.gdse72.therapycenter.dao.CrudDAO;
import com.ijse.gdse72.therapycenter.entity.Payment;

public interface PaymentDAO extends CrudDAO<Payment , String> {
    String getNextPaymentId() throws Exception;
}
