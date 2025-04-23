package com.ijse.gdse72.therapycenter.dao.custom;

import com.ijse.gdse72.therapycenter.bo.SuperBO;
import com.ijse.gdse72.therapycenter.dao.CrudDAO;
import com.ijse.gdse72.therapycenter.entity.User;

public interface UserDAO extends CrudDAO<User, String> {
    User findByUsername(String username) throws Exception;
}
